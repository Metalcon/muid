package de.metalcon.domain;

import java.util.TreeMap;

import de.metalcon.domain.helper.UidConverter;
import de.metalcon.domain.helper.UnknownMuidException;

/**
 * enumeration of all Uid types<br>
 * <br>
 * <ul>
 * <li>band</li>
 * <li>city</li>
 * <li>disc</li>
 * <li>event</li>
 * <li>gallery</li>
 * <li>genre</li>
 * <li>image</li>
 * <li>instrument</li>
 * <li>record</li>
 * <li>tour</li>
 * <li>track</li>
 * <li>user</li>
 * <li>venue</li>
 * <li>url</li>
 * </ul>
 * 
 * WARNING:<br>
 * Although this list is sorted alphabetically you <b>MUST</b> sort the
 * enumerations below by their raw identifier.<br>
 * Add new UID types to the end of the list.
 */
public enum UidType {

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
    VENUE("venue", (short) 9),

    /**
     * external URL
     */
    URL("url", (short) 10),

    /**
     * disc of a record
     */
    DISC("disc", (short) 11),

    /**
     * gallery (manually) created by an entity
     */
    GALLERY("gallery", (short) 12),

    /**
     * image linked to entities
     */
    IMAGE("image", (short) 13);

    /**
     * Map to map identy type names (strings) to enums
     */
    static TreeMap<String, UidType> allTypesByString =
            new TreeMap<String, UidType>();

    /**
     * Fill allTypesByString
     */
    static {
        for (UidType type : UidType.values()) {
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
    private UidType(
            final String identifier,
            final short rawValue) {
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
    public static UidType parseString(final String identifier) {
        return (allTypesByString.get(identifier));
    }

    /**
     * Get a UidType by the type identifying number
     * 
     * @param identifier
     *            the number identifying the type.
     * @see MuidType.getMaximumAllowedType()
     * @return the enum identifying the Muid type corresponding to the given
     *         identifier
     * @throws UnknownMuidException
     *             thrown if the given identifier is invalid
     */
    public static UidType parseShort(final short identifier)
            throws UnknownMuidException {
        if (identifier > UidType.values().length) {
            throw new UnknownMuidException(identifier);
        }
        return UidType.values()[identifier];
    }

    /**
     * Get a UidType by a Muid or UrlID
     * 
     * @param ID
     *            The UID (Muid or UrlID)
     * @return The enum identifying the Muid type corresponding to the given UID
     * @throws UnknownMuidException
     *             Thrown if the given ID is invalid
     */
    public static UidType parseId(final long ID) throws UnknownMuidException {
        short identifier = UidConverter.getType(ID);
        if (identifier > UidType.values().length) {
            throw new UnknownMuidException(identifier);
        }
        return UidType.values()[identifier];
    }

    /**
     * 
     * @return the largest muid type value allowed
     */
    public static short getLargestAllowedType() {
        return (short) UidType.values().length;
    }

}
