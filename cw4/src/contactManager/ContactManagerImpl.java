package contactManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
     * The next Contact Id
     */
    private Integer contactId;

    /**
     * The known Contact list
     */
    private Set<Contact> contactList;

    /**
     * The next Meeting Id
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
        return 0;
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
        if ( meetingDate.after(Calendar.getInstance()) ) throw new IllegalArgumentException();

        // Get contacts
        Set<Contact> meetingContacts = meeting.getContacts();

        // meetingNotes if we are dealing with a PastMeeting
        String meetingNotes = "";

        // Check if we have a PastMeeting and pick existing notes if so.
        if ( meeting.getClass().getSimpleName().equals(PastMeetingImpl.class.getSimpleName())) {
            PastMeeting pastMeeting = (PastMeeting) getMeeting(id);
            meetingNotes = pastMeeting.getNotes();
        }

        // Create a PastMeeting
        PastMeeting pastMeeting = new PastMeetingImpl(id, meetingDate, meetingContacts, meetingNotes);

        return pastMeeting;
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
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getFutureMeetingList(Calendar date) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PastMeeting> getPastMeetingList(Contact contact) {
        // If no contact given, return empty list.
        if ( contact == null ) return new LinkedList<PastMeeting>();

        // If contact does not exist, throw exception
        if ( contactList.stream().filter(c -> c.getId() == contact.getId()).count() == 0 ) throw new IllegalArgumentException();

        // The final returning List object
        List<PastMeeting> finalMeetingList = new LinkedList<PastMeeting>();

        // Go over each meeting and check if the above contact exists in it.
        for(Meeting meeting : meetingList ) {
            // If this is not a PastMeeting, skip to next.
            if ( !meeting.getClass().getSimpleName().equals(PastMeetingImpl.class.getSimpleName())) continue;

            // Check if we have the expected contact in this meeting
            Set<Contact> contactList = meeting.getContacts();
            if ( contactList.stream().filter(c -> c.getId() == contact.getId()).count() > 0 ) {
                // Found the contact id in the list of contacts for this meeting, add it to the final list.
                finalMeetingList.add((PastMeeting)meeting);
            }
        }

        Collections.sort(finalMeetingList, new PastMeetingComparator());

        return finalMeetingList;
    }

    class PastMeetingComparator implements Comparator<PastMeeting> {
        @Override
        public int compare(PastMeeting o1, PastMeeting o2) {
            if ( o1.getId() < o2.getId() ) return 1;
            if ( o1.getId() > o2.getId() ) return -1;
            return 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) { 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMeetingNotes(int id, String text) {
        // Throw exception if notes are null
        if ( text == null ) throw new NullPointerException();

        // Get the respective meeting object for the given meeting id
        Meeting meeting = getMeeting(id);

        // Meeting must exist or throw exception.
        if ( meeting == null ) throw new IllegalArgumentException();

        // Get the meeting's date.
        Calendar meetingDate = meeting.getDate();

        // Not allowed to add notes to a meeting set in the future.
        if ( meetingDate.after(Calendar.getInstance())) throw new IllegalStateException();

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
        if ( notes == null ) throw new NullPointerException();
        if ( name == null ) throw new NullPointerException();
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
        if (result.size() == 0) throw new IllegalArgumentException();

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Contact> getContacts(String name) {
        if ( name == null ) throw new NullPointerException();
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

                    // Switch for all possible expected keys to process data
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
                                Meeting m = jUtils.toMeeting(element);
                                if( !hasMeeting(m.getId()) ) {
                                    meetingList.add(jUtils.toMeeting(element));
                                    meetingId++;
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
            if ( fw != null ) fw.close();
        } finally {
            fw.close();
        }
    }
}
