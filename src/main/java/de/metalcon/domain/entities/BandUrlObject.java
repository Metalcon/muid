package de.metalcon.domain.entities;

import org.json.simple.JSONObject;

import de.metalcon.domain.EntityUrlObject;
import de.metalcon.domain.Muid;

public class BandUrlObject extends EntityUrlObject {

    public BandUrlObject(
            Muid muid,
            String name) {
        super(muid, name);
    }

    public static BandUrlObject deserialize(
            JSONObject band,
            Muid deserializedMuid) {
        final Muid muid =
                (deserializedMuid != null) ? deserializedMuid : getMuid(band);
        final String name = getName(band);

        return new BandUrlObject(muid, name);
    }

}
