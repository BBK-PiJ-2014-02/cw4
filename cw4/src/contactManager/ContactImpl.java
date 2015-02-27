package contactManager;

/**
 * The Contact class implementation.
 * 
 * @author Vasco
 *
 */
public class ContactImpl implements Contact {
	/**
	 * The Contact Id.
	 */
	private final int id;
	
	/**
	 * The Contact name.
	 */
	private final String name;
	
	/**
	 * Contact notes.
	 */
	private String notes;

	/**
	 * Constructor.
	 * 
	 * @param id the contact's id
	 * @param name the contact's name
	 * @param notes the contact's notes
	 */
	public ContactImpl(int id, String name, String notes) {
		this.id    = id;
		this.name  = name;
		this.notes = notes;
		// If supplied notes are null, set empty string.
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
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNotes() {
		return notes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addNotes(String notes) {
		this.notes = notes;
	}
}