package de.metalcon.domain.storage;

import java.net.MalformedURLException;
import java.net.URL;

import de.metalcon.dbhelper.LevelDbHandler;
import de.metalcon.domain.helper.LongAndBoolean;
import de.metalcon.domain.helper.UidConverter;
import de.metalcon.exceptions.MetalconException;

public class UrlStore {
	private static LevelDbHandler db = null;

	/**
	 * @param dbDir
	 *            Path to the directory where the Url mappings should be stored.
	 *            If it doesn't exist, the directory will be created.
	 * @throws MetalconException
	 */
	static {
		try {
			LevelDbHandler.initialize(DbOptions.URL_DB_PATH);
			db = new LevelDbHandler("URLStore");
		} catch (MetalconException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Generates a new UrlID or returns the corresponding one if it already
	 * exists. The boolean in the return value tells you if the url was already
	 * stored.
	 * 
	 * @param url
	 * @throws MalformedURLException
	 */
	public static synchronized long getOrCreateID(final String url)
			throws MalformedURLException {
		LongAndBoolean lab = getOrCalculateID(url);
		if (!lab.b) {
			storeURL(lab.l, url);
		}
		return lab.l;
	}

	/**
	 * Seeks the UrlID associated to the given url or returns a new free one
	 * 
	 * This should be called synchronized if the result should be used to create
	 * a new UrlID
	 * 
	 * @param url
	 *            The url to be searched for
	 * @throws MalformedURLException
	 */
	private static LongAndBoolean getOrCalculateID(final String url)
			throws MalformedURLException {

		URL uri = new URL(url);
		/*
		 * We only need the domain without any subdomain
		 */
		String domain = uri.getHost();
		String[] split = domain.split("\\.");
		if (split.length > 2) {
			domain = split[split.length - 2] + "." + split[split.length - 1];
		}

		/*
		 * Check if the url is already stored by generating the ID and checking
		 * if the URL is already stored associated to the ID as key
		 */

		short domainID = (short) domain.hashCode();
		int fileID = url.hashCode();

		LongAndBoolean lab = new LongAndBoolean();
		while (true) {
			lab.l = UidConverter
					.calculateUrlIdWithoutChecking(domainID, fileID);
			// Is the url already stored?
			String storedUrl = getUrl(lab.l);
			if (storedUrl != null) {
				// and is it really the same url and not a subdomain?
				if (storedUrl.equals(url)) {
					lab.b = true;
					return lab;
				} else {
					fileID++;
					// now try again
				}
			} else {
				/*
				 * If the domain is new to us we can store the URL as is. If not
				 * urlID is now a new unique ID so let's store it
				 */
				lab.b = false;
				return lab;
			}
		}
	}

	/**
	 * Returns the UrlID associated to the given url or 0 if the url is not yet
	 * stored
	 * 
	 * 
	 * @param url
	 *            The url to be searched for
	 * @return the UrlID associated to the given url or 0 if the url is not yet
	 *         stored
	 * @throws MalformedURLException
	 */
	public static long getStoredUrlID(final String url)
			throws MalformedURLException {
		LongAndBoolean lab = getOrCalculateID(url);
		if (!lab.b) {
			return 0;
		}
		return lab.l;
	}

	/**
	 * Returns the complete URL stored associated with the given UrlID
	 * 
	 * @param UrlID
	 *            The complete URL associated with the given UrlID or null if
	 *            the UrlID does not yet exist
	 * @return
	 */
	public static String getUrl(final long urlID) {
		return db.getString(urlID);
	}

	/**
	 * Returns the complete URL stored associated with the given UrlID
	 * 
	 * @param UrlID
	 *            The complete URL associated with the given UrlID or null if
	 *            the UrlID does not yet exist
	 * @return
	 */
	private static void storeURL(final long urlID, final String url) {
		db.put(urlID, url);
	}

	/**
	 * Checks if the given domain is already stored in the DB
	 * 
	 * @param domainID
	 *            The domain to be checked
	 */
	public static void storeDomain(final short domainID) {
		db.put(domainID, true);
	}
}
