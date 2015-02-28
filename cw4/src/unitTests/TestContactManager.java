package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import contactManager.Contact;
import contactManager.ContactImpl;
import contactManager.ContactManager;
import contactManager.ContactManagerImpl;

/**
 * Testing the ContactManager class.
 * 
 * A class to manage your contacts and meetings. 
 * 
 * @author Vasco
 *
 */
public class TestContactManager {
    /**
     * Null Contact name.
     */
    private final String NULL_NAME = null;

    
    // *************************** SINGLE SEARCH CONTACT *************************** //
    /**
     * Contact id for the single search result.
     */
    private final int CONTACT_ID_SINGLE_SEARCH_RESULT = 1;
    
    /**
     * Contact name for a single result search.
     */
    private final String CONTACT_NAME_SINGLE_SEARCH_RESULT = "Zchweski Ywmeylt";
    
    
    // ************************** MULTIPLE SEARCH CONTACT ************************** //
    /**
     * Contact name for multiple result search.
     */
    private final String CONTACT_NAME_MULTIPLE_SEARCH_RESULT = "John";
    
    /**
     * Contact id for the multiple search result.
     */
    private final int CONTACT_ID_MULTIPLE_SEARCH_RESULT = 2;
    
    /**
     * The multiple contact list expected to be returned when searching
     * for contacts using the contact name for multiple search results.
     */
    private Set<Contact> multipleContactList = new HashSet<Contact>();
    
    
    // ********************************* NEW CONTACT ******************************* //
    /**
     * Not real contact id.
     */
    private final int CONTACT_ID_NOT_REAL = 123;
    
    /**
     * New contact id.
     */
    private final int CONTACT_ID_NEW = 1234;
    
    /**
     * New contact to be added, not in the list.
     */
    private final String CONTACT_NAME_NEW = "John New Smith";
    
    /**
     * New contact notes.
     */
    private final String CONTACT_NOTES_NEW = "This is a new contact";

    /**
     * The full contact list available.
     */
    private Set<Contact> contactList = new HashSet<Contact>();

    
    /**
     * The ContactManager testing object handler.
     */
    private ContactManager contactManager;

    
    /**
     * Loading all needed values to be ready for each test.
     */
    @Before
    public void before() {
        // Adding all contacts existing to the contactList to be acted upon.
        contactList.add(new ContactImpl(CONTACT_ID_SINGLE_SEARCH_RESULT, 
                CONTACT_NAME_SINGLE_SEARCH_RESULT, null));
        contactList.add(new ContactImpl(CONTACT_ID_SINGLE_SEARCH_RESULT, 
                CONTACT_NAME_MULTIPLE_SEARCH_RESULT, null));

        // The multiple list search result expected.
        multipleContactList.add(new ContactImpl(CONTACT_ID_MULTIPLE_SEARCH_RESULT, 
                CONTACT_NAME_MULTIPLE_SEARCH_RESULT, CONTACT_NOTES_NEW));
        multipleContactList.add(new ContactImpl(CONTACT_ID_NEW, CONTACT_NAME_NEW, 
                CONTACT_NOTES_NEW));

        // Initialising contactManager.
        contactManager = new ContactManagerImpl();
    }
    
    
    /** 
     * Testing if a new meeting to be held in the future was added. 
     */ 
//    @Test
    public void testAddFutureMeeting() { }
    
    /** 
     * Testing if a new meeting to be held in the future was tried to be added in the past. 
     */ 
//    @Test(expected=IllegalArgumentException.class)
    public void testAddFutureMeetingWithPastDate() { }
    
    /** 
     * Testing if any contact is unknown when adding a future meeting.
     */ 
//    @Test(expected=IllegalArgumentException.class)
    public void testAddFutureMeetingWithUnknownContact() { }
    
    /** 
     * Testing if any contact is non-existent when adding a future meeting.
     */ 
//    @Test(expected=IllegalArgumentException.class)
    public void testAddFutureMeetingWithNonExistentContact() { }
    
    /**
    * Check if the PAST meeting with the requested ID is returned.
    */ 
//    @Test
    public void testGetPastMeetingId(){    }
    
    /**
    * Check if the non existing PAST meeting with a requested ID returns null.
    */ 
//    @Test
    public void testGetPastMeetingIdNull(){    }
    
    /**
    * Check exception is thrown if there is a meeting with that ID happening in the future 
    */ 
//    @Test(expected=IllegalArgumentException.class)
    public void testGetPastMeetingIdInTheFuture(){    }
    
