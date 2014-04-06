package de.metalcon.domain;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import de.metalcon.domain.helper.UidConverter;
import de.metalcon.exceptions.MetalconRuntimeException;

/**
 * unique identifier for a Metalcon Metalcon Unique IDentifier. Additional to
 * type and sourceID (@see {@link Uid}) it stores a timestamp (time of creation)
 * and a fine time (number of created Uid within the timestamp second)
 */
public class Muid extends Uid implements Serializable {
	private static final long serialVersionUID = 6474090689412027428L;

	private static int lastCreationTime = 0;

	public static Muid EMPTY_MUID = new Muid(0);

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
		if (type == UidType.URL) {
			throw new MetalconRuntimeException(
					"Do not try to instantiate a Muid with the URL type. Use UrlID.create instead!");
		}

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
	 * Returns the timestamp this Muid has been created
	 * 
	 * @return The timestamp this Muid has been created
	 */
	public int getTimestamp() {
		if (getTypeValue() == UidType.URL.getRawIdentifier()) {

		}

		return UidConverter.getTimestamp(value);
	}

	/**
	 * Returns the fine time of this Muid (the number of created Muid within the
	 * timestamp second)
	 * 
	 * @return The fine time of this Muid
	 */
	public short getFineTime() {
		return UidConverter.getFineTime(value);
	}
}
