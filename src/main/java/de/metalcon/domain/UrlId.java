package de.metalcon.domain;

import java.net.MalformedURLException;
import java.net.URL;

class HostAndFile {
	public String host;
	public String file;
}

/**
 * unique identifier for a Metalcon Metalcon Unique IDentifier
 */
public class UrlId extends Uid {

	private static final long serialVersionUID = -1281694057384091786L;

	/**
	 * creates a new Muid object with a unique ID
	 * 
	 * If a Muid with the same type has already been created during the current
	 * second the ID value will be incremented
	 * 
	 * @param type
	 *            the type of the Muid to be created
	 * @throws MalformedURLException
	 * @returna new unique Muid object
	 */
	public static Muid create(final String url) throws MalformedURLException {
		HostAndFile haf = getHostAndFileFromURL(url);

		// return new Muid(UidConverter.calculateMuid(
		// UidType.URL.getRawIdentifier(), sourceID, timestamp, ID));
		return null;
	}

	/**
	 * 
	 * @param url
	 * @param host
	 * @param file
	 * @throws MalformedURLException
	 */
	public static HostAndFile getHostAndFileFromURL(final String url)
			throws MalformedURLException {
		URL uri = new URL(url);

		HostAndFile haf = new HostAndFile();
		haf.host = uri.getHost();
		if (uri.getRef() != null) {
			haf.file = uri.getFile() + "#" + uri.getRef();
		} else {
			haf.file = uri.getFile();
		}
		return haf;
	}

	/**
	 * create new Muid instance with an already given value
	 * 
	 * @param value
	 *            unique identifier
	 */
	public UrlId(long value) {
		super(value);
	}

	/**
	 * create a new Euid instance with an already given value in alphanumeric
	 * form
	 * 
	 * @param alphaNumericValue
	 *            unique identifier in base64 format
	 */
	public UrlId(String alphaNumericValue) {
		super(alphaNumericValue);
	}
}
