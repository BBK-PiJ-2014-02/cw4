package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import contactManager.Contact;
import contactManager.ContactImpl;

/**
 * Unit test to test the ContactImpl class.
 * 
 * @author Vasco
 *
 */
public class TestContact {

	/**
	 * The Contact to test.
	 */
	private Contact contact;
	
	/**
	 * Contact name
	 */
	private String NAME = "Contact name";
	
	/**
	 * Some notes.
	 */
	private String NOTES = "Here are some notes";
	
	/**
	 * Empty notes.
	 */
	private String EMPTY_NOTES = "";
	
	/**
	 * The expected ID at the start
	 */
	private int ID = 1;

	/**
	 * Initialise contact for each test.
	 */
	@Before
	public void before() {
		this.contact = new ContactImpl(ID,NAME,NOTES);
	}
	
	/**
	 * Test that the name is returned as expected.
	 */
	@Test
	public void testName() {
		String foundName = contact.getName();
		assertEquals(NAME, foundName);
	}
	
	/**
	 * Test that the notes are empty.
	 */
	@Test
	public void testEmptyNotes() {
		Contact contact = new ContactImpl(ID, NAME, "");
		String foundNotes = contact.getNotes();
		assertEquals(EMPTY_NOTES, foundNotes);
	}
	
	/**
	 * Test that the notes are empty.
	 */
	@Test
	public void testNullNotes() {
		Contact contact = new ContactImpl(ID, NAME, null);
		String foundNotes = contact.getNotes();
		assertEquals(EMPTY_NOTES, foundNotes);
	}
	
	/**
	 * Test addNotes.
	 */
	@Test
	public void testAddNotes() {
		contact.addNotes(NOTES);
		String foundNotes = contact.getNotes();
		assertEquals(NOTES,foundNotes);
	}
	
	/**
	 * Test that the first ID was created.
	 */
	@Test
	public void testFirstId() {
		Integer Id = contact.getId();
		assertTrue(ID == Id);
	}
}
