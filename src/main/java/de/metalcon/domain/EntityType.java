package de.metalcon.domain;

import java.util.TreeMap;

/**
 * enumeration of all entity types<br>
 * (Metalcon objects having an own page)<br>
 * <br>
 * <ul>
 * <li>band</li>
 * <li>city</li>
 * <li>event</li>
 * <li>genre</li>
 * <li>instrument</li>
 * <li>record</li>
 * <li>tour</li>
 * <li>track</li>
 * <li>user</li>
 * <li>venue</li>
 * </ul>
 */
public enum EntityType {
	/**
	 * band linked to a genre
	 */
	BAND("band", (short) 0),

	/**
	 * city
	 */
	CITY("city", (short) 1),

	/**
	 * event hosted in a venue
	 */
	EVENT("event", (short) 2),

	/**
	 * metal genre
	 */
	GENRE("genre", (short) 3),

	/**
	 * music instrument
	 */
	INSTRUMENT("instrument", (short) 4),

	/**
	 * record of a band
	 */
	RECORD("record", (short) 5),

	/**
	 * collection of events
	 */
	TOUR("tour", (short) 6),

	/**
	 * record track
	 */
	TRACK("track", (short) 7),

	/**
	 * user
	 */
	USER("user", (short) 8),

	/**
	 * venue located in a city
	 */
	VENUE("venue", (short) 9);

	/**
	 * Array to map raw identifier IDs to enums
	 */
	static final EntityType[] allTypes = { BAND, CITY, EVENT, GENRE,
			INSTRUMENT, RECORD, TOUR, TRACK, USER, VENUE };

	/**
	 * Map to map identy type names (strings) to enums
	 */
	static TreeMap<String, EntityType> allTypesByString = new TreeMap<String, EntityType>();

	/**
	 * Fill allTypesByString
	 */
	static {
		for (EntityType type : EntityType.values()) {
			allTypesByString.put(type.getIdentifier(), type);
		}
	}

	/**
	 * identifier of the entity type
	 */
	private final String identifier;

	/**
	 * Raw identifier of the entity type
	 */
	private final short rawValue;

	/**
	 * create a new entity type
	 * 
	 * @param identifier
	 *            identifier of the entity type
	 */
	private EntityType(final String identifier, final short rawValue) {
		this.identifier = identifier;
		this.rawValue = rawValue;
	}

	/**
	 * @return identifier of the entity type
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @return short value identifying the entity type
	 */
	public short getRawIdentifier() {
		return rawValue;
	}

	@Override
	public String toString() {
		return identifier;
	}

	/**
	 * 
	 * @param identifier
	 *            identifier of the entity type
	 * @return entity type having the identifier passed<br>
	 *         <b>null</b> if no entity type has such identifier
	 */
	public static EntityType parseString(final String identifier) {
		return (allTypesByString.get(identifier));
	}

	public static EntityType parseShort(final short identifier)
			throws UnknownEntityIdentifierException {
		if (identifier > allTypes.length) {
			throw new UnknownEntityIdentifierException(identifier);
		}
		return allTypes[identifier];
	}
}
