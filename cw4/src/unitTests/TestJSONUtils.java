package unitTests;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import contactManager.Contact;
import contactManager.ContactImpl;
import contactManager.JSONUtils;
import contactManager.JSONUtilsImpl;
import contactManager.Meeting;
import contactManager.MeetingImpl;

public class TestJSONUtils {
    /**
     * The JSONUtils object handler.
     */
    private JSONUtils jUtils;
    
    /**
     * Contact to convert to JSONObject.
     */
    private Contact contact;
    
    /**
     * Meeting to convert to JSONObject.
     */
    private Meeting meeting;
    
    /**
     * The JSONObject with the contact information.
     */
    private JSONObject jContact;
    
    /**
     * The JSONObject with the meeting information.
     */
    private JSONObject jMeeting;
    
    /**
     * The contact id.
     */
    private final Integer CONTACT_ID = 12;

    /**
     * The contact name.
     */
    private final String CONTACT_NAME = "Contact name";

    /**
     * The contact notes.
     */
    private final String CONTACT_NOTES = "Contact notes";

    /**
     * The meeting id.
     */
    private final Integer MEETING_ID = 122;

    /**
     * The meeting date.
     */
    private Calendar MEETING_DATE;

    /**
     * The meeting contacts.
     */
    private Set<Contact> MEETING_CONTACTS;


    /**
     * Initialise a new fresh instance of the JSONUtils for each new test.
     */
    @SuppressWarnings("unchecked")
    @Before
    public void before() {
        // Initialise the date with any date.
        MEETING_DATE = new GregorianCalendar();
        MEETING_DATE.set(2015, 12, 23);
        
        // Initialise the contact with any same contact info
        contact = new ContactImpl(CONTACT_ID, CONTACT_NAME, CONTACT_NOTES);

        // Set a list of contacts to be given to a meeting with
        // only the one contact we have to test, 
        // and create a meeting with the above set info.
        MEETING_CONTACTS = new HashSet<Contact>();
        MEETING_CONTACTS.add(contact);
        try {
            meeting = new MeetingImpl(MEETING_ID, MEETING_DATE, MEETING_CONTACTS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Set the structure for the JSONObject type contact
        jContact = new JSONObject();
        jContact.put("id", CONTACT_ID);
        jContact.put("name", CONTACT_NAME);
        jContact.put("notes", CONTACT_NOTES);
                
        // Set the structure for the JSONObject type contact
        jMeeting = new JSONObject();
        jMeeting.put("id", MEETING_ID);
        jMeeting.put("date", MEETING_DATE);
        JSONArray jA = new JSONArray();
        jA.add(jContact);
        jMeeting.put("contacts", jA);

        // Initialise the JSONUtils class object.
        jUtils = new JSONUtilsImpl();
    }

    /**
     * Test if given Contact is correctly converted to a JSONObject.
     */
    @Test
    public void testContactToJSONObject() { 
        JSONObject foundJO = jUtils.toJSONObject(contact);
        assertEquals(jContact,foundJO);
    }
    /**
     * Test if given Meeting is correctly converted to a JSONObject.
     */
    @Test
    public void testToMeetingJSONObject() {
        JSONObject foundJO = jUtils.toJSONObject(meeting);
        assertEquals(jMeeting,foundJO);
        
    }
    
    /**
     * Given a JSONObject with Contact values, check if the returned
     * matches the original expected Contact.
     */
    @Test
    public void testJSONObjectToContact() {
        Contact foundContact = jUtils.toContact(jContact);
        verifyContacts(contact,foundContact);
    }

    /**
     * Given a JSONObject with Meeting values, check if the returned
     * matches the original expected Meeting.
     */
    @Test
    public void testJSONObjectToMeeting() {
        Meeting meetingFound = jUtils.toMeeting(jMeeting);
        verifyMeetings(meeting,meetingFound);
    }

    /**
     * Asserting that the information is the same, regardless of the object address.
     * 
     * @param expected Contact
     * @param found Contact
     */
    private void verifyContacts(Contact expected, Contact found) {
       assertNotNull(found);
       assertEquals(expected.getId(),found.getId());
       assertEquals(expected.getName(),found.getName());
       assertEquals(expected.getNotes(),found.getNotes());
    }

    /**
     * Asserting that the information on the Meetings match.
     * 
     * @param expected Meeting
     * @param found Meeting
     */
    private void verifyMeetings(Meeting expected, Meeting found) {
       assertNotNull(found);
       assertEquals(expected.getId(),found.getId());
       assertEquals(expected.getDate(),found.getDate());
       assertEquals(expected.getContacts(),found.getContacts());
    }
}
