package de.metalcon.domain;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * unique identifier for a Metalcon entity knowing the entity's type (Metalcon
 * Unique IDentifier)
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

	public static Muid create(final EntityType type) {
		int timestamp = (int) (System.currentTimeMillis() / 1000);
		short ID = 0;
		if (timestamp == lastCreationTime) {
			ID = (short) currentID.incrementAndGet();
		}
		lastCreationTime = timestamp;

		return new Muid(MuidConverter.generateMUID(type, sourceID, timestamp,
				ID));
	}

	/**
	 * create new Muid instance with an already given value
	 * 
	 * @param value
	 *            unique identifier
	 */
	public Muid(long value) {
		this.value = value;
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
	 * @return type of the entity represented by this MUID
	 */
	public EntityType getEntityType() throws UnknownEntityIdentifierException {
		return EntityType.parseShort(MuidConverter.getType(value));
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
}
