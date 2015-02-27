package contactManager;

import java.util.Calendar;
import java.util.Set;

/**
 * A meeting that was held in the past. 
 * 
 * It includes your notes about what happened and what was agreed. 
 * 
 * @author Vasco
 *
 */
public class PastMeetingImpl implements PastMeeting {
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
     * Notes from the meeting.
     */
    private String notes;

    /**
     * Constructor.
     * 
     * @param ID meeting id
     * @param date meeting date
     * @param contacts set of people present at the meeting
     * @param notes from the meeting
     */
    public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes) {
        this.id = id;
        this.date = date;
        this.contacts = contacts;
        this.notes = notes;

        // If no notes are given, make it empty string as per interface specifications.
        if ( this.notes == null ) this.notes = "";
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNotes() {
        return notes;
    }
}
