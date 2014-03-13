package de.metalcon.domain.entities;

import org.json.simple.parser.ParseException;
import org.junit.Before;

import de.metalcon.domain.EntityUrlObjectTest;
import de.metalcon.domain.Muid;

public class BandUrlObjectTest extends EntityUrlObjectTest {

    protected static final Muid VALID_BAND_MUID = new Muid(2);

    private BandUrlObject band;

    @Override
    @Before
    public void setUp() throws ParseException {
        band = new BandUrlObject(VALID_BAND_MUID, VALID_NAME);
        SOURCE_ENTITY = band;
        super.setUp();
    }

}
