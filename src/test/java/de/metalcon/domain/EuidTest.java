package de.metalcon.domain;

import java.net.MalformedURLException;

import org.junit.Assert;
import org.junit.Test;

import de.metalcon.domain.options.DbOptions;

public class EuidTest {

	@Test
	public void getHostAndFileFromURLTest() throws MalformedURLException {
		System.out.println(DbOptions.path);

		String url = "http://asdf.metalcon.de/1/2?df=3#";

		HostAndFile haf = UrlId.getHostAndFileFromURL(url);

		Assert.assertEquals(haf.host, "asdf.metalcon.de");
		Assert.assertEquals(haf.file, "/1/2?df=3#");
	}
}
