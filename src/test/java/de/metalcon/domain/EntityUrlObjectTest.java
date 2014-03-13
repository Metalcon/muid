package de.metalcon.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

public abstract class EntityUrlObjectTest {

    private static final JSONParser PARSER = new JSONParser();

    protected static final String VALID_NAME = "myEntity";

    protected EntityUrlObject SOURCE_ENTITY;

    protected JSONObject serializedObject;

    protected EntityUrlObject entity;

    @Before
    public void setUp() throws ParseException {
        serializedObject = (JSONObject) PARSER.parse(SOURCE_ENTITY.serialize());
    }

    protected void deserialize(final boolean success) {
        try {
            entity = EntityUrlObject.dezerializeEntity(serializedObject);
        } catch (final IllegalArgumentException e) {
            if (success) {
                fail("failed to deserialize unexcpectedly");
            } else {
                return;
            }
        }

        if (!success) {
            fail("deserialization worked unexcpectedly");
        }
    }

    @SuppressWarnings("unchecked")
    protected void setField(final String key, final Object value) {
        serializedObject.put(key, value);
    }

    @SuppressWarnings("unchecked")
    private static JSONObject createMuidObject(final Object value) {
        final JSONObject object = new JSONObject();
        object.put(ZeroMQSerialization.Muid.VALUE, value);
        return object;
    }

    @Test
    public void testMuidValid() {
        deserialize(true);
        assertEquals(SOURCE_ENTITY.getMuid().getValue(), entity.getMuid()
                .getValue(), 0);
    }

    @Test
    public void testMuidMissing() {
        setField(ZeroMQSerialization.MUID, null);
        deserialize(false);
    }

    @Test
    public void testMuidValueMissing() {
        setField(ZeroMQSerialization.MUID, createMuidObject(null));
        deserialize(false);
    }

    @Test
    public void testNameValid() {
        deserialize(true);
        assertEquals(SOURCE_ENTITY.getName(), entity.getName());
    }

    @Test
    public void testNameMissing() {
        setField(ZeroMQSerialization.NAME, null);
        deserialize(false);
    }

}