    /** 
     * Test if FUTURE meeting is returned with the requested ID
     */ 
//    @Test
    public void testGetFutureMeetingId() { }
    
    /** 
     * Test if null if no future meeting found for given id.
     */ 
//    @Test
    public void testGetFutureMeetingIdNonExisting() { }
    
    /** 
     * Check if exception is thrown when meeting id has date in the past.
     */ 
//    @Test(expected=IllegalArgumentException.class)
    public void testGetFutureMeetingIdWithDateInThePast() { }
    
    /** 
     * Check if the meeting with the requested ID is returned. 
     */ 
//    @Test
    public void testGetMeetingId() { }
        
    /** 
     * Check if the non existing meeting the requested ID returns null.
     */ 
//    @Test
    public void testGetMeetingIdNonExisting() { }
        
    /** 
     * Check if the list of future meetings scheduled with this contact is returned. 
     */ 
//    @Test
    public void testGetFutureMeetingListByContact() { }
    
    /** 
     * Check if an empty list is returned on no future meetings.
     */ 
//    @Test
    public void testGetFutureMeetingListByContactEmptyList() { }
    
    /** 
     * Check if chronologically sorted list is returned.
     */ 
//    @Test
    public void testGetFutureMeetingListByContactSorted() { }
    
    /** 
     * Check if no duplicates are returned.
     */ 
//    @Test
    public void testGetFutureMeetingListByContactNoDups() { }
    
    /** 
     * Check if an exception is thrown when a contact does not exist.
     */ 
//    @Test(expected=IllegalArgumentException.class)
    public void testGetFutureMeetingListByContactNonExistent() { }
    
    /** 
     * Test if the list of meetings that are scheduled for, or that took 
     * place on, the specified date is returned.
     */ 
//    @Test
    public void testGetFutureMeetingListByDate() { }
    
    /** 
     * Check that the list returned is chronologically sorted.
     */ 
//    @Test
    public void testGetFutureMeetingListByDateSorted() { }
    
    /** 
     * Check that the list returned does not contain any dups.
     */ 
//    @Test
    public void testGetFutureMeetingListByDateNoDups() { }
    
    /** 
     * Check that the list returned empty if none found.
     */ 
//    @Test
    public void testGetFutureMeetingListByDateNoneFound() { }
    
    /** 
     * Test if the list of past meetings in which this contact has participated is returned. 
     */
//    @Test
    public void testGetPastMeetingList() { }
    
    /** 
     * Test if list is returned empty on none found.
     */ 
//    @Test
    public void testGetPastMeetingListNoneFound() { }
    
    /** 
     * Check if returned list is chronologically sorted.
     */ 
//    @Test
    public void testGetPastMeetingListSorted() { }
    
    /** 
     * Test if returned list does not contain any duplicates. 
     */ 
//    @Test
    public void testGetPastMeetingListNoDups() { }
    
    /** 
     * Check if the exception is thrown when a contact does not exist.
     */ 
//    @Test(expected=IllegalArgumentException.class)
    public void testGetPastMeetingListContactNotExistent() { }
    
    /** 
     * Check if a new record for a meeting that took place in the past was created. 
     */ 
//    @Test
    public void testAddNewPastMeeting() { }
    
    /** 
     * Check if exception is thrown on an empty list.
     */ 
//    @Test(expected=IllegalArgumentException.class)
    public void testAddNewPastMeetingEmptyList() { }
    
    /** 
     * Check if exception is thrown on a non existent contact.
     */ 
//    @Test(expected=IllegalArgumentException.class)
    public void testAddNewPastMeetingContactNonExistent() { }
    
    /** 
     * Check if exception happens if contacts is null. 
     */ 
//    @Test(expected=NullPointerException.class)
    public void testAddNewPastMeetingContactsNull() { }
    
    /** 
     * Check if exception happens if date is null. 
     */ 
//    @Test(expected=NullPointerException.class)
    public void testAddNewPastMeetingDateNull() { }
    
    /** 
     * Check if exception happens if notes is null. 
     */ 
//    @Test(expected=NullPointerException.class)
    public void testAddNewPastMeetingNotesNull() { }
    
    /** 
     * Test if a future meeting was converted to a PastMeeting after adding notes.
     */ 
//    @Test
    public void testAddMeetingNotesFutureToPastMeeting() { }
    
