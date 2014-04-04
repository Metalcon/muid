package de.metalcon.domain;

import java.io.Serializable;

import de.metalcon.exceptions.MetalconRuntimeException;

/**
 * unique identifier for anything
 */
public class Uid implements Serializable {
	private static final long serialVersionUID = 4896224060197683465L;

	protected static byte sourceID = 0;

	/**
	 * unique identifier
	 */
	protected final long value;

	/**
	 * create new Muid instance with an already given value
	 * 
	 * @param value
	 *            unique identifier
	 */
	public Uid(long value) {
		this.value = value;

		if (!UidConverter.checkType(getTypeValue())) {
			throw new MetalconRuntimeException(
					"Muid Type may not be larger or equal to " + (1 << 9));
		}
	}

	/**
	 * create a new Muid instance with an already given value in alphanumeric
	 * form
	 * 
	 * @param alphaNumericValue
	 *            unique identifier in base64 format
	 */
	public Uid(String alphaNumericValue) {
		this(UidConverter.deserialize(alphaNumericValue));
	}

	/**
	 * @return unique identifier
	 */
	public long getValue() {
		return value;
	}

	/**
	 * 
	 * @return
	 */
	public short getTypeValue() {
		return UidConverter.getType(value);
	}

	/**
	 * @return type of the MuidType enum represented by this MUID's type
	 */
	public UidType getMuidType() throws UnknownMuidException {
		return UidType.parseShort(getTypeValue());
	}

	/**
	 * Returns the source that generated the given MUID
	 * 
	 * @param muid
	 *            The MUID storing the source searched for
	 * @return The source that created the given MUID
	 */
	public byte getSource() {
		return UidConverter.getSource(value);
	}

	/**
	 * Returns the timestamp the given MUID has been created
	 * 
	 * @param muid
	 *            The MUID storing the timestamp searched for
	 * @return The timestamp the given MUID has been created at
	 */
	public int getTimestamp() {
		if (getTypeValue() == UidType.URL.getRawIdentifier()) {

		}

		return UidConverter.getTimestamp(value);
	}

	@Override
	public String toString() {
		return UidConverter.serialize(value);
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
		return UidConverter.getMUIDStoragePath(value);
	}

	/**
	 * Returns all characters a MUID path may consist of
	 * 
	 * @return All allowed characters
	 */
	public static char[] getAllowedFolderNames() {
		return UidConverter.getAllowedFolderNames();
	}
}
