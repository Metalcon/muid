package de.metalcon.domain;

import java.util.TreeMap;

/**
 * enumeration of all muid types<br>
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
public enum MuidType {
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
	static final MuidType[] allTypes = { BAND, CITY, EVENT, GENRE, INSTRUMENT,
			RECORD, TOUR, TRACK, USER, VENUE };

	/**
	 * Map to map identy type names (strings) to enums
	 */
	static TreeMap<String, MuidType> allTypesByString = new TreeMap<String, MuidType>();

	/**
	 * Fill allTypesByString
	 */
	static {
		for (MuidType type : MuidType.values()) {
			allTypesByString.put(type.getIdentifier(), type);
		}
	}

	/**
	 * identifier of the Muid type
	 */
	private final String identifier;

	/**
	 * Raw identifier of the Muid type
	 */
	private final short rawValue;

	/**
	 * create a new Muid type
	 * 
	 * @param identifier
	 *            identifier of the Muid type
	 */
	private MuidType(final String identifier, final short rawValue) {
		this.identifier = identifier;
		this.rawValue = rawValue;
	}

	/**
	 * @return identifier of the Muid type
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @return short value identifying the Muid type
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
	 *            identifier of the Muid type
	 * @return Muid type having the identifier passed<br>
	 *         <b>null</b> if no Muid type has such identifier
	 */
	public static MuidType parseString(final String identifier) {
		return (allTypesByString.get(identifier));
	}

	/**
	 * 
	 * @param identifier
	 *            the number identifying the type.
	 * @see MuidType.getMaximumAllowedType()
	 * @return the enum identifying the Muid type corresponding to the given
	 *         identifier
	 * @throws UnknownMuidException
	 *             thrown if the given identifier is invalid
	 */
	public static MuidType parseShort(final short identifier)
			throws UnknownMuidException {
		if (identifier > allTypes.length) {
			throw new UnknownMuidException(identifier);
		}
		return allTypes[identifier];
	}

	/**
	 * 
	 * @return the largest muid type value allowed
	 */
	public static short getLargestAllowedType() {
		return (short) MuidType.values().length;
	}
}
