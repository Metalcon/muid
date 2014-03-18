package de.metalcon.domain;

import java.io.Serializable;

/**
 * unique identifier for a Metalcon entity knowing the entity's type (Metalcon
 * Unique IDentifier)
 */
public class Muid implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final Muid EMPTY_MUID = new Muid(0);

	/**
	 * unique identifier
	 */
	private final long value;

	/**
	 * create new Metalcon identifier
	 * 
	 * @param value
	 *            unique identifier
	 */
	public Muid(long value) {
		this.value = value;
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
		return ((Long) value).toString();
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
