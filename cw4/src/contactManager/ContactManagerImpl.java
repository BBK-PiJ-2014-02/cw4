package contactManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * A class to manage your contacts and meetings. 
 * 
 * @author Vasco
 *
 */
public class ContactManagerImpl implements ContactManager {
    /**
     * Default path for contacts.
     */
    private final String filePath = "src"+File.separator;

    /**
     * The default data file name where all data is stored.
     */
    private final String fileName = "contacts.txt";

    /**
     * The constant file name to be used on all File handlers
     * including the path.
     */
    private String filePathName = filePath + fileName;

    /**
     * The known key name to define Contact objects in file.
     */
    private final String CONTACT_KEY = "contact";

    /**
     * The known key name to define Meeting objects in file.
     */
    private final String MEETING_KEY = "meeting";

    /**
     * The meeting type key for PastMeeting.
     */
    private final String TYPE_PAST_MEETING = "PastMeeting";

    /**
     * The meeting type key for Meeting.
     */
    private final String TYPE_MEETING = "Meeting";

    /**
     * The meeting type key for FutureMeeting.
     */
    private final String TYPE_FUTURE_MEETING = "FutureMeeting";
    
    /**
     * The next Contact Id
     */
    private Integer contactId;

    /**
     * The known Contact list
     */
    private Set<Contact> contactList;

    /**
     * The next Meeting Id free slot
     */
    private Integer meetingId;

    /**
     * The known Meeting list
     */
    private List<Meeting> meetingList;

