package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import contactManager.Contact;
import contactManager.ContactImpl;
import contactManager.ContactManager;
import contactManager.ContactManagerImpl;
import contactManager.FutureMeeting;
import contactManager.FutureMeetingImpl;
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


    // ********************************** BOGUS CONTACT ******************************* //
    /**
     * Fake contact id.
     */
    private final int CONTACT_ID_NOT_REAL = 123;

    /**
     * Fake contact name.
     */
    private final String CONTACT_NAME_NOT_REAL = "Fake Bogus Regis";

    /**
     * Fake notes.
     */
    private final String CONTACT_NOTES_NOT_REAL = "Note: What do you mean by notes not real?";

    /**
     * The bogus Contact
     */
    private Contact bogusContact;

    // ****************************** NON EXISTENT CONTACT **************************** //
    /**
     * Non existing contact id.
     */
    private final int CONTACT_ID_NOT_IN_MEETING = 2123;
    
    /**
     * Non existing contact name.
     */
    private final String CONTACT_NAME_NOT_IN_MEETING = "John Not Real Smith";
    
    /**
     * Non existing contact notes.
     */
    private final String CONTACT_NOTES_NOT_IN_MEETING = "This is a new contact";

    /**
     * The not existing in meeting contact.
     */
    private Contact notInMeetingContact;


    // ********************************* PAST CONTACT ******************************* //
    /**
     * Past contact id.
     */
    private final int CONTACT_ID_PAST = 12345;
    
    /**
     * Past contact name.
     */
    private final String CONTACT_NAME_PAST = "John Past Smith";
    
    /**
     * Past contact notes.
     */
    private final String CONTACT_NOTES_PAST = "This is a past contact";

    /**
     * The past contact.
     */
    private Contact pastContact;

    // ****************************** PRESENT CONTACT ****************************** //
    /**
     * Present contact id.
     */
    private final int CONTACT_ID_PRESENT = 123456;
    
    /**
     * Present contact name.
     */
    private final String CONTACT_NAME_PRESENT = "John Present Smith";
    
    /**
     * Present contact notes.
     */
    private final String CONTACT_NOTES_PRESENT = "This is a present contact";

    /**
     * The present contact.
     */
    private Contact presentContact;

    // ******************************* FUTURE CONTACT ******************************* //
    /**
     * Future contact id.
     */
    private final int CONTACT_ID_FUTURE = 1234567;
    
    /**
     * Future contact name.
     */
    private final String CONTACT_NAME_FUTURE = "John Future Smith";
    
    /**
     * Future contact notes.
     */
    private final String CONTACT_NOTES_FUTURE = "This is a future contact";

    /**
     * The future contact.
     */
    private Contact futureContact;

    // ******************************* ALL CONTACTS LIST ***************************** //
    /**
     * The past contact list.
     */
    private Set<Contact> pastContactList = new HashSet<Contact>();

    /**
     * The present contact list.
     */
    private Set<Contact> presentContactList = new HashSet<Contact>();

    /**
     * The future contact list.
     */
    private Set<Contact> futureContactList = new HashSet<Contact>();

    /**
     * The not used in meetings contact list.
     */
    private Set<Contact> notInMeetingsList = new HashSet<Contact>();

    /**
     * The bogs meeting list to test on contacts that do not exist.
     */
    private Set<Contact> bogusMeetingsList = new HashSet<Contact>();

    /**
     * The full contact list available.
     */
    private Set<Contact> contactList = new HashSet<Contact>();

    
    // ***************************************************************************** //
    // *                                  MEETING                                  * //
    // ***************************************************************************** //
    /**
     * Non existent Meeting id.
     */
    private final int MEETING_NOT_EXISTING_ID = 123;

    /**
     * The Present Meeting id.
     */
    private final int MEETING_ID_PRESENT = 1;

    /**
     * The Present Meeting notes.
     */
    private final String MEETING_NOTES_PRESENT = "Present meeting notes";

    /**
     * Past Meeting id.
     */
    private final int MEETING_ID_PAST = 2;
    
    /**
     * Past Meeting notes.
     */
    private final String MEETING_NOTES_PAST = "Past meeting notes";
    
    /**
     * Future Meeting id.
     */
    private final int MEETING_ID_FUTURE = 3;

    /**
     * Future Meeting notes.
     */
    private final String MEETING_NOTES_FUTURE = "Future meeting notes";

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
     * The Past Meeting testing object handler.
     */
    private Meeting pastMeeting;

    /**
     * The Present Meeting testing object handler.
     */
    private Meeting presentMeeting;

    /**
     * The Future Meeting testing object handler.
     */
    private Meeting futureMeeting;
    
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
        Meeting meetingFound = contactManager.getMeeting(MEETING_ID_PRESENT);
        assertEquals(presentMeeting,meetingFound);
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
        contactManager.addMeetingNotes(MEETING_NOT_EXISTING_ID, MEETING_NOTES_PRESENT);
    }
    
    /** 
     * Test exception on null notes.
     */ 
    @Test(expected=NullPointerException.class)
    public void testAddMeetingNotesNull() { 
        contactManager.addMeetingNotes(MEETING_ID_PRESENT, NULL_NOTES);
    }

    /** 
     * Test if a future meeting was converted to a PastMeeting after adding notes.
     */ 
    @Test
    public void testAddMeetingNotesFutureToPastMeeting() { 
        contactManager.addMeetingNotes(MEETING_ID_FUTURE, MEETING_NOTES_PRESENT);
        Meeting meetingFound = contactManager.getMeeting(MEETING_ID_FUTURE);
        assertNotNull(meetingFound);
        assertEquals(PastMeetingImpl.class,meetingFound.getClass());
    }
    
    /** 
     * Test if returned PastMeeting contain added notes.
     */ 
    @Test
    public void testAddMeetingNotesReturnPastMeetingWithNotes() { 
        contactManager.addMeetingNotes(MEETING_ID_FUTURE, MEETING_NOTES_PRESENT);
        PastMeeting meetingFound = contactManager.getPastMeeting(MEETING_ID_FUTURE);
        assertNotNull(meetingFound);
        assertEquals(MEETING_NOTES_PRESENT, meetingFound.getNotes());
    }
    
    /** 
     * Check if notes are added to a past meeting. 
     */ 
    @Test
    public void testAddMeetingNotesPastMeeting() { 
        contactManager.addMeetingNotes(MEETING_ID_PAST, MEETING_NOTES_PRESENT);
        PastMeeting pastMeeting = contactManager.getPastMeeting(MEETING_ID_PAST);
        assertNotNull(pastMeeting);
        assertEquals(MEETING_NOTES_PRESENT, pastMeeting.getNotes());
    }
    
    /** 
     * Test exception on a meeting set in the future.
     */ 
    @Test(expected=IllegalStateException.class)
    public void testAddMeetingNotesFutureMeeting() { 
        contactManager.addMeetingNotes(MEETING_ID_FUTURE, MEETING_NOTES_PRESENT);
    }


    // ****************************** PAST MEETING tests ******************************* //

    /**
     * Check if the PAST meeting with the requested ID is returned.
     */ 
    @Test
    public void testGetPastMeetingId(){ 
        PastMeeting pastMeetingFound = contactManager.getPastMeeting(MEETING_ID_PAST);

        // Ensure given meeting is not null.
        assertNotNull(pastMeetingFound);
        
        // This is checking Meeting only
        assertEquals(MEETING_ID_PRESENT, pastMeetingFound.getId());
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
    @Test(expected=IllegalArgumentException.class)
    public void testGetPastMeetingIdInTheFuture(){ 
        contactManager.getPastMeeting(MEETING_ID_FUTURE);
    }

    /** 
     * Test if the list of past meetings in which this contact has participated is returned. 
     */
    @Test
    public void testGetPastMeetingList() {
        // Get the PastMeeting List with one result only, where this past Contact has been present.
        List<PastMeeting> pastMeetingList = contactManager.getPastMeetingList(pastContact);

        // Check first that the pastMeetingList is not null before any further operations.
        assertNotNull(pastMeetingList);
        
        // Only expecting one result
        assertTrue(pastMeetingList.size() == 1);
        
        // Get the one result PastMeeting
        PastMeeting pastMeeting = pastMeetingList.get(0);
        
        // Check the contacts of this meeting
        Set<Contact> pastContactSetFound = pastMeeting.getContacts();
        
        // Expect a not null contact list
        assertNotNull(pastContactSetFound);

        // Check if only one contact is found.
        assertTrue(pastContactSetFound.size() == 1);
        
        // Get the respective contact
        Contact pastContactFound = pastContactSetFound.iterator().next();
        
        // Assert that the contact found is the one we expect.
        assertEquals(pastContact.getId(), pastContactFound.getId());
        assertEquals(pastContact.getName(), pastContactFound.getName());
        assertEquals(pastContact.getNotes(), pastContactFound.getNotes());
    }
    
    /** 
     * Test if list is returned empty on none found.
     */ 
    @Test
    public void testGetPastMeetingListNoneFound() { 
        List<PastMeeting> emptyList = contactManager.getPastMeetingList(notInMeetingContact);

        // Check if the emptyList is not null before any other operations.
        // Null here means that not even an empty list was initialised which
        // defeats the interface purpose on defining an empty list being returned.
        assertNotNull(emptyList);
        
        // Ensure that the size is zero.
        assertTrue(emptyList.size() == 0);
    }
    
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
    @Test(expected=IllegalArgumentException.class)
    public void testGetPastMeetingListContactNotExistent() { 
        contactManager.getPastMeetingList(new ContactImpl(CONTACT_ID_NOT_REAL,
                CONTACT_NAME_NOT_IN_MEETING, CONTACT_NOTES_NOT_IN_MEETING));
    }
    
    /** 
     * Check if a new record for a meeting that took place in the past was created. 
     */ 
//    @Test
    public void testAddNewPastMeeting() { }
    
    /** 
     * Check if exception is thrown on an empty list.
     */ 
    @Test(expected=IllegalArgumentException.class)
    public void testAddNewPastMeetingEmptyList() { 
        // Create an empty list
        Set<Contact> emptyList = new HashSet<Contact>();
        
        // Expect exception.
        contactManager.addNewPastMeeting(emptyList, DATE_PAST, MEETING_NOTES_PAST);
    }
    
    /** 
     * Check if exception is thrown on a non existent contact.
     */ 
    @Test(expected=IllegalArgumentException.class)
    public void testAddNewPastMeetingContactNonExistent() {
        // Create a new contact set list.
        Set<Contact> nonExistingContact = new HashSet<Contact>();
        
        // Add a new contact that does not exist in the contact list
        nonExistingContact.add(new ContactImpl(CONTACT_ID_NOT_REAL, CONTACT_NAME_NOT_IN_MEETING, CONTACT_NOTES_NOT_IN_MEETING));
        
        // Expect an exception.
        contactManager.addNewPastMeeting(notInMeetingsList, DATE_PAST, MEETING_NOTES_PAST);
    }
    
    /** 
     * Check if exception happens if contacts is null. 
     */ 
    @Test(expected=NullPointerException.class)
    public void testAddNewPastMeetingContactsNull() { 
        contactManager.addNewPastMeeting(null, DATE_PAST, MEETING_NOTES_PAST);
    }
    
    /** 
     * Check if exception happens if date is null. 
     */ 
    @Test(expected=NullPointerException.class)
    public void testAddNewPastMeetingDateNull() { 
        contactManager.addNewPastMeeting(pastContactList, null, MEETING_NOTES_PAST);
    }
    
    /** 
     * Check if exception happens if notes is null. 
     */ 
    @Test(expected=NullPointerException.class)
    public void testAddNewPastMeetingNotesNull() { 
        contactManager.addNewPastMeeting(pastContactList, DATE_PAST, NULL_NOTES);
    }


    // ****************************** FUTURE MEETING tests ******************************* //

    /** 
     * Testing if a new meeting to be held in the future was added. 
     */ 
    @Test
    public void testAddFutureMeeting() { 
        // A new future meeting with contacts not in any meeting lists.
        contactManager.addFutureMeeting(notInMeetingsList, DATE_FUTURE);
        
        // Get the future meetings on the future date supplied.
        List<Meeting> futureMeetingList = contactManager.getFutureMeetingList(DATE_FUTURE);
        
        // Check we have got something
        assertNotNull(futureMeetingList);
        
        // Need to iterate through all future meetings and contacts to check if the 
        // contacts we have just added on the future meeting, were added.
        // Please note that this test is expecting for the notInMeetingList to contain
        // a set of contacts that have not yet been used elsewhere, thus, unique.
        boolean wasCreated = false;
        for(Meeting meeting: futureMeetingList) {
            Set<Contact> contacts = meeting.getContacts();
            if ( contacts != null ) {
                Contact contactFound = contacts.iterator().next();
                if ( contactFound.equals(notInMeetingContact)) {
                    wasCreated = true;
                    continue;
                }
            }
        }
        
        // Ensure the meeting was created
        assertTrue(wasCreated);
        
    }
    
    /** 
     * Testing if a new meeting to be held in the future was tried to be added in the past. 
     */ 
//    @Test(expected=IllegalArgumentException.class)
    public void testAddFutureMeetingWithPastDate() { }
    
    /** 
     * Testing if any contact is unknown when adding a future meeting.
     */ 
    @Test(expected=IllegalArgumentException.class)
    public void testAddFutureMeetingWithUnknownContact() { 
        contactManager.addFutureMeeting(bogusMeetingsList, DATE_FUTURE);
    }
    
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

    
    
    // **************************************************************************** //
    //                                                                              //
    //                                 HELPERS                                      //
    //                                                                              //
    // **************************************************************************** //
    
    /**
     * Initialize all meetings with the default values.
     */
    private void defaultMeetingInit() {
        try {
            pastMeeting    = new PastMeetingImpl(MEETING_ID_PAST,DATE_PAST,pastContactList,MEETING_NOTES_PAST);
            presentMeeting = new MeetingImpl(MEETING_ID_PRESENT,DATE_PRESENT,presentContactList);
            futureMeeting  = new FutureMeetingImpl(MEETING_ID_FUTURE,DATE_FUTURE,futureContactList);
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
     * Initialize all contacts and contact lists with the default values.
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

        // Initialise past, present and future contact.
        pastContact         = new ContactImpl(CONTACT_ID_PAST, CONTACT_NAME_PAST, CONTACT_NOTES_PAST);
        presentContact      = new ContactImpl(CONTACT_ID_PRESENT, CONTACT_NAME_PRESENT, CONTACT_NOTES_PRESENT);
        futureContact       = new ContactImpl(CONTACT_ID_FUTURE, CONTACT_NAME_FUTURE, CONTACT_NOTES_FUTURE);
        notInMeetingContact = new ContactImpl(CONTACT_ID_NOT_IN_MEETING, CONTACT_NAME_NOT_IN_MEETING, CONTACT_NOTES_NOT_IN_MEETING);
        bogusContact        = new ContactImpl(CONTACT_ID_NOT_REAL, CONTACT_NAME_NOT_REAL, CONTACT_NOTES_NOT_REAL);

        // The past contact list.
        pastContactList.add(pastContact);
        // The present contact list.
        presentContactList.add(presentContact);
        // The future contact list.
        futureContactList.add(futureContact);
        // The not in meeting contact list.
        notInMeetingsList.add(notInMeetingContact);
        // The bogus contact list.
        bogusMeetingsList.add(bogusContact);
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