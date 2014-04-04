package de.metalcon.domain.options;

import java.lang.invoke.MethodHandles;

public class DbOptions extends Options {
	static {
		Options.initialize(MethodHandles.lookup().lookupClass());
	}

	public static String path;
}
