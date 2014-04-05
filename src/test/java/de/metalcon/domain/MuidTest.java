package de.metalcon.domain;

import junit.framework.Assert;

import org.junit.Test;

import de.metalcon.domain.helper.UidConverter;
import de.metalcon.exceptions.MetalconRuntimeException;

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
	}
}
