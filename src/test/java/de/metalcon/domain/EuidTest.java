package de.metalcon.domain;

import java.io.File;
import java.net.MalformedURLException;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import de.metalcon.domain.storage.DbOptions;
import de.metalcon.domain.storage.UrlStore;

public class EuidTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		/*
		 * Change the config to us some random tmp directory for LevelDB
		 */
		File f = File.createTempFile("UidTest", null, new File("/tmp"));
		String filename = f.getAbsolutePath();
		f.delete();

		System.out.println(DbOptions.URL_DB_PATH);
		DbOptions.URL_DB_PATH = filename;
		System.out.println(DbOptions.URL_DB_PATH);

	}

	@Test
	public void getHostAndFileFromURLTest() throws MalformedURLException {
		UrlId id = UrlId.create("http://www.metalcon.de/a");

		UrlId id2 = UrlId.create("http://www.metalcon.de/a");
		Assert.assertTrue(id.equals(id2));

		id2 = UrlId.create("http://metalcon.de/a");
		Assert.assertFalse(id.equals(id2));

		id2 = UrlId.create("http://metalcon.de/b");
		Assert.assertFalse(id.equals(id2));

		long start = System.currentTimeMillis();
		for (int i = 0; i != 1E5; i++) {
			String path = "http://" + i;
			Assert.assertTrue(UrlStore.getStoredUrlID(path) == 0);
			id2 = UrlId.create(path);
			Assert.assertTrue(UrlStore.getStoredUrlID(path) != 0);
			Assert.assertEquals(id2.getUrl(), path);
		}
		long time = System.currentTimeMillis() - start;
		System.out.println(time / 1000.);
	}
}
