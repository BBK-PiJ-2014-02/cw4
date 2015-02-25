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
 * Please note that this class extends from the Meeting class
 * to which there are already unit tests being done to most methods.
 * 
 * This unit tests aims to test the new not yet tested methods, thus assuming
 * all the Meeting functionality is already covered by the TestMeeting class
 * and followed instructions as per the designed Meeting interface.
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
		contactList.add(new ContactImpl(0, "First contact"));
		contactList.add(new ContactImpl(1, "Second contact"));
		
		// Initialise meeting
		pastMeeting = new PastMeetingImpl(ID, DATE, contactList, NOTES);
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
	 * Testing absence of notes at the constructor level.
	 */
	@Test
	public void testAbsentNotes() {
		PastMeeting pm = new PastMeetingImpl(ID, DATE, contactList);
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
