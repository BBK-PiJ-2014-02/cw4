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
import contactManager.FutureMeetingImpl;
import contactManager.JSONUtils;
import contactManager.JSONUtilsImpl;
import contactManager.Meeting;
import contactManager.MeetingImpl;
import contactManager.PastMeeting;
import contactManager.PastMeetingImpl;

public class TestJSONUtils {
    /**
     * The PastMeeting type.
     */
    private final String TYPE_PAST_MEETING = "PastMeeting";

    /**
     * The Meeting type.
     */
    private final String TYPE_MEETING = "Meeting";
    
    /**
     * The FutureMeeting type.
     */
    private final String TYPE_FUTURE_MEETING = "FutureMeeting";

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
     * The Past meeting id.
     */
    private final Integer MEETING_ID_PAST = 1;

    /**
     * The meeting id.
     */
    private final Integer MEETING_ID = 2;

    /**
     * The Future meeting id.
     */
    private final Integer MEETING_ID_FUTURE = 3;

    /**
     * The meeting notes.
     */
    private final String MEETING_NOTES = "Meeting notes";

    /**
     * The meeting contacts.
     */
    private Set<Contact> contacts;

    /**
     * The JSONUtils object handler.
     */
    private JSONUtils jUtils;


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
        contacts = new HashSet<Contact>();
        contacts.add(contact);
        try {
            meeting = new MeetingImpl(MEETING_ID, date, contacts);
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
        jMeeting.put("type", TYPE_MEETING);
        jMeeting.put("id", MEETING_ID);
        jMeeting.put("date", jDate);

        JSONArray jA = new JSONArray();
                  jA.add(jContact);

        jMeeting.put("contacts", jA);

        // Set the structure for the JSONObject type PastMeeting
        jPastMeeting = new JSONObject();
        jPastMeeting.put("type", TYPE_PAST_MEETING);
        jPastMeeting.put("id", MEETING_ID_PAST);
        jPastMeeting.put("date", jDate);
        jPastMeeting.put("notes", MEETING_NOTES);

        jPastMeeting.put("contacts", jA);

        // Set the structure for the JSONObject type FutureMeeting
        jFutureMeeting = new JSONObject();
        jFutureMeeting.put("type", TYPE_FUTURE_MEETING);
        jFutureMeeting.put("id", MEETING_ID_FUTURE);
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
     * Given a JSONObject with Contact values, check if the returned
     * matches the original expected Contact.
     */
    @Test
    public void testToContactFromJSON() {
        Contact foundContact = jUtils.toContact(jContact);
        verifyContact(contact,foundContact);
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
     * Given a JSONObject with Meeting values, check if the returned
     * matches the original expected Meeting.
     */
    @Test
    public void testToMeetingFromJSON() {
        Meeting meetingFound = jUtils.toMeeting(jMeeting);
        verifyMeetings(meeting,meetingFound);
    }

    /**
     * Test if given Meeting is correctly converted to a JSONObject.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testToJSONObjectFromPastMeeting() {
        Set<Contact> contacts = new HashSet<Contact>();
        contacts.add(contact);
        PastMeeting pastMeeting = new PastMeetingImpl(MEETING_ID_PAST, date, contacts, MEETING_NOTES);
        JSONObject foundJO = jUtils.toJSONObject(pastMeeting);

        // Set the structure for the JSONObject type PastMeeting
        JSONObject expectedMeeting = new JSONObject();
        expectedMeeting.put("type", TYPE_PAST_MEETING);
        expectedMeeting.put("id", MEETING_ID_PAST);
        expectedMeeting.put("date", jDate);
        expectedMeeting.put("notes", MEETING_NOTES);

        JSONArray jA = new JSONArray();
                  jA.add(jUtils.toJSONObject(contact));

        expectedMeeting.put("contacts", jA);

        assertEquals(expectedMeeting,foundJO);
    }

    /**
     * Given a JSONObject with PastMeeting values, check if the returned
     * matches the original expected PastMeeting.
     */
    @Test
    public void testToPastMeetingFromJSON() {
        // The expected PastMeeting Object;
        PastMeeting expectedPastMeeting = new PastMeetingImpl(MEETING_ID_PAST, date, contacts, MEETING_NOTES);
        PastMeeting pastMeetingFound = jUtils.toPastMeeting(jPastMeeting);

        // Assert this was not returned null
        assertNotNull(pastMeetingFound);

        // Verify that both Past meetings as the same
        verifyMeetings(expectedPastMeeting, pastMeetingFound);
    }

    /**
     * Test if given FutureMeeting is correctly converted to a JSONObject.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testToJSONObjectFromFutureMeeting() {
        Set<Contact> contacts = new HashSet<Contact>();
        contacts.add(contact);
        FutureMeeting futureMeeting = new FutureMeetingImpl(12, date, contacts);
        JSONObject foundJO = jUtils.toJSONObject(futureMeeting);

        // Set the structure for the JSONObject type FutureMeeting
        JSONObject expectedMeeting = new JSONObject();
        expectedMeeting.put("type", TYPE_FUTURE_MEETING);
        expectedMeeting.put("id", 12);
        expectedMeeting.put("date", jDate);

        JSONArray jA = new JSONArray();
                  jA.add(jUtils.toJSONObject(contact));

        expectedMeeting.put("contacts", jA);

        assertEquals(expectedMeeting,foundJO);
    }

    /**
     * Given a JSONObject with Meeting values, check if the returned
     * matches the original expected Meeting.
     */
    @Test
    public void testToFutureMeetingFromJSON() {
        // The expected FutureMeeting Object;
        FutureMeeting expectedFutureMeeting = new FutureMeetingImpl(MEETING_ID_FUTURE, date, contacts);
        FutureMeeting futureMeetingFound = jUtils.toFutureMeeting(jFutureMeeting);

        // Assert this was not returned null
        assertNotNull(futureMeetingFound);

        // Verify that both Future meetings as the same
        verifyMeetings(expectedFutureMeeting, futureMeetingFound);
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
     * Testing exception on conversion to PastMeeting 
     * when JObject is not with type PastMeeting
     */
    @Test(expected=IllegalArgumentException.class)
    public void testExceptionJSONToPastMeeting() {
        jUtils.toPastMeeting(jFutureMeeting);
    }

    /**
     * Testing exception on conversion to Meeting 
     * when JObject is not with type Meeting
     */
    @Test(expected=IllegalArgumentException.class)
    public void testExceptionJSONToMeeting() {
        jUtils.toMeeting(jFutureMeeting);
    }

    /**
     * Testing exception on conversion to FutureMeeting 
     * when JObject is not with type FutureMeeting
     */
    @Test(expected=IllegalArgumentException.class)
    public void testExceptionJSONToFutureMeeting() {
        jUtils.toFutureMeeting(jMeeting);
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
     * Asserting that the information on the PastMeetings match.
     * 
     * @param expected PastMeeting
     * @param found PastMeeting
     */
    private void verifyMeetings(PastMeeting expected, PastMeeting found) {
        assertNotNull(found);
        assertEquals(expected.getId(),found.getId());
        verifyDate(expected.getDate(),found.getDate());
        verifyContactList(expected.getContacts(),found.getContacts());
        assertEquals(expected.getNotes(),found.getNotes());
    }

    /**
     * Asserting that the information on the FutureMeetings match.
     * 
     * @param expected FutureMeeting
     * @param found FutureMeeting
     */
    private void verifyMeetings(FutureMeeting expected, FutureMeeting found) {
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
