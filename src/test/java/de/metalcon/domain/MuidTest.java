package de.metalcon.domain;

import junit.framework.Assert;

import org.junit.Test;

import de.metalcon.domain.helper.UidConverter;
import de.metalcon.exceptions.MetalconRuntimeException;
import de.metalcon.exceptions.ServiceOverloadedException;

public class MuidTest {

	@Test
	public void constructorTest() {

		try {
			/*
			 * Test if we receive an exception if the Muid type does not exists
			 */
			short badType = (short) (UidType.values().length + 1);
			long id = UidConverter.calculateMuidWithoutChecking(badType,
					(byte) 0, 0, (short) 0);

			new Muid(id);
			Assert.fail("Muid did not throw an Exception while instanciating with a bad type value");
		} catch (MetalconRuntimeException e) {
		}

		Muid id = Muid.create(UidConverter.calculateMuidWithoutChecking(
				UidType.RECORD.getRawIdentifier(), (byte) 0, 0, (short) 0));
		Assert.assertEquals(id, Muid.EMPTY_RECORD_MUID);

		/*
		 * Test if overloading of the Muid service is handled correctly
		 */
		try {
			for (int i = 0; i < UidConverter.getMaximumMuidID(); i++) {
				Muid.create(UidType.BAND);
			}
		} catch (ServiceOverloadedException e) {
			Assert.fail("Creating 0xFFFF MUIDs per second should not throw an exception");
		}

		try {
			for (int i = 0; i < 3 * UidConverter.getMaximumMuidID(); i++) {
				Muid.create(UidType.BAND);
			}
			Assert.fail("Creating more then 0xFFFF MUIDs per second should throw an exception");
		} catch (ServiceOverloadedException e) {
		}
	}
}
