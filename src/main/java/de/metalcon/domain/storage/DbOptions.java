package de.metalcon.domain.storage;

import java.lang.invoke.MethodHandles;

import de.metalcon.dbhelper.Options;

public class DbOptions extends Options {
	static {
		Options.initialize(MethodHandles.lookup().lookupClass());
	}

	public static String URL_DB_PATH;
}
