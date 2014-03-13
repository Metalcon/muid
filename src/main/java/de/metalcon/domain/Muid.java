package de.metalcon.domain;


/**
 * unique identifier for a Metalcon entity knowing the entity's type (Metalcon
 * Unique IDentifier)
 */
public class Muid {

    public static final Muid EMPTY_MUID = new Muid(0);

    /**
     * unique identifier
     */
    private long value;

    /**
     * type of the entity represented by this MUID
     */
    private EntityType entityType;

    /**
     * create new Metalcon identifier
     * 
     * @param value
     *            unique identifier
     */
    public Muid(
            long value) {
        this.value = value;
        entityType = calcEntityType();
    }

    /**
     * @return unique identifier
     */
    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    /**
     * calculate the entity type via identifier
     * 
     * @return type of the entity
     */
    private EntityType calcEntityType() {
        long rem = value % 10;
        if (rem == 1) {
            return EntityType.USER;
        } else if (rem == 2) {
            return EntityType.BAND;
        } else if (rem == 3) {
            return EntityType.RECORD;
        } else if (rem == 4) {
            return EntityType.TRACK;
        } else if (rem == 5) {
            return EntityType.VENUE;
        } else if (rem == 6) {
            return EntityType.EVENT;
        } else if (rem == 7) {
            return EntityType.CITY;
        } else if (rem == 8) {
            return EntityType.GENRE;
        } else if (rem == 9) {
            return EntityType.INSTRUMENT;
        } else if (rem == 0) {
            return EntityType.TOUR;
        }

        throw new UnsupportedOperationException(
                "Muid.getEntityType() not implemented yet.");
    }

    /**
     * @return type of the entity represented by this MUID
     */
    public EntityType getEntityType() {
        return entityType;
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
