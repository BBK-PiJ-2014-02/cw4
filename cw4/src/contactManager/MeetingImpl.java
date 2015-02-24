package contactManager;

import java.util.Calendar;
import java.util.Set;

/**
 * The Meeting Implementation class.
 * 
 * @author Vasco
 *
 */
public class MeetingImpl implements Meeting {
	/**
	 * The meeting id
	 */
	private final int id;
	
	/**
	 * The meeting date
	 */
	private final Calendar date;
	
	/**
	 * The list of contacts present at the meeting.
	 */
	private final Set<Contact> contacts;
	
	/**
	 * Constructor.
	 * 
	 * @param id the meeting id
	 * @param date the meeting date
	 * @param contacts the list of contacts present at the meeting
	 * @throws Exception on empty contact set
	 */
	public MeetingImpl(int id, Calendar date, Set<Contact> contacts) throws Exception {
        // Cannot allow empty contacts to be added as per interface specs.
		if ( contacts.isEmpty() ) throw new Exception("Cannot create Meeting with an empty contact list");
		
	    this.id       = id;
	    this.date     = date;
	    this.contacts = contacts;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Calendar getDate() {
		return date;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Contact> getContacts() {
		return contacts;
	}

}
