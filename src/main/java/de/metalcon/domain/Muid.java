package de.metalcon.domain;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import de.metalcon.exceptions.MetalconRuntimeException;

/**
 * unique identifier for a Metalcon Metalcon Unique IDentifier
 */
public class Muid implements Serializable {
	private static byte sourceID = 0;

	private static int lastCreationTime = 0;

	/**
	 * The number of MUIDs created at <lastCreationTime>
	 */
	private static AtomicInteger currentID = new AtomicInteger(0);

	private static final long serialVersionUID = 1L;

	public static final Muid EMPTY_MUID = new Muid(0);

	/**
	 * unique identifier
	 */
	private final long value;

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
	public static Muid create(final MuidType type) {
		int timestamp = (int) (System.currentTimeMillis() / 1000);
		short ID = 0;
		if (timestamp == lastCreationTime) {
			// We have already created a MUID during the current second
			ID = (short) currentID.incrementAndGet();
		}
		lastCreationTime = timestamp;

		return new Muid(MuidConverter.calculateMuid(type.getRawIdentifier(),
				sourceID, timestamp, ID));
	}

	/**
	 * create new Muid instance with an already given value
	 * 
	 * @param value
	 *            unique identifier
	 */
	public Muid(long value) {
		this.value = value;

		if (!MuidConverter.checkType(getTypeValue())) {
			throw new MetalconRuntimeException(
					"Muid Type may not be larger or equal to " + (1 << 9));
		}

		if (!MuidConverter.checkSource(getSource())) {
			throw new MetalconRuntimeException(
					"Muid Source may not be larger or equal to " + (1 << 5));
		}
	}

	/**
	 * create a new Muid instance with an already given value in alphanumeric
	 * form
	 * 
	 * @param alphaNumericValue
	 *            unique identifier in base64 format
	 */
	public Muid(String alphaNumericValue) {
		value = MuidConverter.deserialize(alphaNumericValue);
	}

	/**
	 * @return unique identifier
	 */
	public long getValue() {
		return value;
	}

	/**
	 * Returns the type stored within the given MUID
	 * 
	 * @param muid
	 *            The MUID storing the type searched for
	 * @return The type within the given muid
	 */
	public short getTypeValue() {
		return MuidConverter.getType(value);
	}

	/**
	 * @return type of the MuidType enum represented by this MUID's type
	 */
	public MuidType getMuidType() throws UnknownMuidException {
		return MuidType.parseShort(MuidConverter.getType(value));
	}

	/**
	 * Returns the source that generated the given MUID
	 * 
	 * @param muid
	 *            The MUID storing the source searched for
	 * @return The source that created the given MUID
	 */
	public byte getSource() {
		return MuidConverter.getSource(value);
	}

	/**
	 * Returns the timestamp the given MUID has been created
	 * 
	 * @param muid
	 *            The MUID storing the timestamp searched for
	 * @return The timestamp the given MUID has been created at
	 */
	public int getTimestamp() {
		return MuidConverter.getTimestamp(value);
	}

	/**
	 * Returns the ID stored within the given MUID
	 * 
	 * @param muid
	 *            The MUID storing the ID searched for
	 * @return The ID within the given muid
	 */
	public short getID() {
		return MuidConverter.getID(value);
	}

	@Override
	public String toString() {
		return MuidConverter.serialize(value);
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other == null || getClass() != other.getClass()) {
			return false;
		}
		Muid o = (Muid) other;
		return value == o.value;
	}

	@Override
	public int hashCode() {
		int hash = 9823;
		int mult = 887;

		hash = hash * mult + ((Long) value).hashCode();

		return hash;
	}

	/**
	 * Returns the path corresponding to the muid. The path consist of 3 folders
	 * with one hex character as name (0-f). The letters are generated from 4
	 * consecutive bits in the 32-bit hash of the MUID. The path will be in the
	 * format [0-9a-f]/[0-9a-f]/[0-9a-f]/
	 * 
	 * @return The path that should be used to store data corresponding to this
	 *         Muid
	 */
	public String getStoragePath() {
		return MuidConverter.getMUIDStoragePath(value);
	}

	/**
	 * Returns all characters a MUID path may consist of
	 * 
	 * @return All allowed characters
	 */
	public static char[] getAllowedFolderNames() {
		return MuidConverter.getAllowedFolderNames();
	}
}
