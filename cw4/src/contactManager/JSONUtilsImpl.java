package contactManager;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Implementation of the JSONUtils interface.
 * 
 * Re-factoring the JSON computation between JSON Objects and Contacts
 * and JSON Objects and Meeting and vice-versa conversions, required
 * by the contactManager, when saving and loading to / from file.
 * 
 * @author Vasco
 *
 */
public class JSONUtilsImpl implements JSONUtils {
    /**
     * The PastMeeting type.
     */
    private final String TYPE_PAST_MEETING = "PastMeeting";

    /**
     * The Meeting type.
     */
    private final String TYPE_MEETING = "Meeting";

    /**
     * The FutureMeeting type.
     */
    private final String TYPE_FUTURE_MEETING = "FutureMeeting";

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject toJSONObject(Contact contact) {
        JSONObject jo = new JSONObject();
        // If nothing is returned, keep integrity by returning
        // an empty JSONObject.
        if ( contact == null ) return jo;

        jo.put("id", contact.getId());
        jo.put("name", contact.getName().toString());
        jo.put("notes", contact.getNotes().toString());
        return jo;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject toJSONObject(Meeting meeting) {
        JSONObject jo = new JSONObject();

        // If meeting is null, keep integrity by returning and empty JSON Object.
        if ( meeting == null ) return jo;

        // Get the Interface name for this class. 
        // E.g.: FutureMeeting, PastMeeting, Meeting,
        // and pass this on to the JSONObject.
        String classSimpleName = meeting.getClass().getInterfaces()[0].getSimpleName();
        jo.put("type", classSimpleName);
        jo.put("id", meeting.getId());
        jo.put("date", toJSONObject(meeting.getDate()));
        if ( classSimpleName.equals(TYPE_PAST_MEETING)) {
            PastMeeting pastMeeting = (PastMeeting) meeting;
            jo.put("notes", pastMeeting.getNotes());
        }

        JSONArray contactsJO = new JSONArray();
        for( Contact c : meeting.getContacts() ) {
            JSONObject contactJO = new JSONObject();
            contactJO.put("id", c.getId());
            contactJO.put("name", c.getName().toString());
            contactJO.put("notes", c.getNotes().toString());
            contactsJO.add(contactJO);
        }

        jo.put("contacts", contactsJO);
        return jo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Contact toContact(JSONObject jObject) {
        if (jObject == null) return null;
        Integer id   = Integer.valueOf(jObject.get("id").toString());
        String name  = jObject.get("name").toString();
        String notes = jObject.get("notes").toString();

        return new ContactImpl(id,name,notes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Meeting toMeeting(JSONObject jObject) {
        // If jObject is null, return null
        if ( jObject == null ) return null;

        // Read the meeting Interface to be had
        String meetingInterface = jObject.get("type").toString();

        // Read the meeting id
        Integer id = Integer.valueOf(jObject.get("id").toString());

        // Load the date.
        JSONObject jDate = (JSONObject) jObject.get("date");
        Calendar date = toCalendar(jDate);

        // Load all set contacts.
        Set<Contact> contacts = getContacts(jObject);

        // Build the Meeting to be returned with all the above info
        if ( meetingInterface.equals(TYPE_MEETING)) {
            // Create the Meeting object to be returned.
            Meeting meeting = null;
            try {
                meeting = new MeetingImpl(id,date,contacts);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return meeting;
        }
        else {
            throw new IllegalArgumentException("Cannot convert a meeting type " + meetingInterface + " into a Meeting.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PastMeeting toPastMeeting(JSONObject jObject) {
        // If jObject is null, return null
        if ( jObject == null ) return null;

        // Read the meeting Interface to be had
        String meetingInterface = jObject.get("type").toString();

        // Read the meeting id
        Integer id = Integer.valueOf(jObject.get("id").toString());

        // Load the date.
        JSONObject jDate = (JSONObject) jObject.get("date");
        Calendar date = toCalendar(jDate);

        // Load all set contacts.
        Set<Contact> contacts = getContacts(jObject);

        // Build the PastMeeting to be returned with all the above info
        if ( meetingInterface.equals(TYPE_PAST_MEETING)) {
            // Create the PastMeeting object to be returned.
            PastMeeting pastMeeting = null;

            // Get the Notes
            String notes = jObject.get("notes").toString();

            try {
                pastMeeting = new PastMeetingImpl(id,date,contacts,notes);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return pastMeeting;
        }
        else {
            throw new IllegalArgumentException("Cannot convert a meeting type " + meetingInterface + " into a PastMeeting.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FutureMeeting toFutureMeeting(JSONObject jObject) {
        // If jObject is null, return null
        if ( jObject == null ) return null;

        // Read the meeting Interface to be had
        String meetingInterface = jObject.get("type").toString();

        // Read the meeting id
        Integer id = Integer.valueOf(jObject.get("id").toString());

        // Load the date.
        JSONObject jDate = (JSONObject) jObject.get("date");
        Calendar date = toCalendar(jDate);

        // Load all set contacts.
        Set<Contact> contacts = getContacts(jObject);

        // Build the FutureMeeting to be returned with all the above info
        if ( meetingInterface.equals(TYPE_FUTURE_MEETING)) {
            // Create the FutureMeeting object to be returned.
            FutureMeeting futureMeeting = null;
            try {
                futureMeeting = new FutureMeetingImpl(id,date,contacts);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return futureMeeting;
        }
        else {
            throw new IllegalArgumentException("Cannot convert a meeting type " + meetingInterface + " into a FutureMeeting.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({ "unchecked" })
    @Override
    public JSONObject toJSONObject(Calendar date) {
        // Create the JSON object to be returned
        JSONObject jo = new JSONObject();

        // If not date was supplied, return the empty JSON Object
        if ( date == null ) return jo;

        Integer year   = date.get(Calendar.YEAR);
        Integer month  = date.get(Calendar.MONTH);
        Integer day    = date.get(Calendar.DAY_OF_MONTH);
        Integer hour   = date.get(Calendar.HOUR_OF_DAY);
        Integer minute = date.get(Calendar.MINUTE);
        Integer second = date.get(Calendar.SECOND);

        jo.put("year", year);
        jo.put("month", month);
        jo.put("day", day);
        jo.put("hour", hour);
        jo.put("minute", minute);
        jo.put("second", second);

        return jo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Calendar toCalendar(JSONObject jObject) {
        // Create a new instance of the Calendar to be returned.
        Calendar date = new GregorianCalendar();

        // If no jObject given, return date as is.
        if ( jObject == null ) return date;

        Integer year   = Integer.valueOf(jObject.get("year").toString());
        Integer month  = Integer.valueOf(jObject.get("month").toString());
        Integer day    = Integer.valueOf(jObject.get("day").toString());
        Integer hour   = Integer.valueOf(jObject.get("hour").toString());
        Integer minute = Integer.valueOf(jObject.get("minute").toString());
        Integer second = Integer.valueOf(jObject.get("second").toString());

        date.set(year, month, day, hour, minute, second);

        return date;
    }

    /**
     * Convert JSON Object with key contacts into a Set of Contact objects
     * 
     * @param jObject JSONObject
     * @return Set of Contact objects
     */
    private Set<Contact> getContacts(JSONObject jObject) {
        Set<Contact> contacts = new HashSet<Contact>();
        new HashSet<Contact>();
        JSONArray jArray = (JSONArray) jObject.get("contacts");
        @SuppressWarnings("unchecked")
        Iterator<JSONObject> i = jArray.iterator();
        while( i.hasNext() ) {
            JSONObject jContact = i.next();
            Contact c = toContact(jContact);
            contacts.add(c);
        }

        return contacts;
    }
}
