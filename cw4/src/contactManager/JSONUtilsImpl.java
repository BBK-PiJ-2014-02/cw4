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
 * Refactoring the JSON computation between JSON Objects and Contacts
 * and JSON Objects and Meeting and vice-versa conversions, required
 * by the contactManager, when saving and loading to / from file.
 * 
 * @author Vasco
 *
 */
public class JSONUtilsImpl implements JSONUtils {
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject toJSONObject(Contact contact) {
        JSONObject jo = new JSONObject();
        jo.put("id", contact.getId());
        jo.put("name", contact.getName());
        jo.put("notes", contact.getNotes());
        return jo;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject toJSONObject(Meeting meeting) {
        JSONObject jo = new JSONObject();
        jo.put("id", meeting.getId());
        jo.put("date", meeting.getDate());
        
        JSONArray contactsJO = new JSONArray();
        for( Contact c : meeting.getContacts() ) {
            JSONObject contactJO = new JSONObject();
            contactJO.put("id", c.getId());
            contactJO.put("name", c.getName());
            contactJO.put("notes", c.getNotes());
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
    @SuppressWarnings("unchecked")
    @Override
    public Meeting toMeeting(JSONObject jObject) {
        // Read the meeting id
        Integer id      = Integer.valueOf(jObject.get("id").toString());
        
        // Load the date.
        Calendar date   = new GregorianCalendar();
                 date = (Calendar) jObject.get("date");
                 
        // Load all set contacts.
        Set<Contact> contacts = new HashSet<Contact>();
        JSONArray jArray = (JSONArray) jObject.get("contacts");
        Iterator<JSONObject> i = jArray.iterator();
        while( i.hasNext() ) {
            JSONObject jContact = i.next();
            Contact c = toContact(jContact);
            contacts.add(c);
        }

        // Create the Meeting object to be returned.
        Meeting meeting = null;
        try {
            meeting = new MeetingImpl(id,date,contacts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return meeting;
    }
}
