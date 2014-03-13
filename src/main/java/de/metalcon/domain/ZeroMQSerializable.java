package de.metalcon.domain;

import org.json.simple.JSONObject;

public abstract class ZeroMQSerializable {

    /**
     * deserialize a JSON field from JSON
     * 
     * @param key
     *            JSON name
     * @param object
     *            serialized object
     * @return JSON value
     */
    protected static Object getField(final String key, final JSONObject object) {
        final Object value = object.get(key);
        if (value != null) {
            return value;
        }

        throw new IllegalArgumentException("serialization field \"" + key
                + "\" is missing");
    }

    abstract public JSONObject toJSON();

    abstract public String serialize();
}
