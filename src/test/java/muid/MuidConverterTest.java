/**
 * 
 */
package muid;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

import de.metalcon.domain.EntityType;
import de.metalcon.domain.MuidConverter;

/**
 * @author kunzejo
 * 
 */
public class MuidConverterTest {

	@Test
	public void test() {
		Random rng = new Random();

		/*
		 * Smoketest
		 */
		for (short type = 0; type < EntityType.values().length; type++) {
			for (int i = 0; i < 100; i++) {
				final byte source = (byte) Math.abs((rng.nextInt() % (1 << 5)));
				final int timestamp = rng.nextInt();
				final short ID = (short) rng.nextInt();
				long muid = MuidConverter.generateMUID(type, source, timestamp,
						ID);

				String muidString = MuidConverter.serialize(muid);
				assertEquals(muid, MuidConverter.deserialize(muidString));
				assertEquals(type, MuidConverter.getType(muid));
				assertEquals(source, MuidConverter.getSource(muid));
				assertEquals(timestamp, MuidConverter.getTimestamp(muid));
				assertEquals(ID, MuidConverter.getID(muid));
			}
		}

		/*
		 * Test if the entity type characters are constant
		 */
		for (short type = 0; type < EntityType.values().length; type++) {
			String muidString = MuidConverter.serialize(MuidConverter
					.generateMUID(type, (byte) 0, 0, (short) 0));
			String typeString = muidString.substring(9, 11);
			for (int i = 0; i < 100; i++) {
				long muid = MuidConverter.generateMUID(type,
						(byte) Math.abs(rng.nextInt() % (1 << 5)),
						Math.abs(rng.nextInt()),
						(short) Math.abs(rng.nextInt()));
				muidString = MuidConverter.serialize(muid);
				assertEquals(typeString, muidString.substring(9, 11));
			}
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
			MuidConverter.generateMUID((short) (rng.nextInt() % (1 << 9)),
					(byte) (rng.nextInt() % (1 << 5)), rng.nextInt(),
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
			MuidConverter.serialize(Math.abs(rng.nextLong()));
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
			MuidConverter.deserialize(muid);
		}
		double timePerDeSerialization = (System.currentTimeMillis() - start)
				/ (float) (iterations / 1E6);
		System.out.println(timePerDeSerialization
				+ "ns per MUID deserialization");
	}

	// @Test
	public void getMUIDStoragePathTest() {
		for (int i = 0; i < 100; i++) {
			String path = MuidConverter.getMUIDStoragePath(i);
			System.out.println(path);
		}
	}
}
