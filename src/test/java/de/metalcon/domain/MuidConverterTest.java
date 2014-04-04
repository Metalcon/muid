/**
 * 
 */
package de.metalcon.domain;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import junit.framework.Assert;

import org.junit.Test;

import de.metalcon.exceptions.MetalconRuntimeException;

/**
 * @author kunzejo
 * 
 */
public class MuidConverterTest {

	@Test
	public void typeTest() {
		for (int type = 0; type < 0xFF + 1; type++) {
			long muid = UidConverter.calculateMuidWithoutChecking((short) 0,
					(byte) type, 0, (short) 0);
			System.out.println(muid + "\t" + UidConverter.serialize(muid));
		}
	}

	@Test
	public void test() {
		Random rng = new Random();

		/*
		 * Smoketest
		 */
		for (short type = 0; type < UidType.values().length; type++) {
			for (int i = 0; i < 100; i++) {
				final byte source = (byte) Math.abs((rng.nextInt() % (1 << 5)));
				final int timestamp = rng.nextInt();
				final short ID = (short) rng.nextInt();
				long muid = UidConverter.calculateMuid(type, source,
						timestamp, ID);

				String muidString = UidConverter.serialize(muid);
				assertEquals(muid, UidConverter.deserialize(muidString));
				assertEquals(type, UidConverter.getType(muid));
				assertEquals(source, UidConverter.getSource(muid));
				assertEquals(timestamp, UidConverter.getTimestamp(muid));
				assertEquals(ID, UidConverter.getID(muid));
			}
		}

		/*
		 * Test if the Muid type characters are constant
		 */
		for (short type = 0; type < UidType.values().length; type++) {
			String muidString = UidConverter.serialize(UidConverter
					.calculateMuid(type, (byte) 0, 0, (short) 0));
			String typeString = muidString.substring(9, 11);
			for (int i = 0; i < 100; i++) {
				long muid = UidConverter.calculateMuid(type,
						(byte) Math.abs(rng.nextInt() % (1 << 5)),
						Math.abs(rng.nextInt()),
						(short) Math.abs(rng.nextInt()));
				muidString = UidConverter.serialize(muid);
				assertEquals(typeString, muidString.substring(9, 11));
			}
		}

		try {
			/*
			 * Test if we receive an exception if the source ID is too large
			 */
			UidConverter.calculateMuid((short) 0,
					(byte) (UidConverter.getLargestAllowedSourceID() + 1), 0,
					(short) 0);

			Assert.fail("Muidconverter.calculateMuid did not throw an Exception while running with a bad source ID");
		} catch (MetalconRuntimeException e) {
		}
	}

	@Test
	public void perfTest() {
		Random rng = new Random();
		final int iterations = (int) 1E5;

		/*
		 * heat up CPU
		 */
		for (int i = 0; i != iterations; i++) {
			rng.nextInt();
		}

		/*
		 * MUID generation
		 */
		long start = System.currentTimeMillis();
		for (int i = 0; i != iterations; i++) {
			short s = (short) (rng.nextInt() % (1 << 9));
			byte b = (byte) (rng.nextInt() % (1 << 5));
			int in = rng.nextInt();
			s = (short) rng.nextInt();
		}
		double randomTime = (System.currentTimeMillis() - start)
				/ (float) (iterations / 1E6);

		start = System.currentTimeMillis();
		for (int i = 0; i != iterations; i++) {
			UidConverter.calculateMuidWithoutChecking(
					(short) (rng.nextInt() % (1 << 9)),
					(byte) (rng.nextInt() % UidConverter
							.getLargestAllowedSourceID()), rng.nextInt(),
					(short) rng.nextInt());
		}
		double timePerMUID = (System.currentTimeMillis() - start)
				/ (float) (iterations / 1E6) - randomTime;
		System.out.println(timePerMUID + "ns per MUID generation");

		/*
		 * MUID serialization
		 */
		start = System.currentTimeMillis();
		for (int i = 0; i != iterations; i++) {
			Math.abs(rng.nextLong());
		}
		randomTime = (System.currentTimeMillis() - start)
				/ (float) (iterations / 1E6);

		start = System.currentTimeMillis();
		for (int i = 0; i != iterations; i++) {
			UidConverter.serialize(Math.abs(rng.nextLong()));
		}
		double timePerSerialization = (System.currentTimeMillis() - start)
				/ (float) (iterations / 1E6) - randomTime;
		System.out.println(timePerSerialization + "ns per MUID serialization");

		/*
		 * MUID deserialization
		 */
		ArrayList<String> muids = new ArrayList<String>();
		for (int i = 0; i != iterations; i++) {
			String muid;
			do {
				muid = BigInteger.valueOf(rng.nextLong()).abs().toString(32)
						.substring(2);
			} while (muid.length() < 11);
			muid = muid.substring(0, 11);
			muids.add(muid);
		}

		start = System.currentTimeMillis();
		for (String muid : muids) {
			UidConverter.deserialize(muid);
		}
		double timePerDeSerialization = (System.currentTimeMillis() - start)
				/ (float) (iterations / 1E6);
		System.out.println(timePerDeSerialization
				+ "ns per MUID deserialization");
	}

	// @Test
	public void getMUIDStoragePathTest() {
		for (int i = 0; i < 100; i++) {
			String path = UidConverter.getMUIDStoragePath(i);
			System.out.println(path);
		}
	}
}
