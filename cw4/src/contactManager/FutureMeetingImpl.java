package contactManager;

import java.util.Calendar;
import java.util.Set;

/**
 * A meeting to be held in the future 
 * 
 * @author Vasco
 *
 */
public class FutureMeetingImpl implements FutureMeeting {
    /**
     * The meeting id.
     */
    private final int id;
    
    /**
     * The calendar date for the meeting.
     */
    private final Calendar date;
    
    /**
     * The set of contacts present at the meeting.
     */
    private final Set<Contact> contacts;
    
    /**
     * Constructor.
     * 
     * @param ID meeting id
     * @param date meeting date
     * @param contacts set of people present at the meeting
     */
    public FutureMeetingImpl(int id, Calendar date, Set<Contact> contacts) {
        this.id = id;
        this.date = date;
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
