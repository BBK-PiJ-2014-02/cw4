package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import contactManager.Contact;

/**
 * Testing the Contact class.
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
	 * Initialize contact for each test.
	 */
	@Test
	public void before() {
		this.contact = new ContactImpl(NAME);
		this.contact.addNotes(NOTES);
	}
	
	/**
	 * Test that the name is returned as expected.
	 */
	public void testName() {
		String foundName = contact.getName();
		assertEquals(NAME, foundName);
	}
	
	/**
	 * Test that the notes are empty.
	 */
	public void testEmptyNotes() {
		String foundNotes = contact.getNotes();
		assertEquals(EMPTY_NOTES, foundNotes);
	}
	
	/**
	 * Test addNotes.
	 */
	private void testAddNotes() {
		contact.addNotes(NOTES);
		String foundNotes = contact.getNotes();
		assertEquals(NOTES,foundNotes);
	}
	
	/**
	 * Test that the first ID was created.
	 */
	private void testFirstId() {
		Integer Id = contact.getId();
		assertTrue(ID == Id);
	}
}
