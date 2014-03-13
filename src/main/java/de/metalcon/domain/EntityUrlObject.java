package de.metalcon.domain;

import org.json.simple.JSONObject;

import de.metalcon.domain.entities.BandUrlObject;

/**
 * basic class for Metalcon entities having an own page
 * 
 * @see EntityType
 */
public abstract class EntityUrlObject extends ZeroMQSerializable {

    /**
     * human readable name
     */
    private final String name;

    /**
     * MUID of the entity
     */
    private final Muid muid;

    /**
     * create basic entity
     * 
     * @param muid
     *            entity identifier
     * @param name
     *            human readable name
     */
    public EntityUrlObject(
            Muid muid,
            String name) {
        this.name = name;
        this.muid = muid;
    }

    /**
     * @return human readable name
     */
    public final String getName() {
        return name;
    }

    /**
     * @return MUID of the entity
     */
    public Muid getMuid() {
        return muid;
    }

    /**
     * @return entity type
     */
    public EntityType getEntityType() {
        return muid.getEntityType();
    }

    @SuppressWarnings("unchecked")
    @Override
    public JSONObject toJSON() {
        final JSONObject muidObject = new JSONObject();
        muidObject.put(ZeroMQSerialization.Muid.VALUE, muid.getValue());

        final JSONObject object = new JSONObject();
        object.put(ZeroMQSerialization.MUID, muidObject);
        object.put(ZeroMQSerialization.NAME, name);
        return object;
    }

    @Override
    public String serialize() {
        return toJSON().toJSONString();
    }

    protected static Muid getMuid(JSONObject entity) {
        final JSONObject muidObject =
                (JSONObject) getField(ZeroMQSerialization.MUID, entity);
        final long value =
                (Long) getField(ZeroMQSerialization.Muid.VALUE, muidObject);

        return new Muid(value);
    }

    protected static String getName(JSONObject entity) {
        return (String) getField(ZeroMQSerialization.NAME, entity);
    }

    public static EntityUrlObject dezerializeEntity(JSONObject entity) {
        final Muid muid = getMuid(entity);

        switch (muid.getEntityType()) {
            case BAND:
                return BandUrlObject.deserialize(entity, muid);

            default:
                throw new UnsupportedOperationException("entity type \""
                        + muid.getEntityType() + "\" is unknown yet");
        }
    }
}
