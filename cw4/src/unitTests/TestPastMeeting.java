package unitTests;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import contactManager.Contact;
import contactManager.ContactImpl;
import contactManager.PastMeeting;
import contactManager.PastMeetingImpl;

/**
 * Unit test for the PastMeeting class implementation.
 * 
 * Testing also Meeting implementations that may have been overridden 
 * on this PastMeeting implementation.
 * 
 * @author Vasco
 *
 */
public class TestPastMeeting {
    /**
     * The past meeting implementation class object to test with.
     */
    private PastMeeting pastMeeting;
    
    /**
     * PastMeeting's Id.
     */
    private int ID = 123;
    
    /**
     * PastMeeting's date.
     */
    private Calendar DATE;
    
    /**
     * PastMeeting's list of contacts.
     */
    private Set<Contact> contactList;
    
    /**
     * PastMeeting's notes.
     */
    private String NOTES = "Some notes";

    /**
     * Needed initialisations before each test.
     * 
     * @throws Exception 
     */
    @Before
    public void before() {
        // Initialise DATE
        DATE = new GregorianCalendar();
        DATE.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND);

        // Add some random contacts expected to be returned later
        contactList = new HashSet<Contact>();
        contactList.add(new ContactImpl(0, "First contact", null));
        contactList.add(new ContactImpl(1, "Second contact", NOTES));

        // Initialise meeting
        pastMeeting = new PastMeetingImpl(ID, DATE, contactList, NOTES);
    }
    
    /**
     * Testing getId.
     */
    @Test
    public void testGetId() {
        int foundId = pastMeeting.getId();
        assertEquals(ID,foundId);
    }

    /**
     * Testing getContacts.
     */
    @Test
    public void testGetContacts() {
        Set<Contact> contactsFound = pastMeeting.getContacts();
        assertEquals(contactList,contactsFound);
    }
    
    /**
     * Testing getDate.
     */
    @Test
    public void testGetDate() {
        Calendar dateFound = pastMeeting.getDate();
        assertEquals(DATE,dateFound);
    }

    /**
     * Testing null notes.
     */
    @Test
    public void testNullNotes() {
        PastMeeting pm = new PastMeetingImpl(ID, DATE, contactList, null);
        String foundNotes = pm.getNotes();
        String expectedNotes = "";
        assertEquals(expectedNotes,foundNotes);
    }

    /**
     * Testing getNotes().
     */
    @Test
    public void testNotes() {
        String foundNotes = pastMeeting.getNotes();
        assertEquals(NOTES, foundNotes);
    }
    
}