    // ***************************************************************************** //
    // *                                CONSTRUCTOR                                * //
    // ***************************************************************************** //
    /**
     * Default constructor
     */
    public ContactManagerImpl() {
        this.contactId = 0;
        this.meetingId = 0;
        this.contactList = new HashSet<Contact>();
        this.meetingList = new LinkedList<Meeting>();

        // load all data if any, or create a new file to save data into.
        try {
            loadData();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add a shutdown hook to call flush() before exiting the application.
        this.addShutdownHook();
    }
    
    /**
     * Constructor to store data on a different file.
     * 
     * @param fileName the file to store all data
     */
    public ContactManagerImpl(String fileName) {
        filePathName = fileName;
        this.contactId = 0;
        this.meetingId = 0;
        this.contactList = new HashSet<Contact>();
        this.meetingList = new LinkedList<Meeting>();

        // load all data if any, or create a new file to save data into.
        try {
            loadData();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add a shutdown hook to call flush() before exiting the application.
        this.addShutdownHook();
    }
    
    // ***************************************************************************** //
    // *                             INTERFACE METHODS                             * //
    // ***************************************************************************** //

    /**
     * {@inheritDoc}
     */
    @Override
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        // Throw exception if any of the given contacts it not known.
        for(Contact contact : contacts ) {
            // If we do not have one contact for each of the given contacts, throw exception
            Long contactFound = contactList.stream().filter(c -> c.getId() == contact.getId() ).count(); 
            if ( !(contactFound == 1) ) throw new IllegalArgumentException("Not a valid contact id: " + contact.getId());
        }

        // If date is in the past, throw exception
        if ( date.before(Calendar.getInstance()) ) throw new IllegalArgumentException("Cannot add FutureMeeting with past date.");

        // Create a new future meeting to be added.
        FutureMeeting futureMeeting = new FutureMeetingImpl(meetingId, date, contacts);

        // Add the future meeting.
        meetingList.add(futureMeeting);

        // Increment the meeting count.
        meetingId++;

        // Return the meeting id created.
        return (meetingId-1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PastMeeting getPastMeeting(int id) {
        // Get the meeting
        Meeting meeting = getMeeting(id);

        // If null, there was not meeting with that id.
        if ( meeting == null ) return null;

        // Get date
        Calendar meetingDate = meeting.getDate();

        // Check if date is in the future
        if ( meetingDate.after(Calendar.getInstance()) ) throw new IllegalArgumentException("Cannot get a PastMeeting with a future date.");

        // Cast it into a PastMeeting
        return (PastMeeting) meeting;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FutureMeeting getFutureMeeting(int id) {
        // Get the meeting with the respective given id.
        Meeting meeting = getMeeting(id);

        // No meeting found, return null.
        if ( meeting == null ) return null;

        Calendar date = meeting.getDate();

        // Check date is not in the past, or throw exception.
        if ( date.before(Calendar.getInstance()) ) throw new IllegalArgumentException("Cannot get a FutureMeeting with a past date.");

        Set<Contact> contacts = meeting.getContacts();
        FutureMeeting futureMeeting = new FutureMeetingImpl(id, date, contacts);
        return futureMeeting;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Meeting getMeeting(int id) {
        // Get the meeting with the requested meeting id.
        for( Meeting meetingFound : meetingList ) {
            if ( meetingFound.getId() == id ) return meetingFound;
        }

        // If all the above fails, return null.
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getFutureMeetingList(Contact contact) {
        // If no contact given, return an empty list
        if ( contact == null ) return new LinkedList<Meeting>();

        // If contact is not valid, throw exception
        if ( !hasContact(contact.getId()) ) throw new IllegalArgumentException("Cannot find contact id: " + contact.getId());

        // Final FutureMeeting list to be returned
        List<Meeting> finalFutureMeetingList = new LinkedList<Meeting>();

        for(Meeting meeting : meetingList ) {
            // Only interested in the FutureMeeting types.
            if ( FutureMeeting.class.isInstance(meeting)) {
                Set<Contact> contacts = meeting.getContacts();
                // Check if this meeting has this contact in.
                Long numberOfContactsFound = contacts.stream().filter(c -> c.getId() == contact.getId() ).count();
                  if ( numberOfContactsFound == 1 ) finalFutureMeetingList.add(meeting);
            }
        }

        // Sort the list.
        Collections.sort(finalFutureMeetingList, new MeetingComparator());

        return finalFutureMeetingList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getFutureMeetingList(Calendar date) {
        List<Meeting> finalMeetingList = new LinkedList<Meeting>();

        // Ensure that if date is null, an empty list is returned
        if ( date == null ) return finalMeetingList;

        for( Meeting meeting : meetingList ) {
            // Check if meeting is set after given date
            if ( meeting.getDate().after(date) ) finalMeetingList.add(meeting);
        }

        // Get the list sorted by date.
        Collections.sort(finalMeetingList,new MeetingComparator());

        return finalMeetingList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PastMeeting> getPastMeetingList(Contact contact) {
        // If no contact given, return empty list.
        if ( contact == null ) return new LinkedList<PastMeeting>();

        // If contact does not exist, throw exception
        if ( contactList.stream().filter(c -> c.getId() == contact.getId()).count() == 0 ) 
            throw new IllegalArgumentException("Unknown contact id: " + contact.getId());

        // The final returning List object
        List<PastMeeting> finalMeetingList = new LinkedList<PastMeeting>();

        // Go over each meeting and check if the above contact exists in it.
        for(Meeting meeting : meetingList ) {
            // If this is not a PastMeeting, skip to next.
            if ( !PastMeeting.class.isInstance(meeting)) continue;

            // Check if we have the expected contact in this meeting
            Set<Contact> contactList = meeting.getContacts();
            if ( contactList.stream().filter(c -> c.getId() == contact.getId()).count() == 1 ) {
                // Found the contact id in the list of contacts for this meeting, add it to the final list.
                finalMeetingList.add((PastMeeting)meeting);
            }
        }

        Collections.sort(finalMeetingList, new MeetingComparator());

        return finalMeetingList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) { 
        // Check if contacts set is null
        if ( contacts == null ) throw new NullPointerException("No contacts supplied.");

        // Check if date is null
        if ( date == null ) throw new NullPointerException("No date supplied.");

        // Check if text null
        if ( text == null ) throw new NullPointerException("No notes supplied.");

        // Check if contacts set is empty
        if ( contacts.size() == 0 ) throw new IllegalArgumentException("Empty set of contacts.");

        // Check all contacts to see if any does not exist
        for(Contact contact : contacts ) {
            if( !hasContact(contact.getId()) ) 
                throw new IllegalArgumentException("Unknown contact id: " + contact.getId());
        }

        // Create a new past meeting
        PastMeeting pastMeeting = new PastMeetingImpl(contactId, date, contacts, text);

        // Increment the internal contact id count.
        contactId++;

        // Add the new meeting in
        meetingList.add(pastMeeting);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMeetingNotes(int id, String text) {
        // Throw exception if notes are null
        if ( text == null ) 
            throw new NullPointerException("No text supplied.");

        // Get the respective meeting object for the given meeting id
        Meeting meeting = getMeeting(id);

        // Meeting must exist or throw exception.
        if ( meeting == null ) 
            throw new IllegalArgumentException("No meeting found with id: " + id);

        // Get the meeting's date.
        Calendar meetingDate = meeting.getDate();

        // Not allowed to add notes to a meeting set in the future.
        if ( meetingDate.after(Calendar.getInstance())) 
            throw new IllegalStateException("Cannot add notes to a meeting set in the future.");

        // Get the contacts
        Set<Contact> meetingContacts = meeting.getContacts();

        // Create the new PastMeeting with the notes
        PastMeeting newPastMeeting = new PastMeetingImpl(id, meetingDate, meetingContacts, text);

        // When adding notes to a meeting, it becomes a PastMeeting.
        // Remove old meeting and add the new PastMeeting.
        meetingList.remove(meeting);
        meetingList.add(newPastMeeting);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNewContact(String name, String notes) {
        if ( notes == null ) throw new NullPointerException("No notes supplied.");
        if ( name == null )  throw new NullPointerException("No name supplied.");
        Contact contact = new ContactImpl(this.contactId+1, name, notes);
        this.contactId++;
        contactList.add(contact);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Contact> getContacts(int... ids) {
        Set<Contact> result = new HashSet<Contact>();
        for( int i : ids ) {
            List<Contact> list = contactList.stream()
                .filter(contact -> contact.getId() == i )
                .collect(Collectors.toList());

            for( Contact c : list ) {
                result.add(c);
            }
        }

        // If no result is found, must throw exception as per interface
        if (result.size() == 0) throw new IllegalArgumentException("No contact found.");

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Contact> getContacts(String name) {
        if ( name == null ) throw new NullPointerException("No name supplied.");
        return contactList.stream()
                .filter(contact -> contact.getName().contains(name))
                .collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() {
        try {
            saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ***************************************************************************** //
    // *                             PRIVATE METHODS                               * //
    // ***************************************************************************** //

    /**
     * Shutdown hook to always call flush() at exit.
     */
    private void addShutdownHook() {
        // Set a ShutdownHook to call flush() at exit.
        Runtime.getRuntime().addShutdownHook(
            new Thread(
                new Runnable() {
                    public void run() {
                        flush();
                    }
                }
            )
        );
    }

    /**
     * Checks if a contact Id has already been added to the contact list
     */
    private boolean hasContact(int contactId) {
        if ( contactId == 0 ) return false;

        Long hasContact = contactList.stream()
                .filter(contact->contact.getId()==contactId).count();

        if ( hasContact > 0 ) return true;

        return false;
    }

    /**
     * Checks if a meeting Id has already been added to the meeting list
     */
    private boolean hasMeeting(int meetingId) {
        if ( meetingId == 0 ) return false;

        Long hasMeeting = meetingList.stream()
                .filter(meeting->meeting.getId() == meetingId).count();

        if ( hasMeeting > 0 ) return true;

        return false;
    }

    /**
     * Loads all data from the file into memory.
     * 
     * If the file does not exists, creates one.
     */
    private void loadData() throws ParseException, IOException {
        // Create a new instance of the JUtils.
        JSONUtils jUtils = new JSONUtilsImpl();

        // Create a local file handler
        File file = new File(filePathName);

        // If no file, nothing to be loaded.
        if ( !file.exists() ) return;

        // Prepare to load from file.
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            String line;
            while((line = in.readLine()) != null ) {
                JSONParser parser = new JSONParser();
                JSONObject jo     = (JSONObject) parser.parse(line);

                // If the json object is not null, collect the key values and then 
                // loop through them, converting the values into the expected object form
                @SuppressWarnings("rawtypes")
                Iterator joIterator = jo.keySet().iterator();

                // Loop through all available keys to load from file.
                while( joIterator.hasNext()) {

                    // Get the next key
                    String joKey = joIterator.next().toString();

                    // Grab the value associated to the above key
                    JSONArray joArray = (JSONArray) parser.parse(jo.get(joKey).toString());

                    // Instantiate a new JSON Object element
                    JSONObject element = new JSONObject();

                    // Switch for all possible expected Object keys to process data
                    // e.g.: Contact Object, Meeting Objects
                    switch (joKey) {

                        // Check if found joKey is a CONTACT_KEY
                        case CONTACT_KEY : {
                           // Loop through all elements and load them into the contactList.
                           for( int i = 0; i < joArray.size(); i++ ) {
                               element = (JSONObject) joArray.get(i);
                               // Do not add multiple contacts of the same id
                               Contact c = jUtils.toContact(element);
                               if( !hasContact(c.getId()) ) {
                                   contactList.add(jUtils.toContact(element));
                                   contactId++;
                               }
                           }
                           break;
                        }
                        // Check if the found joKey is a MEETING_KEY
                        case MEETING_KEY : {
                            // Loop through all elements and load them into the meetingList.
                            for( int i = 0; i < joArray.size(); i++ ) {
                                element = (JSONObject) joArray.get(i);
                                String meetingType = element.get("type").toString();
                                switch (meetingType) {
                                    case TYPE_MEETING : {
                                        Meeting m = jUtils.toMeeting(element);
                                        if( !hasMeeting(m.getId()) ) {
                                            meetingList.add(jUtils.toMeeting(element));
                                            meetingId++;
                                        }
                                        break;
                                    }
                                    case TYPE_PAST_MEETING : {
                                        PastMeeting m = jUtils.toPastMeeting(element);
                                        if( !hasMeeting(m.getId()) ) {
                                            meetingList.add(jUtils.toPastMeeting(element));
                                            meetingId++;
                                        }
                                        break;
                                    }
                                    case TYPE_FUTURE_MEETING : {
                                        FutureMeeting m = jUtils.toFutureMeeting(element);
                                        if( !hasMeeting(m.getId()) ) {
                                            meetingList.add(jUtils.toFutureMeeting(element));
                                            meetingId++;
                                        }
                                        break;
                                    }
                                    default : {
                                        throw new IllegalArgumentException("Meeting type not known: " + meetingType);
                                    }
                                }
                            }
                            break;
                        }
                        default : {
                            // If list key is defined in class and found in file but not found code to deal with it,
                            // this is an alert will tell the developer to implement this part of the code. 
                            System.out.println("'" + joKey + "' is not implemented.");
                        }
                    }
                }
            }

            // Close Buffer if not closed yet.
            if ( in != null ) in.close();

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves all data from memory into file.
     * 
     * If the file does not exists, creates one.
     */
    @SuppressWarnings("unchecked")
    private void saveData() throws IOException {

        // Create a local File handler
        File file = new File(filePathName);

        // Create a new file if the given does not exist.
        if ( !file.exists() ) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            // If file exists, deletes it and creates a new one, to be sure we have a new clean file.
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Final JSONObject to save into file
        JSONObject jFinal = new JSONObject();
        JSONUtils jUtils = new JSONUtilsImpl();

        // Save all contacts into jContactsArray
        Iterator<Contact> contactsIterator = contactList.iterator();
        JSONArray jContactsArray = new JSONArray();
        while( contactsIterator.hasNext() ) {
            jContactsArray.add(jUtils.toJSONObject(contactsIterator.next()));
        }

        // Finalise Contacts.
        jFinal.put(CONTACT_KEY,jContactsArray);

        // Save all meetings into jMeetingsArray
        JSONArray jMeetingsArray = new JSONArray();
        for( Meeting meeting : meetingList ) {
            jMeetingsArray.add(jUtils.toJSONObject(meeting));
        }

        // Finalise Meetings.
        jFinal.put(MEETING_KEY,jMeetingsArray);

        // Save all into file.
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            fw.write(jFinal.toJSONString());
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Ensure the file handler is closed at exception
            if ( fw != null ) fw.close();
        }

        // Ensure handler is closed in the end.
        fw.close();
    }
}
