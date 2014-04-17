package de.metalcon.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import de.metalcon.domain.helper.UidConverter;
import de.metalcon.exceptions.MetalconRuntimeException;
import de.metalcon.exceptions.ServiceOverloadedException;

/**
 * unique identifier for a Metalcon Metalcon Unique IDentifier. Additional to
 * type and sourceID (@see {@link Uid}) it stores a timestamp (time of creation)
 * and a fine time (number of created Uid within the timestamp second)
 */
public class Muid extends Uid implements Serializable {

    private static final long serialVersionUID = 6474090689412027428L;

    private static int lastCreationTime = 0;

    private static Map<UidType, Muid> emptyMuids = new TreeMap<UidType, Muid>();

    public static final Muid EMPTY_BAND_MUID = addEmptyMuid(UidType.BAND);

    public static final Muid EMPTY_CITY_MUID = addEmptyMuid(UidType.CITY);

    public static final Muid EMPTY_DISC_MUID = addEmptyMuid(UidType.DISC);

    public static final Muid EMPTY_EVENT_MUID = addEmptyMuid(UidType.EVENT);

    public static final Muid EMPTY_GALLERY_MUID = addEmptyMuid(UidType.GALLERY);

    public static final Muid EMPTY_GENRE_MUID = addEmptyMuid(UidType.GENRE);

    public static final Muid EMPTY_IMAGE_MUID = addEmptyMuid(UidType.IMAGE);

    public static final Muid EMPTY_INSTRUMENT_MUID =
            addEmptyMuid(UidType.INSTRUMENT);

    public static final Muid EMPTY_RECORD_MUID = addEmptyMuid(UidType.RECORD);

    public static final Muid EMPTY_TOUR_MUID = addEmptyMuid(UidType.TOUR);

    public static final Muid EMPTY_TRACK_MUID = addEmptyMuid(UidType.TRACK);

    public static final Muid EMPTY_USER_MUID = addEmptyMuid(UidType.USER);

    public static final Muid EMPTY_VENUE_MUID = addEmptyMuid(UidType.VENUE);

    private static Muid addEmptyMuid(UidType type) {
        Muid emptyMuid =
                new Muid(UidConverter.calculateMuid(type.getRawIdentifier(),
                        (byte) 0, 0, (short) 0));
        emptyMuids.put(type, emptyMuid);
        return emptyMuid;
    }

    /**
     * The number of MUIDs created at <lastCreationTime>
     */
    private static AtomicInteger lastCreatedID = new AtomicInteger(0);

    /**
     * 
     * @param id
     * @return
     */
    public static Muid createFromID(final long id) {
        if (UidConverter.getTimestamp(id) == 0) {
            Muid muid = emptyMuids.get(UidType.parseId(id));
            if (muid.value != id) {
                throw new MetalconRuntimeException(
                        "You've tried to create a Muid with a 0-timestamp but with source or finetime not being 0. Only empty Muids may have 0 timestamps and should only have the type being not 0.");
            }
            return muid;
        }
        return new Muid(id);
    }

    /**
     * create a new Muid instance with an already given value in alphanumeric
     * form
     * 
     * @param alphaNumericValue
     *            unique identifier in base64 format
     */
    public static Muid createFromID(final String alphaNumericValue) {
        return createFromID(UidConverter.deserialize(alphaNumericValue));
    }

    /**
     * Returns the empty Muid related to the given type or null it the type is
     * not valid
     * 
     * @param type
     *            The type of the Muid to be returned
     * @return An empty Muid with the given type
     */
    public static Muid getEmptyType(final UidType type) {
        return emptyMuids.get(type);
    }

    /**
     * Creates a new Muid object of the given type
     * 
     * If a Muid with the same type has already been created during the current
     * second the ID value will be incremented
     * 
     * @param type
     *            The type of the Muid to be created
     * @throws ServiceOverloadedException
     *             Tto reduce the load you should not try to create a MUID again
     *             if this exception has been thrown!
     * @return A new unique Muid object
     */
    public static Muid create(final UidType type)
            throws ServiceOverloadedException {
        if (type == UidType.URL) {
            throw new MetalconRuntimeException(
                    "Tried to create a Muid object with a URL type. Please use UrlID instead.");
        }

        int timestamp = (int) (System.currentTimeMillis() / 1000);
        short ID = 0;
        if (timestamp == lastCreationTime) {
            // We have already created a MUID during the current second
            if (lastCreatedID.intValue() == UidConverter.getMaximumMuidID()) {
                // We've reached the Muid per second limit
                throw new ServiceOverloadedException(
                        "Alreay created more then "
                                + UidConverter.getMaximumMuidID()
                                + " during the current second");
            }

            ID = (short) lastCreatedID.incrementAndGet();
        } else {
            lastCreatedID.set(0);
            lastCreationTime = timestamp;
        }

        return new Muid(UidConverter.calculateMuid(type.getRawIdentifier(),
                sourceID, timestamp, ID));
    }

    /**
     * create new Muid instance with an already given value
     * 
     * @param value
     *            unique identifier
     */
    private Muid(
            final long value) {
        super(value);
    }

    /**
     * Returns the timestamp this Muid has been created
     * 
     * @return The timestamp this Muid has been created
     */
    public int getTimestamp() {
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

    /**
     * Is this object an empty Muid?
     * 
     * @return true if the timestamp is 0 which is only the case if this object
     *         is an empty muid
     */
    public boolean isEmpty() {
        return getTimestamp() == 0;
    }
}
