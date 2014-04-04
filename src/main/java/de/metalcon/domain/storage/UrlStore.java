package de.metalcon.domain.storage;

import static org.fusesource.leveldbjni.JniDBFactory.bytes;
import static org.fusesource.leveldbjni.JniDBFactory.factory;

import java.io.File;
import java.io.IOException;

import org.iq80.leveldb.DB;

import de.metalcon.exceptions.MetalconRuntimeException;

public class UrlStore {
	private DB db;

	private final byte[] LARGEST_DOMAIN_KEY = bytes("");

	/**
	 * 
	 * @param dbDir
	 *            Path to the directory where the Url mappings should be stored.
	 *            If it doesn't exist, the directory will be created.
	 */
	public UrlStore(final String dbDir) {
		File f = new File(dbDir);

		if (!f.exists()) {
			if (!f.mkdirs()) {
				throw new MetalconRuntimeException(
						"Unable to create directory " + dbDir);
			}
		}

		try {
			org.iq80.leveldb.Options options = new org.iq80.leveldb.Options();
			options.createIfMissing(true);

			// options.logger(new Logger() {
			// public void log(String message) {
			// System.out.println(message);
			// }
			// });
			db = factory.open(new File(dbDir), options);
		} catch (IOException e) {
			System.err.println("Unable to instanciate levelDB on " + dbDir
					+ ": " + e.getMessage());
			System.exit(1);
		}
	}

	@Override
	public void finalize() {
		try {
			db.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getLargestDomainID() {

	}
}
