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
	private String notes = "";

	/**
	 * Constructor.
	 * 
	 * @param name the contact's name
	 */
	public ContactImpl(int id, String name) {
		this.name  = name;
		this.id    = id;
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
