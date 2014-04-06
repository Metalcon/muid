package de.metalcon.domain;

import java.net.MalformedURLException;

import de.metalcon.domain.helper.UidConverter;
import de.metalcon.domain.storage.UrlStore;

/**
 * unique identifier for a Metalcon Metalcon Unique IDentifier
 */
public class UrlId extends Uid {

	private static final long serialVersionUID = -1281694057384091786L;

	/**
	 * Creates a new Muid object with the given unique ID
	 * 
	 * @param id
	 *            the unique ID of the new UrlID
	 * @returna New unique Muid object
	 */
	public static UrlId create(final long id) {
		return new UrlId(id);
	}

	/**
	 * Creates a new Muid object with a unique ID storing the given URL
	 * 
	 * If a Muid with the same type has already been created during the current
	 * second the ID value will be incremented
	 * 
	 * @param url
	 *            The URL to be stored. It must be of the full URL including the
	 *            protocol/scheme
	 * @throws MalformedURLException
	 * @returna new unique Muid object
	 */
	public static UrlId create(final String url) throws MalformedURLException {
		return new UrlId(UrlStore.getOrCreateID(url));
	}

	/**
	 * Create new Muid instance with an already given value
	 * 
	 * @param value
	 *            unique identifier
	 */
	public UrlId(long value) {
		super(value);
	}

	/**
	 * Create a new Euid instance with an already given value in alphanumeric
	 * form
	 * 
	 * @param alphaNumericValue
	 *            unique identifier in base64 format
	 */
	public UrlId(String alphaNumericValue) {
		super(alphaNumericValue);
	}

	/**
	 * @return The full URL string
	 */
	public String getUrl() {
		return UrlStore.getUrl(getValue());
	}

	/**
	 * @return The ID identifying the domain of this URL
	 */
	public short getDomainId() {
		return UidConverter.getDomainID(getValue());
	}

	/**
	 * @return The ID identifying the file of all files within the domain of
	 *         this URL
	 */
	public int getFileId() {
		return UidConverter.getFileID(getValue());
	}
}