    /** 
     * Test if returned PastMeeting contain added notes.
     */ 
//    @Test
    public void testAddMeetingNotesReturnPastMeetingWithNotes() { }
    
    /** 
     * Check if notes are added to a past meeting. 
     */ 
//    @Test
    public void testAddMeetingNotesPastMeeting() { }
    
    /** 
     * Test exception when meeting does not exist.
     */ 
//    @Test(expected=IllegalArgumentException.class)
    public void testAddMeetingNotesMeetingNonExisting() { }
    
    /** 
     * Test exception on a meeting set in the future.
     */ 
//    @Test(expected=IllegalStateException.class)
    public void testAddMeetingNotesFutureMeeting() { }
    
    /** 
     * Test exception on null notes.
     */ 
//    @Test(expected=NullPointerException.class)
    public void testAddMeetingNotesNull() { }
    
    /** 
     * Test if specified contact was created.
     */ 
    @Test
    public void testAddNewContact() {  
        // Add the new contact and some notes.
        contactManager.addNewContact(CONTACT_NAME_NEW, CONTACT_NOTES_NEW);

        // Get all added contacts with the given name.
        Set<Contact> contacts = contactManager.getContacts(CONTACT_NAME_NEW);

        // Make sure something is returned.
        assertNotNull(contacts);

        // Expect at least one record
        assertTrue( contacts.size() > 0 );

        // Need to find the contact we have added.
        boolean foundContact = false;
        for( Contact found : contacts ) {
            // If name and notes match the contact we have added, the test was a success.
            if (found.getName().equals(CONTACT_NAME_NEW) && found.getNotes().equals(CONTACT_NOTES_NEW)) {
                foundContact = true;
                continue;
            }
        }
        
        // If looped through all contacts and the one we need was not found,
        // the test would fail at this point.
        assertTrue(foundContact);
    }
    
    /** 
     * Test exception on name null.
     */ 
//    @Test(expected=NullPointerException.class)
    public void testAddNewContactNullName() { }
    
    /** 
     * Test exception on notes null.
     */ 
//    @Test(expected=NullPointerException.class)
    public void testAddNewContactNullNotes() { }
    
    /** 
     * Check if the list returned corresponds to the given ids.
     */ 
//    @Test
    public void testGetContactsById() { }
    
    /** 
     * Check if exception is thrown on an id that does not correspond to a real contact. 
     */ 
    @Test(expected=IllegalArgumentException.class)
    public void testGetContactsByIdNonRealContact() {
        // Calling contactManager with a non real contact id
        // is expected an exception.
        contactManager.getContacts(CONTACT_ID_NOT_REAL);
    }
    
    /** 
     * Check if expected list of contacts is returned from the given name string.
     */ 
    @Test
    public void testGetContactsByName() { 
        Set<Contact> foundContactList = contactManager.getContacts(CONTACT_NAME_MULTIPLE_SEARCH_RESULT);
        assertEquals(multipleContactList,foundContactList);
    }
    
    /** 
     * Check if the one and only contact is returned from the given name string.
     */ 
    @Test
    public void testGetContactsByNameSingleResult() { 
        // Search for the contact that is expected to return only one result.
        Set<Contact> singleFound = contactManager.getContacts(CONTACT_NAME_SINGLE_SEARCH_RESULT);

        // Make sure that we have got something back before further checks.
        assertNotNull(singleFound);
        
        // Expected is only one result
        assertTrue(singleFound.size() == 1);
        
        // To get the one and only, iterate->next to get the Contact.
        Contact contactFound = singleFound.iterator().next();
        
        // Check if the name requested matches the name of the contact found.
        assertEquals(CONTACT_NAME_SINGLE_SEARCH_RESULT,contactFound.getName());
    }
    
    /** 
     * Check if exception is thrown on a null parameter.
     * @throws NullPointerException if the parameter is null 
     */ 
    @Test(expected=NullPointerException.class)
    public void testGetContactsByNameNull() {
        // Need to create a string pointing to null instead of just null,
        // to avoid this call mixed with the getContacts(int... ids).
        contactManager.getContacts(NULL_NAME);
    }
    
    /** 
     * Check that expected data is saved to disk.
     */ 
//    @Test
    public void testFlush() { }

    /** 
     * Check if when application is closed, expected data is saved in file.
     */ 
//    @Test
    public void testFlushAtClose() { }

}
