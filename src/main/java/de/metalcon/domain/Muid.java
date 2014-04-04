package de.metalcon.domain;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * unique identifier for a Metalcon Metalcon Unique IDentifier
 */
public class Muid extends Uid implements Serializable {
	private static final long serialVersionUID = 6474090689412027428L;

	private static int lastCreationTime = 0;

	/**
	 * The number of MUIDs created at <lastCreationTime>
	 */
	private static AtomicInteger lastCreatedID = new AtomicInteger(0);

	/**
	 * creates a new Muid object with a unique ID
	 * 
	 * If a Muid with the same type has already been created during the current
	 * second the ID value will be incremented
	 * 
	 * @param type
	 *            the type of the Muid to be created
	 * @returna new unique Muid object
	 */
	public static Muid create(final UidType type) {
		int timestamp = (int) (System.currentTimeMillis() / 1000);
		short ID = 0;
		if (timestamp == lastCreationTime) {
			// We have already created a MUID during the current second
			ID = (short) lastCreatedID.incrementAndGet();
		} else {
			lastCreatedID.set(0);
		}
		lastCreationTime = timestamp;

		return new Muid(UidConverter.calculateMuid(type.getRawIdentifier(),
				sourceID, timestamp, ID));
	}

	/**
	 * create new Muid instance with an already given value
	 * 
	 * @param value
	 *            unique identifier
	 */
	public Muid(long value) {
		super(value);
	}

	/**
	 * create a new Muid instance with an already given value in alphanumeric
	 * form
	 * 
	 * @param alphaNumericValue
	 *            unique identifier in base64 format
	 */
	public Muid(String alphaNumericValue) {
		super(alphaNumericValue);
	}

	/**
	 * Returns the ID stored within the given MUID
	 * 
	 * @param muid
	 *            The MUID storing the ID searched for
	 * @return The ID within the given muid
	 */
	public short getID() {
		return UidConverter.getID(value);
	}
}
