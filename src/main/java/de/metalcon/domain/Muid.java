package de.metalcon.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import de.metalcon.domain.helper.UidConverter;
import de.metalcon.exceptions.MetalconRuntimeException;

/**
 * unique identifier for a Metalcon Metalcon Unique IDentifier. Additional to
 * type and sourceID (@see {@link Uid}) it stores a timestamp (time of creation)
 * and a fine time (number of created Uid within the timestamp second)
 */
public class Muid extends Uid implements Serializable {
	private static final long serialVersionUID = 6474090689412027428L;

	private static int lastCreationTime = 0;

	public static final Muid EMPTY_BAND_MUID = new Muid(
			UidConverter.calculateMuid(UidType.BAND.getRawIdentifier(),
					(byte) 0, 0, (short) 0));
	public static final Muid EMPTY_CITY_MUID = new Muid(
			UidConverter.calculateMuid(UidType.CITY.getRawIdentifier(),
					(byte) 0, 0, (short) 0));
	public static final Muid EMPTY_EVENT_MUID = new Muid(
			UidConverter.calculateMuid(UidType.EVENT.getRawIdentifier(),
					(byte) 0, 0, (short) 0));
	public static final Muid EMPTY_GENRE_MUID = new Muid(
			UidConverter.calculateMuid(UidType.GENRE.getRawIdentifier(),
					(byte) 0, 0, (short) 0));
	public static final Muid EMPTY_INSTRUMENT_MUID = new Muid(
			UidConverter.calculateMuid(UidType.INSTRUMENT.getRawIdentifier(),
					(byte) 0, 0, (short) 0));
	public static final Muid EMPTY_RECORD_MUID = new Muid(
			UidConverter.calculateMuid(UidType.RECORD.getRawIdentifier(),
					(byte) 0, 0, (short) 0));
	public static final Muid EMPTY_TOUR_MUID = new Muid(
			UidConverter.calculateMuid(UidType.TOUR.getRawIdentifier(),
					(byte) 0, 0, (short) 0));
	public static final Muid EMPTY_TRACK_MUID = new Muid(
			UidConverter.calculateMuid(UidType.TRACK.getRawIdentifier(),
					(byte) 0, 0, (short) 0));
	public static final Muid EMPTY_USER_MUID = new Muid(
			UidConverter.calculateMuid(UidType.USER.getRawIdentifier(),
					(byte) 0, 0, (short) 0));
	public static final Muid EMPTY_VENUE_MUID = new Muid(
			UidConverter.calculateMuid(UidType.VENUE.getRawIdentifier(),
					(byte) 0, 0, (short) 0));

	private static Map<UidType, Muid> emptyMuids;
	static {
		emptyMuids = new TreeMap<UidType, Muid>();
		emptyMuids.put(UidType.BAND, EMPTY_BAND_MUID);
		emptyMuids.put(UidType.CITY, EMPTY_CITY_MUID);
		emptyMuids.put(UidType.EVENT, EMPTY_EVENT_MUID);
		emptyMuids.put(UidType.GENRE, EMPTY_GENRE_MUID);
		emptyMuids.put(UidType.INSTRUMENT, EMPTY_INSTRUMENT_MUID);
		emptyMuids.put(UidType.RECORD, EMPTY_RECORD_MUID);
		emptyMuids.put(UidType.TOUR, EMPTY_TOUR_MUID);
		emptyMuids.put(UidType.TRACK, EMPTY_TRACK_MUID);
		emptyMuids.put(UidType.USER, EMPTY_USER_MUID);
		emptyMuids.put(UidType.VENUE, EMPTY_VENUE_MUID);
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
	public static Muid create(final long id) {
		if (UidConverter.getTimestamp(id) == 0) {
			return emptyMuids.get(UidType.parseId(id));
		}
		return new Muid(id);
	}

	/**
	 * Creates a new Muid object of the given type
	 * 
	 * If a Muid with the same type has already been created during the current
	 * second the ID value will be incremented
	 * 
	 * @param type
	 *            the type of the Muid to be created
	 * @returna new unique Muid object
	 */
	public static Muid create(final UidType type) {
		if (type == UidType.URL) {
			throw new MetalconRuntimeException(
					"Do not try to create a Muid with the URL type! Use UrlID.create instead.");
		}

		int timestamp = (int) (System.currentTimeMillis() / 1000);
		short ID = 0;
		if (timestamp == lastCreationTime) {
			// We have already created a MUID during the current second
			ID = (short) lastCreatedID.incrementAndGet();
		} else {
			lastCreatedID.set(0);
		}
		lastCreationTime = timestamp;

		return new Muid(UidConverter.calculateMuid(type.getRawIdentifier(),
				sourceID, timestamp, ID));
	}

	/**
	 * create new Muid instance with an already given value
	 * 
	 * @param value
	 *            unique identifier
	 */
	public Muid(long value) {
		super(value);
	}

	/**
	 * create a new Muid instance with an already given value in alphanumeric
	 * form
	 * 
	 * @param alphaNumericValue
	 *            unique identifier in base64 format
	 */
	public Muid(String alphaNumericValue) {
		super(alphaNumericValue);
	}

	/**
	 * Returns the timestamp this Muid has been created
	 * 
	 * @return The timestamp this Muid has been created
	 */
	public int getTimestamp() {
		if (getTypeValue() == UidType.URL.getRawIdentifier()) {

		}

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
}
