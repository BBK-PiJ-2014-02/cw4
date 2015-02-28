package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import contactManager.Contact;
import contactManager.ContactImpl;
import contactManager.ContactManager;
import contactManager.ContactManagerImpl;
import contactManager.Meeting;
import contactManager.MeetingImpl;
import contactManager.PastMeeting;
import contactManager.PastMeetingImpl;

/**
 * Testing the ContactManager class.
 * 
 * A class to manage your contacts and meetings. 
 * 
 * @author Vasco
 *
 */
public class TestContactManager {
    // ***************************************************************************** //
    // *                                  CONTACTS                                 * //
    // ***************************************************************************** //
    /**
     * Null Contact name.
     */
    private final String NULL_NAME = null;

    /**
     * Null Notes.
     */
    private final String NULL_NOTES = null;
    
    // *************************** SINGLE SEARCH CONTACT *************************** //
    /**
     * Contact id for the single search result.
     */
    private final int CONTACT_ID_SINGLE_SEARCH_RESULT = 1;
    
    /**
     * Contact name for a single result search.
     */
    private final String CONTACT_NAME_SINGLE_SEARCH_RESULT = "Zchweski Ywmeylt";
    
    /**
     * Single contact notes.
     */
    private final String CONTACT_NOTES_SINGLE = "Notes for the single contact";
    
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
     * Multiple contact notes.
     */
    private final String CONTACT_NOTES_MULTIPLE = "Notes for the multiple contact";

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

    // ***************************************************************************** //
    // *                                  MEETING                                  * //
    // ***************************************************************************** //

    /**
     * The default Meeting id.
     */
    private final int MEETING_ID = 1;

    /**
     * The default Meeting notes.
     */
    private final String MEETING_NOTES = "Default meeting notes";

    /**
     * Non existent Meeting id.
     */
    private final int MEETING_NOT_EXISTING_ID = 123;

    /**
     * Past Meeting id.
     */
    private final int MEETING_PAST_ID = 2;
    
    /**
     * Past Meeting notes.
     */
    private final String MEETING_PAST_NOTES = "Past meeting notes";
    
    /**
     * Future Meeting id.
     */
    private final int MEETING_FUTURE_ID = 3;

    /**
     * Future Meeting notes.
     */
    private final String MEETING_FUTURE_NOTES = "Future meeting notes";

    /**
     * Meeting Past Date.
     */
    private Calendar DATE_PAST;
    
    /**
     * Meeting Future Date.
     */
    private Calendar DATE_FUTURE;
    
    /**
     * Meeting Present Date.
     */
    private Calendar DATE_PRESENT;
    
    
    // ***************************************************************************** //
    // *                           GENERIC OBJECT HANDLERS                         * //
    // ***************************************************************************** //
    /**
     * The generic Meeting testing object handler.
     */
    private Meeting meeting;
    
    /**
     * The ContactManager testing object handler.
     */
    private ContactManager contactManager;

    
    // ********************************** BEFORE ********************************* //
    /**
     * Loading all needed values to be ready for each test.
     */
    @Before
    public void before() {
        // Contact initialisations.
        defaultContactInit();
        
        // Calendar date initialisations.
        defaultCalendarInit();
            
        // Meeting initialisations.
        defaultMeetingInit();
        
        // Initialising contactManager.
        contactManager = new ContactManagerImpl();
    }


    // **************************************************************************** //
    //                                                                              //
    //                                   TESTS                                      //
    //                                                                              //
    // **************************************************************************** //


    // ****************************** CONTACT tests ******************************* //

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
    @Test(expected=NullPointerException.class)
    public void testAddNewContactNullName() { 
        contactManager.addNewContact(NULL_NAME, CONTACT_NOTES_NEW);
    }
    
    /** 
     * Test exception on notes null.
     */ 
    @Test(expected=NullPointerException.class)
    public void testAddNewContactNullNotes() {
        contactManager.addNewContact(CONTACT_NAME_NEW, NULL_NOTES);
    }
    
    /** 
     * Check if the contact returned corresponds to the given id.
     */ 
    @Test
    public void testGetContactsById() { 
        // Search for the one contact id.
        Set<Contact> contactListFound = contactManager.getContacts(CONTACT_ID_NEW);
        
        // Ensure null was not returned
        assertNotNull(contactListFound);
        
        // Check if we have got one result only as expected
        assertTrue(contactListFound.size() == 1);
        
        // Retrieve the contact info to check if it matches to the result we expect
        Contact contactFound = contactListFound.iterator().next();
        assertTrue(contactFound.getId() == CONTACT_ID_NEW);
        assertEquals(contactFound.getName(),CONTACT_NAME_NEW);
        assertEquals(contactFound.getNotes(),CONTACT_NOTES_NEW);
    }

