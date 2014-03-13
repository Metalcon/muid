package de.metalcon.domain;

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
    BAND("band"),

    /**
     * city
     */
    CITY("city"),

    /**
     * event hosted in a venue
     */
    EVENT("event"),

    /**
     * metal genre
     */
    GENRE("genre"),

    /**
     * music instrument
     */
    INSTRUMENT("instrument"),

    /**
     * record of a band
     */
    RECORD("record"),

    /**
     * collection of events
     */
    TOUR("tour"),

    /**
     * record track
     */
    TRACK("track"),

    /**
     * user
     */
    USER("user"),

    /**
     * venue located in a city
     */
    VENUE("venue");

    /**
     * identifier of the entity type
     */
    private final String identifier;

    /**
     * create a new entity type
     * 
     * @param identifier
     *            identifier of the entity type
     */
    private EntityType(
            final String identifier) {
        this.identifier = identifier;
    }

    /**
     * @return identifier of the entity type
     */
    public String getIdentifier() {
        return identifier;
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
        if (BAND.getIdentifier().equals(identifier)) {
            return BAND;
        } else if (CITY.getIdentifier().equals(identifier)) {
            return CITY;
        } else if (EVENT.getIdentifier().equals(identifier)) {
            return EVENT;
        } else if (GENRE.getIdentifier().equals(identifier)) {
            return GENRE;
        } else if (INSTRUMENT.getIdentifier().equals(identifier)) {
            return INSTRUMENT;
        } else if (RECORD.getIdentifier().equals(identifier)) {
            return RECORD;
        } else if (TOUR.getIdentifier().equals(identifier)) {
            return TOUR;
        } else if (TRACK.getIdentifier().equals(identifier)) {
            return TRACK;
        } else if (USER.getIdentifier().equals(identifier)) {
            return USER;
        } else if (VENUE.getIdentifier().equals(identifier)) {
            return VENUE;
        } else {
            return null;
        }
    }

}
