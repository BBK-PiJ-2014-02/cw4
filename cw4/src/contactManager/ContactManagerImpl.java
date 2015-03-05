package contactManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
     * The default data file name where all data is stored.
     */
    private final String fileName = "contacts.txt";

    /**
     * File handler
     */
    private final File file;
    
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
        // Initialise the file handler
        this.file = new File(this.fileName);
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
        this.file = new File(fileName);
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
    @Override
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public PastMeeting getPastMeeting(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FutureMeeting getFutureMeeting(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Meeting getMeeting(int id) {
        return null;
    }

    @Override
    public List<Meeting> getFutureMeetingList(Contact contact) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Meeting> getFutureMeetingList(Calendar date) {
        return null;
    }

    @Override
    public List<PastMeeting> getPastMeetingList(Contact contact) {
        return null;
    }

    @Override
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) { 
    }

    @Override
    public void addMeetingNotes(int id, String text) {}

    @Override
    public void addNewContact(String name, String notes) {
        Contact contact = new ContactImpl(this.contactId+1, name, notes);
        this.contactId++;
        contactList.add(contact);
    }

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
        return result;
    }

    @Override
    public Set<Contact> getContacts(String name) {
        return contactList.stream()
                .filter(contact -> contact.getName().equals(name))
                .collect(Collectors.toSet());
    }

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
     * Loads all data from the file into memory.
     * 
     * If the file does not exists, creates one.
     */
    private void loadData() throws ParseException, IOException {
        // If no file, nothing to be loaded.
        if ( ! this.file.exists() ) return;

        // Prepare to load from file.
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            String line;
            while((line = in.readLine()) != null ) {
                JSONParser parser = new JSONParser();
                JSONObject jo     = (JSONObject) parser.parse(line);
                // If the json object is null, go to next line.
                if ( jo == null ) continue;

                // If the json object is not null, collect the key values and then 
                // loop through them, converting the values into the expected object form
                @SuppressWarnings("rawtypes")
                Iterator joIterator = jo.keySet().iterator();

                // Loop through all available keys to load from file.
                while( joIterator.hasNext()) {

                    // Get the next key
                    String joKey = joIterator.next().toString();

                    // Grab the value associated to the above key
                    JSONArray joArray = (JSONArray) jo.get(joKey);

                    // No need for further processing if nothing found
                    if ( joArray == null ) continue;

                    // Instantiate a new JSON Object element
                    JSONObject element = new JSONObject();

                    // Switch for all possible expected keys to process data
                    switch (joKey) {

                        // Check if found joKey is a CONTACT_KEY
                        case CONTACT_KEY : {
                           // Loop through all elements and load them into the contactList.
                           for( int i = 0; i < joArray.size(); i++ ) {
                               element = (JSONObject) joArray.get(i);
                               contactList.add(toContact(element));
                           }
                           break;
                        }
                        // Check if the found joKey is a MEETING_KEY
                        case MEETING_KEY : {
                            // Loop through all elements and load them into the meetingList.
                            for( int i = 0; i < joArray.size(); i++ ) {
                                element = (JSONObject) joArray.get(i);
                                meetingList.add(toMeeting(element));
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
     * Converts given JSONObject with expected Contact labelled info, into a Meeting Object
     * 
     * @param jsonObject the JSON object
     * @return Meeting object
     */
    private Meeting toMeeting(JSONObject jsonObject) {
        return null;
    }
    
    /**
     * Converts given JSONObject with expected Contact labelled info into a Contact
     * 
     * @param jsonObject the JSON object
     * @return Contact object
     */
    private Contact toContact(JSONObject jsonObject) {
        // Contact needs id, name and notes
        String name  = jsonObject.get("name").toString();
        String notes = jsonObject.get("notes").toString();
        Integer id   = Integer.valueOf(jsonObject.get("id").toString());

        // Now that we have all elements, create a new Contact with them
        Contact newContact = new ContactImpl(id, name, notes);

        return newContact;
    }

    /**
     * Converts given Meeting object into a JSONObject
     * 
     * @param contact the Meeting object
     * @return JSONObject
     */
    @SuppressWarnings("unchecked")
    private JSONObject toJSONObject(Meeting meeting) {
        // The final JSONObject to be returned.
        JSONObject jsonObject = new JSONObject();

        // If meeting is null, just return an empty JSONObject
        if ( meeting == null ) return jsonObject;

        // Constructing the Map to add.
        Map<String,String> meetingMap = new HashMap<String, String>();
        meetingMap.put("id", ( new Integer(meeting.getId()) ).toString() );

        JSONArray jContacts = new JSONArray();
        for( Contact contact : meeting.getContacts() ) {
            Map<String,String> mContact = new HashMap<String, String>();
            mContact.put("id", (Integer.valueOf(contact.getId())).toString());
            mContact.put("name", contact.getName());
            mContact.put("notes", contact.getNotes());
            jContacts.add(mContact);
        }

        meetingMap.put("date", ( meeting.getDate() ).toString());
        meetingMap.put("contacts", jContacts.toJSONString());

        // Adding the constructed map.
        jsonObject.putAll(meetingMap);

        // Returning the constructed JSONObject.
        return jsonObject;
    }

    /**
     * Converts given Contact object into a JSONObject
     * 
     * @param contact the Contact object
     * @return JSONObject
     */
    @SuppressWarnings("unchecked")
    private JSONObject toJSONObject(Contact contact) {
        // The final JSONObject to be returned.
        JSONObject jsonObject = new JSONObject();

        // Constructing the Map to add.
        Map<String,String> contactMap = new HashMap<String, String>();
        contactMap.put("id", ( new Integer(contact.getId()) ).toString() );
        contactMap.put("name", contact.getName());
        contactMap.put("notes", contact.getNotes());

        // Adding the constructed map.
        jsonObject.putAll(contactMap);

        // Returning the constructed JSONObject.
        return jsonObject;
    }

    /**
     * Saves all data from memory into file.
     * 
     * If the file does not exists, creates one.
     */
    @SuppressWarnings("unchecked")
    private void saveData() throws IOException {
        // Create a new file if the given does not exist.
        if ( ! this.file.exists() ) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            // If file exists, deletes it and creates a new one, to be sure we have a new clean file.
            this.file.delete();
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Final JSONObject to save into file
        JSONObject jFinal = new JSONObject();

        // Save all contacts into jContactsArray
        Iterator<Contact> contactsIterator = contactList.iterator();
        JSONArray jContactsArray = new JSONArray();
        while( contactsIterator.hasNext() ) {
            jContactsArray.add(toJSONObject(contactsIterator.next()));
        }

        // Finalise Contacts.
        jFinal.put(CONTACT_KEY,jContactsArray);

        // Save all meetings into jMeetingsArray
        JSONArray jMeetingsArray = new JSONArray();
        for( Meeting m : meetingList ) {
            jMeetingsArray.add(toJSONObject(m));
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
