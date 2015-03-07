package contactManager;

import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

public interface JSONUtils extends JSONStreamAware {
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
