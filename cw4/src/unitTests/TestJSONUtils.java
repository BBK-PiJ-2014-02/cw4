package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import contactManager.FutureMeeting;
import contactManager.JSONUtils;
import contactManager.JSONUtilsImpl;
import contactManager.Meeting;
import contactManager.MeetingImpl;
import contactManager.PastMeeting;

public class TestJSONUtils {
    /**
     * The JSONUtils object handler.
     */
    private JSONUtils jUtils;
    
    /**
     * Calendar date to convert to JSONObject.
     */
    private Calendar date;

    /**
     * Contact to convert to JSONObject.
     */
    private Contact contact;
    
    /**
     * Meeting to convert to JSONObject.
     */
    private Meeting meeting;

    /**
     * PastMeeting to convert to JSONObject.
     */
    private PastMeeting pastMeeting;

    /**
     * FutureMeeting to convert to JSONObject.
     */
    private FutureMeeting futureMeeting;

    /**
     * The JSONObject with the date information.
     */
    private JSONObject jDate;
    
    /**
     * The JSONObject with the contact information.
     */
    private JSONObject jContact;
    
    /**
     * The JSONObject with the meeting information.
     */
    private JSONObject jMeeting;

    /**
     * The JSONObject with the past meeting information.
     */
    private JSONObject jPastMeeting;

    /**
     * The JSONObject with the future meeting information.
     */
    private JSONObject jFutureMeeting;

    /**
     * Calendar Year.
     */
    private final Integer YEAR = 2020;

    /**
     * Calendar Month.
     */
    private final Integer MONTH = 8;

    /**
     * Calendar Day.
     */
    private final Integer DAY = 24;

    /**
     * Calendar Hour.
     */
    private final Integer HOUR = 23;

    /**
     * Calendar Minute.
     */
    private final Integer MINUTE = 21;

    /**
     * Calendar Second.
     */
    private final Integer SECOND = 42;

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
     * The past meeting id.
     */
    private final Integer MEETING_ID_PAST = 12;

    /**
     * The future meeting id.
     */
    private final Integer MEETING_ID_FUTURE = 1222;

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
        // Initialise date.
        date = new GregorianCalendar();
        date.set(YEAR,MONTH,DAY,HOUR,MINUTE,SECOND);

        // Initialize jDate
        jDate = new JSONObject();
        jDate.put("year", YEAR);
        jDate.put("month", MONTH);
        jDate.put("day", DAY);
        jDate.put("hour", HOUR);
        jDate.put("minute", MINUTE);
        jDate.put("second", SECOND);
        
        // Initialise the contact with any same contact info
        contact = new ContactImpl(CONTACT_ID, CONTACT_NAME, CONTACT_NOTES);

