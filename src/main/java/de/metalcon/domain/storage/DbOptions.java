package de.metalcon.domain.storage;

import java.lang.invoke.MethodHandles;

import de.metalcon.dbhelper.Options;

public class DbOptions extends Options {

    static {
        try {
            Options.initialize("/usr/share/metalcon/uid/config.txt",
                    MethodHandles.lookup().lookupClass());
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static String URL_DB_PATH;
}
