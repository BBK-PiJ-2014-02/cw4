package contactManager;

import java.util.Calendar;

import org.json.simple.JSONObject;

public interface JSONUtils {
    /**
     * Given a Calendar date, returns the JSONObject for given
     * date on year, month, day, hour, minute, second and millisecond.
     * 
     * @param date Calendar date
     * @return JSONOnject
     */
    JSONObject toJSONObject(Calendar date);

    /**
     * Given a Contact, returns the JSONObject for given Contact
     * in the JSONObject format, ready to be saved on the file.
     * 
     * @param contact to be converted
     * @return JSONObject
     */
    JSONObject toJSONObject(Contact contact);

    /**
     * Given a Meeting, returns the JSONObject for given Meeting
     * in the JSONObject format, ready to be saved on the file.
     * 
     * @param meeting to be converted
     * @return JSONObject
     */
    JSONObject toJSONObject(Meeting meeting);

    /**
     * Convert a JSONObject into a Calendar object.
     * 
     * @param jObject of type Calendar
     * @return Calendar
     */
    Calendar toCalendar(JSONObject jObject);
    
    /**
     * Convert a JSONObject into a Contact object.
     * 
     * @param jObject of type Contact
     * @return Contact
     */
    Contact toContact(JSONObject jObject);
    
    /**
     * Convert a JSONObject into a Meeting object
     * 
     * @param jObject of type meeting
     * @return Meeting
     */
    Meeting toMeeting(JSONObject jObject);
}