    /** 
     * Check if the list of contacts returned corresponds to the given ids.
     */ 
    @Test
    public void testGetContactsByIds() { 
        // Get the list of contacts for the three ids to check
        Set<Contact> contactListFound = contactManager.getContacts(CONTACT_ID_NEW,
                CONTACT_ID_SINGLE_SEARCH_RESULT,CONTACT_ID_MULTIPLE_SEARCH_RESULT);

        verifyDefaultContactList(contactListFound);
    }
    
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


    // ****************************** MEETING tests ******************************* //

    /** 
     * Check if the meeting with the requested ID is returned. 
     */ 
    @Test
    public void testGetMeetingId() { 
        Meeting meetingFound = contactManager.getMeeting(MEETING_ID);
        assertEquals(meeting,meetingFound);
    }
        
    /** 
     * Check if the non existing meeting the requested ID returns null.
     */ 
    @Test
    public void testGetMeetingIdNonExisting() { 
    	Meeting meetingFound = contactManager.getMeeting(MEETING_NOT_EXISTING_ID);
    	assertNull(meetingFound);
    }

    /** 
     * Test exception when meeting does not exist.
     */ 
    @Test(expected=IllegalArgumentException.class)
    public void testAddMeetingNotesMeetingNonExisting() { 
    	contactManager.addMeetingNotes(MEETING_NOT_EXISTING_ID, MEETING_NOTES);
    }
    
    /** 
     * Test exception on null notes.
     */ 
    @Test(expected=NullPointerException.class)
    public void testAddMeetingNotesNull() { 
    	contactManager.addMeetingNotes(MEETING_ID, NULL_NOTES);
    }

    /** 
     * Test if a future meeting was converted to a PastMeeting after adding notes.
     */ 
    @Test
    public void testAddMeetingNotesFutureToPastMeeting() { 
    	contactManager.addMeetingNotes(MEETING_FUTURE_ID, MEETING_NOTES);
    	Meeting meetingFound = contactManager.getMeeting(MEETING_FUTURE_ID);
    	assertNotNull(meetingFound);
    	assertEquals(PastMeetingImpl.class,meetingFound.getClass());
    }
    
    /** 
     * Test if returned PastMeeting contain added notes.
     */ 
    @Test
    public void testAddMeetingNotesReturnPastMeetingWithNotes() { 
    	contactManager.addMeetingNotes(MEETING_FUTURE_ID, MEETING_NOTES);
    	PastMeeting meetingFound = contactManager.getPastMeeting(MEETING_FUTURE_ID);
    	assertNotNull(meetingFound);
    	assertEquals(MEETING_NOTES, meetingFound.getNotes());
    }
    
    /** 
     * Check if notes are added to a past meeting. 
     */ 
    @Test
    public void testAddMeetingNotesPastMeeting() { 
    	contactManager.addMeetingNotes(MEETING_PAST_ID, MEETING_NOTES);
    	PastMeeting pastMeeting = contactManager.getPastMeeting(MEETING_PAST_ID);
    	assertNotNull(pastMeeting);
    	assertEquals(MEETING_NOTES, pastMeeting.getNotes());
    }
    
    /** 
     * Test exception on a meeting set in the future.
     */ 
    @Test(expected=IllegalStateException.class)
    public void testAddMeetingNotesFutureMeeting() { 
    	contactManager.addMeetingNotes(MEETING_FUTURE_ID, MEETING_NOTES);
    }
    

    // ****************************** PAST MEETING tests ******************************* //

    /**
     * Check if the PAST meeting with the requested ID is returned.
     */ 
    @Test
    public void testGetPastMeetingId(){ 
    	PastMeeting pastMeetingFound = contactManager.getPastMeeting(MEETING_PAST_ID);

    	// Ensure given meeting is not null.
    	assertNotNull(pastMeetingFound);
    	
    	// This is checking Meeting only
        assertEquals(MEETING_ID, pastMeetingFound.getId());
        // Verify all expected contacts are there.
        verifyDefaultContactList(pastMeetingFound.getContacts());
        // Check the date of the meeting if matches the default expected.
        assertEquals(DATE_PRESENT, pastMeetingFound.getDate());
    }
    
    /**
     * Check if the non existing PAST meeting with a requested ID returns null.
     */ 
    @Test
    public void testGetPastMeetingIdNull(){
    	PastMeeting pastMeeting = contactManager.getPastMeeting(MEETING_NOT_EXISTING_ID);
    	assertNull(pastMeeting);
    }
    
