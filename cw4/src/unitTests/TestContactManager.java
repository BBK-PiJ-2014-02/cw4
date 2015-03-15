package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import contactManager.Contact;
import contactManager.ContactImpl;
import contactManager.ContactManager;
import contactManager.ContactManagerImpl;
import contactManager.FutureMeeting;
import contactManager.FutureMeetingImpl;
import contactManager.JSONUtils;
import contactManager.JSONUtilsImpl;
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
    /**
     * The JSON Utils handler.
     */
    private final JSONUtils jUtils = new JSONUtilsImpl();

    /**
     * When this flag is set to true, the original file will be regenerated new,
     * using currently set variables below for Contacts and Meetings.
     */
    public static boolean generateOriginalFile = true;

    /**
     * Path to the test files.
     */
    private final String TEST_FILE_PATH = "src"+File.separatorChar+"unitTests"+File.separatorChar;

    /**
     * The original test data file.
     */
    private final String ORIGINAL_TEST_DATA_FILE = TEST_FILE_PATH + "original_test_data.txt";

    /**
     * The temporary copy of the original test data file for processing.
     */
    private final String TEST_DATA_FILE = TEST_FILE_PATH + "test_data.txt";

    /**
     * The known key name to define Contact objects in file.
     */
    private final String CONTACT_KEY = "contact";

    /**
     * The known key name to define Meeting objects in file.
     */
    private final String MEETING_KEY = "meeting";

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
    private final int CONTACT_ID_NOT_REAL = 4;

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
    private final int CONTACT_ID_NOT_IN_MEETING = 5;
    
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
    private final int CONTACT_ID_PAST = 6;
    
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
    private final int CONTACT_ID_PRESENT = 7;
    
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
    private final int CONTACT_ID_FUTURE = 8;
    
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
    private Set<Contact> notInMeetingsContactList = new HashSet<Contact>();

    /**
     * The bogs meeting list to test on contacts that do not exist.
     */
    private Set<Contact> bogusContactList = new HashSet<Contact>();

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
    private final int MEETING_NOT_EXISTING_ID = 1;

    /**
     * The Present Meeting id.
     */
    private final int MEETING_ID_PRESENT = 2;

    /**
     * The Present Meeting notes.
     */
    private final String MEETING_NOTES_PRESENT = "Present meeting notes";

    /**
     * Past Meeting id.
     */
    private final int MEETING_ID_PAST = 3;
    
    /**
     * Past Meeting notes.
     */
    private final String MEETING_NOTES_PAST = "Past meeting notes";
    
    /**
     * Future Meeting id.
     */
    private final int MEETING_ID_FUTURE = 4;

    /**
     * Future Meeting id with past date.
     */
    private final int MEETING_ID_FUTURE_PAST_DATE = 5;

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
     * The Future Meeting testing object handler with a past date.
     */
    private Meeting futureMeetingPastDate;

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

        // Generate the Original test file if required.
        if (generateOriginalFile) generateOriginalFile();

        // Create a copy of the original test file into the test file to be used
        copyOriginalTestToTestFile();

        // Initialising contactManager.
        contactManager = new ContactManagerImpl(TEST_DATA_FILE);
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
        // Search for the one contact id that should definitely be in memory.
        Set<Contact> contactListFound = contactManager.getContacts(CONTACT_ID_PAST);

        // Ensure null was not returned
        assertNotNull(contactListFound);

        // Check if we have got one result only as expected
        assertTrue(contactListFound.size() == 1);

        // Retrieve the contact info to check if it matches to the result we expect
        Contact contactFound = contactListFound.iterator().next();
        assertTrue(contactFound.getId() == CONTACT_ID_PAST);
        assertEquals(contactFound.getName(),CONTACT_NAME_PAST);
        assertEquals(contactFound.getNotes(),CONTACT_NOTES_PAST);
    }

    /** 
     * Check if the list of contacts returned corresponds to the given ids.
     */ 
    @Test
    public void testGetContactsByIds() { 
        // Get the list of contacts for the three ids to check
        Set<Contact> contactListFound = contactManager.getContacts(CONTACT_ID_PAST,
                CONTACT_ID_SINGLE_SEARCH_RESULT,CONTACT_ID_MULTIPLE_SEARCH_RESULT);

        verifyContactLists(contactList,contactListFound);
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
        // Search for a contact name that should have multiple results
        Set<Contact> foundContactList = contactManager.getContacts(CONTACT_NAME_MULTIPLE_SEARCH_RESULT);

        // Check that the size is more than 1.
        assertTrue(foundContactList.size() > 1);

        // Go over all that is expected and match to what was found.
        for( Contact c : multipleContactList ) {
            List<Contact> found = foundContactList.stream()
                    .filter(contact -> contact.getId() == c.getId())
                    .collect(Collectors.toList());

            // There should be only one record found.
            assertTrue(found.size() == 1);

            // Check if this contact is match.
            verify(c,found.get(0));
        }
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
        // Get the Meeting for the requested meeting id
        Meeting meetingFound = contactManager.getMeeting(MEETING_ID_PRESENT);
        // This meeting is expected to exist.
        assertNotNull(meetingFound);
        // Ensure we have got the correct one.
        verify(presentMeeting,meetingFound);
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
        // Need to test with a FutureMeeting with a date set in the past
        contactManager.addMeetingNotes(MEETING_ID_FUTURE_PAST_DATE, MEETING_NOTES_PRESENT);
        Meeting meetingFound = contactManager.getMeeting(MEETING_ID_FUTURE_PAST_DATE);
        assertNotNull(meetingFound);
        assertEquals(PastMeetingImpl.class,meetingFound.getClass());
    }
    
    /** 
     * Test if returned PastMeeting contain added notes.
     */ 
    @Test
    public void testAddMeetingNotesReturnPastMeetingWithNotes() { 
        contactManager.addMeetingNotes(MEETING_ID_FUTURE_PAST_DATE, MEETING_NOTES_PRESENT);
        PastMeeting meetingFound = contactManager.getPastMeeting(MEETING_ID_FUTURE_PAST_DATE);
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
        assertEquals(MEETING_ID_PAST, pastMeetingFound.getId());

        // Verify all expected contacts are there.
        verifyContactLists(pastMeeting.getContacts(),pastMeetingFound.getContacts());

        // Check if predefined past meeting date matches the expected.
        verify(DATE_PAST, pastMeetingFound.getDate());
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
     * Test if list is returned empty on none found for this contact.
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
    @Test
    public void testGetPastMeetingListSorted() { 
        // Get a list of past meetings.
        List<PastMeeting> pastMeetingList = contactManager.getPastMeetingList(pastContact);

        // Check that we do not have a null list.
        assertNotNull(pastMeetingList);

        // Expect at least one element.
        assertTrue(pastMeetingList.size() > 0);

        // Keep a record of previous checked date
        Calendar previousDate = null;

        // Keep a flag on all sorted until told otherwise..
        Boolean sorted = true;

        // Check that these come all sorted
        for(PastMeeting pastMeeting : pastMeetingList) {
            if ( previousDate == null ) {
                previousDate = pastMeeting.getDate();
            }
            else {
                if ( previousDate.before(pastMeeting.getDate()) ) {
                    previousDate = pastMeeting.getDate();
                }
                else {
                    sorted = false;
                    continue;
                }
            }
        }

        // Final check: sorted?
        assertTrue(sorted);
    }
    
    /** 
     * Test if returned list does not contain any duplicates. 
     */ 
    @Test
    public void testGetPastMeetingListNoDups() {
        // Get the list.
        List<PastMeeting> pastMeetingFound = contactManager.getPastMeetingList(pastContact);

        // Ensure we have one
        assertNotNull(pastMeetingFound);

        // TODO:
        // Ensure it is not empty and more than one element is found.
        // This forces to revisit the test if only one element is produced.
        assertTrue(pastMeetingFound.size() > 0);

        // Start with the right foot
        Boolean hasDuplicates = false;

        // Keep a list of all seen elements
        List<PastMeeting> pastMeetingSeen = new LinkedList<PastMeeting>();

        // Traverse all found elements
        for( PastMeeting pastMeeting : pastMeetingFound ) {
            if ( pastMeetingSeen.contains(pastMeeting) ) {
                hasDuplicates = true;
                continue;
            }
            pastMeetingSeen.add(pastMeeting);
        }

        // Final moment of truth
        assertFalse(hasDuplicates);
    }

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
    @Test
    public void testAddNewPastMeeting() {
        // Random singular notes to match when searching for this meeting back.
        String newNotes = "Very specific notes about this new meeting to be found";

        // Add new Past Meeting
        contactManager.addNewPastMeeting(notInMeetingsContactList, DATE_PAST, newNotes);

        // Retrieve meeting
        List<PastMeeting> pastMeetingListFound = contactManager.getPastMeetingList(notInMeetingContact);

        // Check if exists
        assertNotNull(pastMeetingListFound);

        // Check we have only one record
        assertTrue(pastMeetingListFound.size() == 1);

        // The Meeting Found from searching
        PastMeeting searchedFoundMeeting = null;

        // Scan through the meetings in search for that past date and contact notes.
        for( PastMeeting pastMeeting : pastMeetingListFound ) {
            if ( pastMeeting.getNotes().equals(newNotes) ) {
                // This is the meeting we created
                searchedFoundMeeting = contactManager.getPastMeeting(pastMeeting.getId());
                break;
            }
        }

        // The final moment of truth by checking the notes added.
        assertEquals(searchedFoundMeeting.getNotes(),newNotes);
    }

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
        contactManager.addNewPastMeeting(nonExistingContact, DATE_PAST, MEETING_NOTES_PAST);
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
        // Create a new future date with today's date one year ahead.
        Calendar dateFuture = Calendar.getInstance();
        dateFuture.add(Calendar.YEAR, 1);

        // A new future meeting with contacts not in any meeting lists.
        Integer meetingId = contactManager.addFutureMeeting(notInMeetingsContactList, dateFuture);

        // Get the future meeting that was created returning that id and check if the same.
        FutureMeeting futureMeetingCreated = contactManager.getFutureMeeting(meetingId);
        List<Meeting> futureMeetingListFromContact = contactManager.getFutureMeetingList(notInMeetingContact);

        // Check we have got something.
        assertNotNull(futureMeetingCreated);
        assertNotNull(futureMeetingListFromContact);

        // Check we have got only one.
        assertTrue(futureMeetingListFromContact.size() == 1);

        // The contact given to create the FutureMeeting is expected to be unique.
        Meeting foundMeeting  = futureMeetingListFromContact.get(0);
        Set<Contact> contacts = foundMeeting.getContacts();

        // The one-only records in each list should perfectly match
        verify(notInMeetingsContactList.iterator().next(),contacts.iterator().next());
    }
    
    /** 
     * Testing if a new meeting to be held in the future was tried to be added in the past. 
     */ 
    @Test(expected=IllegalArgumentException.class)
    public void testAddFutureMeetingWithPastDate() {
        contactManager.addFutureMeeting(contactList, DATE_PAST);
    }
    
    /** 
     * Testing if any contact is unknown when adding a future meeting.
     */ 
    @Test(expected=IllegalArgumentException.class)
    public void testAddFutureMeetingWithUnknownContact() { 
        contactManager.addFutureMeeting(bogusContactList, DATE_FUTURE);
    }
    
    /** 
     * Testing if any contact is non-existent when adding a future meeting.
     */ 
    @Test(expected=IllegalArgumentException.class)
    public void testAddFutureMeetingWithNonExistentContact() {
        contactManager.addFutureMeeting(bogusContactList, DATE_FUTURE);
    }
    
    /** 
     * Test if FUTURE meeting is returned with the requested ID
     */ 
    @Test
    public void testGetFutureMeetingId() {
        // Get the future meeting
        FutureMeeting futureMeeting = contactManager.getFutureMeeting(MEETING_ID_FUTURE);

        // Check that we have a future meeting
        assertNotNull(futureMeeting);

        // Check that the returned elements match
        assertEquals(MEETING_ID_FUTURE,futureMeeting.getId());
        verify(DATE_FUTURE, futureMeeting.getDate());
        Set<Contact> contactsSet = futureMeeting.getContacts();

        // Ensure we got a contactsSet
        assertNotNull(contactsSet);

        // Check that we have this contact in the found list.
        contactsSet.stream().forEach(contact -> verify(contact,futureContact) );
    }

    /** 
     * Test null if no future meeting found for given id.
     */ 
    @Test
    public void testGetFutureMeetingIdNonExisting() {
        FutureMeeting futureMeetingFound = contactManager.getFutureMeeting(MEETING_NOT_EXISTING_ID);

        // The id was not real, expecting a null.
        assertNull(futureMeetingFound);
    }
    
    /** 
     * Check if exception is thrown when meeting id has date in the past.
     */ 
    @Test(expected=IllegalArgumentException.class)
    public void testGetFutureMeetingIdWithDateInThePast() { 
        contactManager.getFutureMeeting(MEETING_ID_PAST);
    }
    
    /** 
     * Check if the list of future meetings scheduled with this contact is returned. 
     */ 
    @Test
    public void testGetFutureMeetingListByContact() {
        // Request list of a known to be FutureMeeting.
        List<Meeting> futureMeetingsFound = contactManager.getFutureMeetingList(futureContact);

        // Check that we have got something
        assertNotNull(futureMeetingsFound);

        // Check we have got at least one
        assertTrue(futureMeetingsFound.size() > 0);

        // Make sure we have got at least one future meeting and no other.
        Boolean gotFutureMeeting = false;
        Boolean gotOtherMeetings = false;

        // Check that we have the expected future meeting
        for( Meeting meetingFound : futureMeetingsFound ) {
            if ( meetingFound.getClass().getSimpleName().equals(FutureMeetingImpl.class.getSimpleName()) ) {
                gotFutureMeeting = true;
            } else {
                gotOtherMeetings = true;
            }
        }

        // Final test
        assertTrue(gotFutureMeeting);
        assertFalse(gotOtherMeetings);
    }

    /** 
     * Check if an empty list is returned on no future meetings.
     */ 
    @Test
    public void testGetFutureMeetingListByContactEmptyList() { 
        List<Meeting> foundNoFutureMeetingsList = contactManager.getFutureMeetingList(pastContact);

        // Empty list is not a null response.
        assertNotNull(foundNoFutureMeetingsList);

        // Check it is empty
        assertTrue(foundNoFutureMeetingsList.size() == 0);
    }
    
    /** 
     * Check if chronologically sorted list is returned.
     */ 
    @Test
    public void testGetFutureMeetingListByContactSorted() { 
        List<Meeting> futureMeetingsList = contactManager.getFutureMeetingList(futureContact);

        // Check if not null
        assertNotNull(futureMeetingsList);

        // check at least two records
        assertTrue(futureMeetingsList.size() > 1);

        // Save previous meeting for comparison
        Meeting previousMeeting = null;

        // Sorted flag true by default. 
        Boolean sorted = true;

        // Ensure they all come chronologically sorted
        for( Meeting meeting : futureMeetingsList ) {
            if ( previousMeeting == null ) {
                previousMeeting = meeting;
            }
            else {
                if ( previousMeeting.getDate().after(meeting.getDate()) ) {
                    sorted = false;
                    // Once one record is not in order, no need to keep checking
                    break;
                }
            }
        }

        // Check if the returned list is still sorted.
        assertTrue(sorted);
    }

    /** 
     * Check if no duplicates are returned.
     */ 
    @Test
    public void testGetFutureMeetingListByContactNoDups() { 
        List<Meeting> futureMeetingListFound = contactManager.getFutureMeetingList(futureContact);

        // check it is not null
        assertNotNull(futureMeetingListFound);

        // check we have more than one record
        assertTrue(futureMeetingListFound.size() > 1);

        // Meetings already seen
        List<Meeting> seenMeetings = new LinkedList<Meeting>();

        // Assume no dups, until told otherwise
        Boolean hasDups = false;

        for(Meeting meeting : futureMeetingListFound) {
            if ( seenMeetings.contains(meeting) ) {
                hasDups = true;
                continue;
            }
            else {
                seenMeetings.add(meeting);
            }
        }

        // Final test
        assertFalse(hasDups);
    }
    
    /** 
     * Check if an exception is thrown when a contact does not exist.
     */ 
    @Test(expected=IllegalArgumentException.class)
    public void testGetFutureMeetingListByContactNonExistent() { 
       contactManager.getFutureMeetingList(bogusContact);
    }

    /** 
     * Test if the list of meetings that are scheduled for, or that took 
     * place on, the specified date is returned.
     */ 
    @Test
    public void testGetFutureMeetingListByDate() { 
        // Create a new future date
        Calendar newFutureDate = Calendar.getInstance();
        newFutureDate.add(Calendar.HOUR_OF_DAY, 1);

        List<Meeting> foundMeetingsList = contactManager.getFutureMeetingList(newFutureDate);

        // Check not null
        assertNotNull(foundMeetingsList);

        // It is expected to have one record at least
        assertTrue(foundMeetingsList.size() > 0);

        // Only need one failed date to fail the test.
        Boolean allInDate = true;

        // Check they are all on the given data
        for( Meeting meeting : foundMeetingsList ) {
            // If one fails, no need to check more.
            if (! meeting.getDate().after(newFutureDate) )  {
                allInDate = false;
                break;
            }
        }

        // Final check
        assertTrue(allInDate);

    }

    /** 
     * Check that the list returned is chronologically sorted.
     */ 
    @Test
    public void testGetFutureMeetingListByDateSorted() { 
        // Create a new future date
        Calendar newFutureDate = Calendar.getInstance();
        newFutureDate.add(Calendar.HOUR_OF_DAY, 1);

        List<Meeting> futureMeetingsList = contactManager.getFutureMeetingList(newFutureDate);

        // Check if not null
        assertNotNull(futureMeetingsList);

        // Expect at least one record.
        assertTrue(futureMeetingsList.size() > 0);

        // Save previous meeting for comparison
        Meeting previousMeeting = null;

        // Sorted flag true by default. 
        Boolean sorted = true;

        // Ensure they all come chronologically sorted
        for( Meeting meeting : futureMeetingsList ) {
            if ( previousMeeting == null ) {
                previousMeeting = meeting;
            }
            else {
                if ( previousMeeting.getDate().after(meeting.getDate()) ) {
                    sorted = false;
                    continue;
                }
            }
        }

        // Check if the returned list is still sorted.
        assertTrue(sorted);
    }

    /** 
     * Check that the list returned does not contain any dups.
     */ 
    @Test
    public void testGetFutureMeetingListByDateNoDups() {
        // Create a new future date
        Calendar newFutureDate = Calendar.getInstance();
        newFutureDate.add(Calendar.HOUR_OF_DAY, 1);

        List<Meeting> futureMeetingListFound = contactManager.getFutureMeetingList(newFutureDate);

        // check it is not null
        assertNotNull(futureMeetingListFound);

        // Expect at least one record
        assertTrue(futureMeetingListFound.size() > 0);

        // Assume no dups, until told otherwise
        Boolean hasDups = false;

        // Keep a list of all Meetings already seen
        List<Meeting> seenMeetings = new LinkedList<Meeting>();

        for(Meeting meeting : futureMeetingListFound) {
            // If same meeting is found in the already seen meetings, the test fails.
            if ( seenMeetings.contains(meeting) ) {
                hasDups = true;
                break;
            }
            else {
                seenMeetings.add(meeting);
            }
        }

        // Final test
        assertFalse(hasDups);
    }

    /** 
     * Check that the list returned empty if none found.
     */ 
    @Test
    public void testGetFutureMeetingListByDateNoneFound() {
        Calendar endOfTimeDate = DATE_FUTURE;
        endOfTimeDate.add(Calendar.YEAR, 10000);

        List<Meeting> foundNoFutureMeetingsList = contactManager.getFutureMeetingList(endOfTimeDate);

        // Empty list is not a null response.
        assertNotNull(foundNoFutureMeetingsList);

        // Check it is empty
        assertTrue(foundNoFutureMeetingsList.size() == 0);
    }


    // ****************************** File access tests ******************************* //

    /** 
     * Check that expected data is saved to disk.
     */ 
    @Test
    public void testFlush() {
        // Create a new contact and load it into memory.
        String newNotes = "Completely new notes";
        String newName = "Completely new name";
        contactManager.addNewContact(newName, newNotes);
        contactManager.flush();

        // Load the hopefully saved file with the new Contact.
        List<Contact> newList = null;
        try {
            newList = loadContactData(new File(TEST_DATA_FILE));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Check if the new List contains the new contact.
        Contact newContact = null;
        for(Contact contact : newList ) {
            if ( contact.getName().equals(newName) && contact.getNotes().equals(newNotes) ) {
                newContact = contact;
                break;
            }
        }

        // Time to cross-check it by Name and Notes.
        assertNotNull(newList);
        assertNotNull(newContact);
        assertEquals(newContact.getName(),newName);
        assertEquals(newContact.getNotes(),newNotes);
    }

    /** 
     * Check if when application is closed, expected data is saved in file.
     */ 
    @Test
    public void testFlushAtClose() { 
        // Add new notes to an existing meeting, close, 
        // create a new contactManager instance, and check for the new notes.
        String newNotes = "Completely new notes 2";
        contactManager.addMeetingNotes(MEETING_ID_PAST, newNotes);

        // TODO: Check how to close contactManager here without doing a direct flush.

        ContactManager cm = new ContactManagerImpl(TEST_DATA_FILE);
        PastMeeting meetingFound = cm.getPastMeeting(MEETING_ID_PAST);
        assertNotNull(meetingFound);

        assertEquals(newNotes,meetingFound.getNotes());
    }

    
    
    // **************************************************************************** //
    //                                                                              //
    //                                 HELPERS                                      //
    //                                                                              //
    // **************************************************************************** //
    
    /**
     * Generate the original test file data from the available private variable states
     * expected to exist in memory, for when the tests run.
     */
    @SuppressWarnings("unchecked")
    private void generateOriginalFile() {
        // If the file is not requested to be generated, return.
        if(!generateOriginalFile) return;

        // The original test file
        File originalFile = new File(ORIGINAL_TEST_DATA_FILE);

        // If the file does not exist, create it.
        if( !originalFile.exists() ) {
            try {
                originalFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Prepare a file writer.
        FileWriter fw = null;
        try {
            // Initialize the file writer for the original file.
            fw = new FileWriter(originalFile);

            // Adding all Contacts
            JSONObject jObject = new JSONObject();
            jObject.put(CONTACT_KEY, getContactsInJSONArray().toJSONString());

            // Adding all Meetings
            jObject.put(MEETING_KEY, getMeetingsInJSONArray().toJSONString());

            fw.append(jObject.toJSONString());

            // Saving results
            fw.flush();
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Original file is generated only once before all tests run.
        // Subsequent loads come from the copied TEST_DATA_FILE.
        generateOriginalFile = false;
    }

    /**
     * Converting all listed Contacts into the JSONArray format
     * to be then saved into the original test file.
     * 
     * @return JSONArray of all Contacts
     */
    @SuppressWarnings("unchecked")
    private JSONArray getContactsInJSONArray() {
        JSONArray jArray = new JSONArray();
        jArray.add(jUtils.toJSONObject(pastContact));
        jArray.add(jUtils.toJSONObject(presentContact));
        jArray.add(jUtils.toJSONObject(futureContact));
        jArray.add(jUtils.toJSONObject(notInMeetingContact));
        contactList.stream().forEach(contact -> jArray.add(jUtils.toJSONObject(contact)));
        return jArray;
    }

    /**
     * Converting all listed Meetings into the JSONArray format
     * to be then saved into the original test file.
     * 
     * @return JSONArray of all Meetings
     */
    @SuppressWarnings("unchecked")
    private JSONArray getMeetingsInJSONArray() {
        JSONArray jMeetings = new JSONArray();
        jMeetings.add(jUtils.toJSONObject(pastMeeting));
        jMeetings.add(jUtils.toJSONObject(presentMeeting));
        jMeetings.add(jUtils.toJSONObject(futureMeeting));
        jMeetings.add(jUtils.toJSONObject(futureMeetingPastDate));
        return jMeetings;
    }

    /**
     * Initialise all meetings with the default values.
     */
    private void defaultMeetingInit() {
        try {
            pastMeeting           = new PastMeetingImpl(MEETING_ID_PAST,DATE_PAST,pastContactList,MEETING_NOTES_PAST);
            presentMeeting        = new MeetingImpl(MEETING_ID_PRESENT,DATE_PRESENT,presentContactList);
            futureMeeting         = new FutureMeetingImpl(MEETING_ID_FUTURE,DATE_FUTURE,futureContactList);
            futureMeetingPastDate = new FutureMeetingImpl(MEETING_ID_FUTURE_PAST_DATE,DATE_PAST,futureContactList);
        } catch (Exception e) {
            // Keep calm and carry on.
        }
    }
    
    /**
     * Initialise all Calendar dates with the default values.
     */
    private void defaultCalendarInit() {
        DATE_FUTURE = Calendar.getInstance();
        DATE_FUTURE.add(Calendar.DAY_OF_MONTH, 1);
        DATE_PAST = Calendar.getInstance();
        DATE_PAST.add(Calendar.DAY_OF_MONTH, -1);
        DATE_PRESENT = Calendar.getInstance();
    }
    
    /**
     * Initialise all contacts and contact lists with the default values.
     */
    private void defaultContactInit() {
        // Adding all contacts existing to the contactList to be acted upon.
        contactList.add(new ContactImpl(CONTACT_ID_PAST, CONTACT_NAME_PAST, 
                CONTACT_NOTES_PAST));
        contactList.add(new ContactImpl(CONTACT_ID_SINGLE_SEARCH_RESULT, 
                CONTACT_NAME_SINGLE_SEARCH_RESULT, CONTACT_NOTES_SINGLE));
        contactList.add(new ContactImpl(CONTACT_ID_MULTIPLE_SEARCH_RESULT, 
                CONTACT_NAME_MULTIPLE_SEARCH_RESULT, CONTACT_NOTES_MULTIPLE));

        // Initialise past, present and future contact.
        pastContact         = new ContactImpl(CONTACT_ID_PAST, CONTACT_NAME_PAST, CONTACT_NOTES_PAST);
        presentContact      = new ContactImpl(CONTACT_ID_PRESENT, CONTACT_NAME_PRESENT, CONTACT_NOTES_PRESENT);
        futureContact       = new ContactImpl(CONTACT_ID_FUTURE, CONTACT_NAME_FUTURE, CONTACT_NOTES_FUTURE);
        notInMeetingContact = new ContactImpl(CONTACT_ID_NOT_IN_MEETING, CONTACT_NAME_NOT_IN_MEETING, CONTACT_NOTES_NOT_IN_MEETING);
        bogusContact        = new ContactImpl(CONTACT_ID_NOT_REAL, CONTACT_NAME_NOT_REAL, CONTACT_NOTES_NOT_REAL);

        // The multiple list search result expected.
        multipleContactList.add(futureContact);
        multipleContactList.add(pastContact);
        multipleContactList.add(presentContact);
        multipleContactList.add(new ContactImpl(CONTACT_ID_MULTIPLE_SEARCH_RESULT, 
                CONTACT_NAME_MULTIPLE_SEARCH_RESULT, CONTACT_NOTES_MULTIPLE));

        // The past contact list.
        pastContactList.add(pastContact);
        // The present contact list.
        presentContactList.add(presentContact);
        // The future contact list.
        futureContactList.add(futureContact);
        // The not in meeting contact list.
        notInMeetingsContactList.add(notInMeetingContact);
        // The bogus contact list.
        bogusContactList.add(bogusContact);
    }

    /**
     * Given a set list of contacts, check if all expected contacts match found.
     * 
     * @param contactListFound the Set of Contacts found.
     */
    private void verifyContactLists(Set<Contact> contactListExpected, Set<Contact> contactListFound) {
        // Ensure we have got something returned
        assertNotNull(contactListFound);

        // Check if we have same sized lists
        assertEquals(contactListExpected.size(),contactListFound.size());

        // Expected all records to be correctly matched.
        int foundCorrect = 0;

        // Check details for each contact in list and check if match.
        for(Contact contactExpected : contactListExpected) {
            for( Contact contactFound : contactListFound ) {
                if ( contactExpected.getId() == contactFound.getId() ) {
                    verify(contactExpected, contactFound);
                    foundCorrect++;
                }
            }
        }
        
        // re-ensure we have got all three correct.
        assertEquals(contactListExpected.size(), foundCorrect);
    }
 
    /**
     * Create a copy of the original test file to the test file name to be used by all tests.
     */
    private void copyOriginalTestToTestFile() {
        File originalFile = new File(ORIGINAL_TEST_DATA_FILE);
        File copyFile     = new File(TEST_DATA_FILE);

        // If we do not have the original file, this needs to be aborted
        if ( !originalFile.exists() ) {
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Create the copy new file.
        if ( !copyFile.exists() ) {
            try {
                copyFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            // If copy file exists, deletes it first and then 
            // creates a new one, to be sure we have a new clean file.
            copyFile.delete();
            try {
                copyFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Load all data from original file and write into the copy file.
        BufferedReader in = null;
        FileWriter fileWriter = null;
        try {
            in = new BufferedReader(new FileReader(originalFile));
            String line;
            fileWriter = new FileWriter(copyFile);

            while((line = in.readLine()) != null ) {
               fileWriter.write(line+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            if ( fileWriter != null ) {
                try {
                    fileWriter.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        if ( in != null ) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if ( fileWriter != null ) {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Verify if expected meeting match the found meeting
     * 
     * @param expected Meeting
     * @param found Meeting
     */
    private void verify(Meeting expected, Meeting found) {
        // found cannot be null
        assertNotNull(found);

        // Meeting ids much match
        assertEquals(expected.getId(),found.getId());

        // Dates much match
        verify(expected.getDate(),found.getDate());

        // Checking through the contact list
        int matchingContactsExpected = found.getContacts().size();
        int matchedContactsFound     = 0;

        for( Contact contactFound : found.getContacts() ) {
            for( Contact contactExpected : expected.getContacts() ) {
                if( contactFound.getId() == contactExpected.getId() ) {
                    verify(contactExpected,contactFound);
                    // Increment the matched Contacts found and jump to next contactFound
                    matchedContactsFound++;
                    break;
                }
            }
        }

        assertTrue(matchingContactsExpected == matchedContactsFound);
    }

    /**
     * Verify if the expected Contact matched with the found one.
     * 
     * @param expected Contact
     * @param found Contact
     */
    private void verify(Contact expected, Contact found) {
        assertNotNull(found);
        assertEquals(expected.getId(),found.getId());
        assertEquals(expected.getName(),found.getName());
        assertEquals(expected.getNotes(),found.getNotes());
    }

    /**
     * Verify if expected date matched the found Calendar date.
     * 
     * @param expected Calendar date
     * @param found Calendar date
     */
    private void verify(Calendar expected, Calendar found) {
        assertNotNull(found);
        assertEquals(expected.get(Calendar.YEAR),found.get(Calendar.YEAR));
        assertEquals(expected.get(Calendar.MONTH),found.get(Calendar.MONTH));
        assertEquals(expected.get(Calendar.DAY_OF_MONTH),found.get(Calendar.DAY_OF_MONTH));
        assertEquals(expected.get(Calendar.HOUR_OF_DAY),found.get(Calendar.HOUR_OF_DAY));
        assertEquals(expected.get(Calendar.MINUTE),found.get(Calendar.MINUTE));
        assertEquals(expected.get(Calendar.SECOND),found.get(Calendar.SECOND));
    }

    /**
     * Loads all Contact from the given file name and returns all Contacts in a list form.
     */
    private List<Contact> loadContactData(File file) throws ParseException, IOException {
        List<Contact> finalList = new LinkedList<Contact>();

        // If no file, return an empty list.
        if ( !file.exists() ) return finalList;

        // Create a new instance of the JUtils.
        JSONUtils jUtils = new JSONUtilsImpl();

        // Prepare to load from file.
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            String line;
            while((line = in.readLine()) != null ) {
                JSONParser parser = new JSONParser();
                JSONObject jo     = (JSONObject) parser.parse(line);

                // If the json object is not null, collect the key values and then 
                // loop through them, converting the values into the expected object form
                @SuppressWarnings("rawtypes")
                Iterator joIterator = jo.keySet().iterator();

                // Loop through all available keys to load from file.
                while( joIterator.hasNext()) {

                    // Get the next key
                    String joKey = joIterator.next().toString();

                    // Grab the value associated to the above key
                    JSONArray joArray = (JSONArray) parser.parse(jo.get(joKey).toString());

                    // Instantiate a new JSON Object element
                    JSONObject element = new JSONObject();

                    // Switch for all possible expected keys to process data
                    switch (joKey) {
                        // Check if found joKey is a CONTACT_KEY
                        case CONTACT_KEY : {
                           // Loop through all elements and load them into the contactList.
                           for( int i = 0; i < joArray.size(); i++ ) {
                               element = (JSONObject) joArray.get(i);
                               finalList.add(jUtils.toContact(element));
                           }
                           break;
                        }
                    }
                }
            }

            // Close Buffer if not closed yet.
            if ( in != null ) in.close();

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalList;
    }
}