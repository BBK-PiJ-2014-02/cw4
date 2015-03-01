package contactManager;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Set;


/**
 * A class to manage your contacts and meetings. 
 * 
 * @author Vasco
 *
 */
public class ContactManagerImpl implements ContactManager {
	/**
	 * The default data file name where all data is stored.
	 */
	private final String fileName = "contacts.txt";

	/**
	 * File handler
	 */
	private final File file;

    // ***************************************************************************** //
    // *                                CONSTRUCTOR                                * //
    // ***************************************************************************** //
	/**
	 * Default constructor
	 */
	public ContactManagerImpl() {
		this.file = new File(this.fileName);
	}
	
	/**
	 * Constructor to store data on a different file.
	 * 
	 * @param fileName the file to store all data
	 */
	public ContactManagerImpl(String fileName) {
		this.file = new File(fileName);
	}
	
    // ***************************************************************************** //
    // *                             INTERFACE METHODS                             * //
    // ***************************************************************************** //
	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PastMeeting getPastMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FutureMeeting getFutureMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meeting getMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date,
			String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMeetingNotes(int id, String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNewContact(String name, String notes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Contact> getContacts(int... ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Contact> getContacts(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	// ***************************************************************************** //
    // *                             PRIVATE METHODS                               * //
    // ***************************************************************************** //
}