    /**
     * Check exception is thrown if there is a meeting with that ID happening in the future 
     */ 
//    @Test(expected=IllegalArgumentException.class)
    public void testGetPastMeetingIdInTheFuture(){    }
    
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


    // ****************************** FUTURE MEETING tests ******************************* //

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


    // ****************************** File access tests ******************************* //

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

    
    
    
    
    /**
     * Initialize all meetings with the default values.
     */
    private void defaultMeetingInit() {
        try {
            meeting = new MeetingImpl(MEETING_ID,DATE_PRESENT,contactList);
            meeting = new MeetingImpl(MEETING_FUTURE_ID,DATE_FUTURE,contactList);
            meeting = new MeetingImpl(MEETING_PAST_ID,DATE_PAST,contactList);
        } catch (Exception e) {
            // Keep calm and carry on.
        }
    }
    
    /**
     * Initialize all Calendar dates with the default values.
     */
    private void defaultCalendarInit() {
        DATE_FUTURE = new GregorianCalendar();
        DATE_FUTURE.set(Calendar.YEAR+1, Calendar.MONTH, Calendar.DATE, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND);
        DATE_PAST = new GregorianCalendar();
        DATE_PAST.set(Calendar.YEAR-1, Calendar.MONTH, Calendar.DATE, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND);
        DATE_PRESENT = new GregorianCalendar();
        DATE_PRESENT.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND);
    }
    
    /**
     * Initialize all contacts with the default values.
     */
    private void defaultContactInit() {
        // Adding all contacts existing to the contactList to be acted upon.
        contactList.add(new ContactImpl(CONTACT_ID_SINGLE_SEARCH_RESULT, 
                CONTACT_NAME_SINGLE_SEARCH_RESULT, CONTACT_NOTES_SINGLE));
        contactList.add(new ContactImpl(CONTACT_ID_SINGLE_SEARCH_RESULT, 
                CONTACT_NAME_MULTIPLE_SEARCH_RESULT, CONTACT_NOTES_MULTIPLE));

        // The multiple list search result expected.
        multipleContactList.add(new ContactImpl(CONTACT_ID_MULTIPLE_SEARCH_RESULT, 
                CONTACT_NAME_MULTIPLE_SEARCH_RESULT, CONTACT_NOTES_NEW));
        multipleContactList.add(new ContactImpl(CONTACT_ID_NEW, CONTACT_NAME_NEW, 
                CONTACT_NOTES_NEW));
    }

    /**
     * Given a set list of contacts, check if all the default ones are inside only once.
     * 
     * @param contactListFound the Set of Contacts found.
     */
    private void verifyDefaultContactList(Set<Contact> contactListFound) {
        // Ensure we have got something returned
        assertNotNull(contactListFound);
        
        // Check if we have three contacts only as expected
        assertTrue(contactListFound.size() == 3);

        // Expected three records to be correctly matched.
        int foundCorrect = 0;
        
        // Check details as per the contact id returned of each one of them
        for(int i = 0; i < contactListFound.size(); i++ ) {
            Contact contactFound = contactListFound.iterator().next();
            if ( contactFound.getId() == CONTACT_ID_MULTIPLE_SEARCH_RESULT ) {
                assertTrue(contactFound.getId() == CONTACT_ID_MULTIPLE_SEARCH_RESULT);
                assertEquals(contactFound.getName(),CONTACT_NAME_MULTIPLE_SEARCH_RESULT);
                assertEquals(contactFound.getNotes(),CONTACT_NOTES_MULTIPLE);
                foundCorrect++;
            }
            else if ( contactFound.getId() == CONTACT_ID_NEW ) {
                assertTrue(contactFound.getId() == CONTACT_ID_NEW);
                assertEquals(contactFound.getName(),CONTACT_NAME_NEW);
                assertEquals(contactFound.getNotes(),CONTACT_NOTES_NEW);
                foundCorrect++;
            }
            else if ( contactFound.getId() == CONTACT_ID_SINGLE_SEARCH_RESULT ){
                assertTrue(contactFound.getId() == CONTACT_ID_SINGLE_SEARCH_RESULT);
                assertEquals(contactFound.getName(),CONTACT_NAME_SINGLE_SEARCH_RESULT);
                assertEquals(contactFound.getNotes(),CONTACT_NOTES_SINGLE);
                foundCorrect++;
            }
            else {
                foundCorrect--;
            }
        }
        
        // re-ensure we have got all three correct.
        assertTrue(foundCorrect == 3);
    	
    }
} 