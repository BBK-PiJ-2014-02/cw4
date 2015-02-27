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
import contactManager.FutureMeeting;
import contactManager.FutureMeetingImpl;

/**
 * Unit test for the FutureMeeting class implementation.
 * 
 * Testing also Meeting implementations that may have been overridden 
 * on this FutureMeeting implementation.
 * 
 * @author Vasco
 *
 */
public class TestFutureMeeting {
    /**
     * The future meeting implementation class object to test with.
     */
    private FutureMeeting futureMeeting;
    
    /**
     * FutureMeeting's Id.
     */
    private int ID = 123;
    
    /**
     * FutureMeeting's date.
     */
    private Calendar DATE;
    
    /**
     * FutureMeeting's list of contacts.
     */
    private Set<Contact> contactList;
    
    /**
     * The Contact's notes.
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
        futureMeeting = new FutureMeetingImpl(ID, DATE, contactList);
    }
    
    /**
     * Testing getId.
     */
    @Test
    public void testGetId() {
        int foundId = futureMeeting.getId();
        assertEquals(ID,foundId);
    }

    /**
     * Testing getContacts.
     */
    @Test
    public void testGetContacts() {
        Set<Contact> contactsFound = futureMeeting.getContacts();
        assertEquals(contactList,contactsFound);
    }
    
    /**
     * Testing getDate.
     */
    @Test
    public void testGetDate() {
        Calendar dateFound = futureMeeting.getDate();
        assertEquals(DATE,dateFound);
    }
}
