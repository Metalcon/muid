package de.metalcon.domain;

import junit.framework.Assert;

import org.junit.Test;

import de.metalcon.exceptions.MetalconRuntimeException;
import de.metalcon.exceptions.ServiceOverloadedException;

public class MuidTest {

	@Test
	public void constructorTest() {

		try {
			/*
			 * Test if we receive an exception if the Muid type does not exists
			 */
			short badType = (short) (MuidType.values().length + 1);
			long id = MuidConverter.calculateMuidWithoutChecking(badType,
					(byte) 0, 0, (short) 0);

			new Muid(id);
			Assert.fail("Muid did not throw an Exception while instanciating with a bad type value");
		} catch (MetalconRuntimeException e) {
		}

		/*
		 * Test if overloading of the Muid service is handled correctly
		 */
		try {
			for (int i = 0; i < MuidConverter.getMaximumMuidID(); i++) {
				Muid.create(MuidType.BAND);
			}
		} catch (ServiceOverloadedException e) {
			Assert.fail("Creating 0xFFFF MUIDs per second should not throw an exception");
		}

		try {
			for (int i = 0; i < 3 * MuidConverter.getMaximumMuidID(); i++) {
				Muid.create(MuidType.BAND);
			}
			Assert.fail("Creating more then 0xFFFF MUIDs per second should throw an exception");
		} catch (ServiceOverloadedException e) {
		}
	}
}