        // Set a list of contacts to be given to a meeting with
        // only the one contact we have to test, 
        // and create a meeting with the above set info.
        MEETING_CONTACTS = new HashSet<Contact>();
        MEETING_CONTACTS.add(contact);
        try {
            meeting = new MeetingImpl(MEETING_ID, date, MEETING_CONTACTS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Set the structure for the JSONObject type contact
        jContact = new JSONObject();
        jContact.put("id", CONTACT_ID);
        jContact.put("name", CONTACT_NAME);
        jContact.put("notes", CONTACT_NOTES);
                
        // Set the structure for the JSONObject type Meeting
        jMeeting = new JSONObject();
        jMeeting.put("type", "Meeting");
        jMeeting.put("id", MEETING_ID);
        jMeeting.put("date", jDate);

        JSONArray jA = new JSONArray();
                  jA.add(jContact);

        jMeeting.put("contacts", jA);

        // Set the structure for the JSONObject type PastMeeting
        jPastMeeting = new JSONObject();
        jPastMeeting.put("type", "PastMeeting");
        jPastMeeting.put("id", MEETING_ID);
        jPastMeeting.put("date", jDate);

        jPastMeeting.put("contacts", jA);

        // Set the structure for the JSONObject type FutureMeeting
        jFutureMeeting = new JSONObject();
        jFutureMeeting.put("type", "FutureMeeting");
        jFutureMeeting.put("id", MEETING_ID);
        jFutureMeeting.put("date", jDate);

        jFutureMeeting.put("contacts", jA);

        // Initialise the JSONUtils class object.
        jUtils = new JSONUtilsImpl();
    }

    /**
     * Test if given Contact is correctly converted to a JSONObject.
     */
    @Test
    public void testToJSONObjectFromContact() { 
        JSONObject foundJO = jUtils.toJSONObject(contact);
        assertEquals(jContact,foundJO);
    }

    /**
     * Test if given Meeting is correctly converted to a JSONObject.
     */
    @Test
    public void testToJSONObjectFromMeeting() {
        JSONObject foundJO = jUtils.toJSONObject(meeting);
        assertEquals(jMeeting,foundJO);
    }

    /**
     * Given a JSONObject with Contact values, check if the returned
     * matches the original expected Contact.
     */
    @Test
    public void testToContactFromJSON() {
        Contact foundContact = jUtils.toContact(jContact);
        verifyContact(contact,foundContact);
    }

    /**
     * Given a JSONObject with Meeting values, check if the returned
     * matches the original expected Meeting.
     */
    @Test
    public void testToMeetingFromJSON() {
        Meeting meetingFound = jUtils.toMeeting(jMeeting);
        verifyMeetings(meeting,meetingFound);
    }

    /**
     * Given a Calendar date, check if the returned Object
     * matched the expected.
     */
    @Test
    public void testToJSONObjectFromCalendar() {
        JSONObject foundJO = jUtils.toJSONObject(date);
        verifyDate(jDate, foundJO);
    }

    /**
     * Given a JSON Object of type calendar, check that
     * the returned Calendar object matches expected.
     */
    @Test
    public void testToCalendarFromJSON() {
        Calendar found = jUtils.toCalendar(jDate);
        verifyDate(date, found);
    }

    /**
     * Asserting that the information is the same, regardless of the object address.
     * 
     * @param expected Contact
     * @param found Contact
     */
    private void verifyContact(Contact expected, Contact found) {
       assertNotNull(found);
       assertEquals(expected.getId(),found.getId());
       assertEquals(expected.getName(),found.getName());
       assertEquals(expected.getNotes(),found.getNotes());
    }

    /**
     * Asserting that the expected list of contacts matches the found list of contacts 
     * in same order and contents.
     * 
     * @param expected Contacts
     * @param found Contacts
     */
    private void verifyContactList(Set<Contact> expected, Set<Contact> found) {
        assertNotNull(found);
        assertTrue(expected.size() >= found.size());

        // Ensure we have one and only one of the expected contacts in the found list,
        // matching on id, name and notes.
        for( Contact expectedContact : expected ) {
            assertTrue(found.stream()
                    .filter( contact -> 
                        contact.getId() == expectedContact.getId() && 
                        contact.getName().equals(expectedContact.getName()) &&
                        contact.getNotes().equals(expectedContact.getNotes()) 
                     ).count() == 1);
        }
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
        verifyDate(expected.getDate(),found.getDate());
        verifyContactList(expected.getContacts(),found.getContacts());
    }

    /**
     * Check that two calendar dates are the same on year, month, day, 
     * hour, minute, second and millisecond.
     * 
     * @param expected Calendar date
     * @param found Calendar date
     */
    private void verifyDate(Calendar expected, Calendar found) {
        assertNotNull(found);
        assertEquals(expected.get(Calendar.YEAR),found.get(Calendar.YEAR));
        assertEquals(expected.get(Calendar.MONTH),found.get(Calendar.MONTH));
        assertEquals(expected.get(Calendar.DAY_OF_MONTH),found.get(Calendar.DAY_OF_MONTH));
        assertEquals(expected.get(Calendar.HOUR),found.get(Calendar.HOUR));
        assertEquals(expected.get(Calendar.MINUTE),found.get(Calendar.MINUTE));
        assertEquals(expected.get(Calendar.SECOND),found.get(Calendar.SECOND));
    }

    /**
     * Check that two JSONObjects dates are the same on year, month, day, 
     * hour, minute, second and millisecond.
     * 
     * @param expected JSONObject date
     * @param found JSONObject date
     */
    private void verifyDate(JSONObject expected, JSONObject found) {
        assertNotNull(found);
        assertEquals(expected.get("year"),found.get("year"));
        assertEquals(expected.get("month"),found.get("month"));
        assertEquals(expected.get("day"),found.get("day"));
        assertEquals(expected.get("hour"),found.get("hour"));
        assertEquals(expected.get("minute"),found.get("minute"));
        assertEquals(expected.get("second"),found.get("second"));
    }
}
