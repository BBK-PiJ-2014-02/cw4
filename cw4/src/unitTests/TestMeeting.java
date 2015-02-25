package unitTests;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import contactManager.Contact;
import contactManager.ContactImpl;
import contactManager.Meeting;
import contactManager.MeetingImpl;

/**
 * Unit tests to test MeetingImpl class
 * 
 * @author Vasco
 *
 */
public class TestMeeting {
	/**
	 * The class object to test with
	 */
	private Meeting meeting;
	
	/**
	 * The meeting ID
	 */
	private final int ID = 123;
	
	/**
	 * The Meeting DATE
	 */
	private Calendar DATE;
	
	/**
	 * The default contact list
	 */
	private Set<Contact> CONTACTS;
	
	/**
	 * Needed initialisations before each test.
	 * @throws Exception 
	 */
	@Before
	public void before() throws Exception {
		// Initialize DATE
		DATE = new GregorianCalendar();
		DATE.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND);
		
		// Add some random contacts expected to be returned later
		CONTACTS = new HashSet<Contact>();
		CONTACTS.add(new ContactImpl(0, "First contact"));
		CONTACTS.add(new ContactImpl(1, "Second contact"));
		
		// Initialize meeting
		meeting = new MeetingImpl(ID, DATE, CONTACTS);
	}

	/**
	 * Testing if the expected Meeting id is returned.
	 */
	@Test
	public void testId() {
		int foundId = meeting.getId();
		assertTrue(ID == foundId);
	}

	/**
	 * Testing if expected Calendar date is returned.
	 */
	@Test
	public void testDate() {
		Calendar foundDate = meeting.getDate();
		assertEquals(DATE, foundDate);
	}
	
	/**
	 * Testing if the list of set contacts is returned.
	 */
	@Test
	public void testContactList() {
		Set<Contact> contactListFound = meeting.getContacts();
		assertEquals(CONTACTS, contactListFound);
	}
	
	/**
	 * Testing if a different set of contacts is returned.
	 */
	@Test
	public void testDifferentContactList() {
		Set<Contact> contactListFound = meeting.getContacts();
		Set<Contact> differentContactList = new HashSet<Contact>();
		differentContactList.add(new ContactImpl(123, "I am a new contact"));
		assertNotEquals(differentContactList, contactListFound);
	}
	
	/**
	 * It is not possible to setup a Meeting with an empty list of contacts.
	 * @throws Exception on empty contact list
	 */
	@Test(expected=Exception.class)
	public void testEmptyContactList() throws Exception {
		Set<Contact> emptyContactList = new HashSet<Contact>();
		meeting = new MeetingImpl(ID, DATE, emptyContactList);
	}
}
