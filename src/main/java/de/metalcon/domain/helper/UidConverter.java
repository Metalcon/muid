package de.metalcon.domain.helper;

import de.metalcon.domain.UidType;
import de.metalcon.exceptions.MetalconRuntimeException;

/**
 * This class defines the 64 bit MuidS and can generate, serialize and deserialize them 
 * 
 * This code is GPLv3
 */

/**
 * @author Jonas Kunze
 * 
 */
public class UidConverter {
	private final static char[] folderChars = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static final char DIGITS[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', '!', '~' };
	public static final int base = DIGITS.length;

	private final static int[] reverseTokens;
	static {
		reverseTokens = new int[256];
		for (int i = 0; i != 256; ++i) {
			reverseTokens[i] = -1;
		}
		for (int i = 0; i != base; ++i) {
			reverseTokens[(DIGITS[i])] = i;
		}
	}

	private final static short Muid_LENGTH = 11;

	/**
	 * 
	 * @param type
	 *            The type identifier to be checked
	 * @return <true> if the type is a correct value
	 */
	public static boolean checkType(final short type) {
		return type < getLargestAllowedType();
	}

	/**
	 * 
	 * @return the largest value a Muid type may have
	 */
	public static short getLargestAllowedType() {
		return UidType.getLargestAllowedType();
	}

	/**
	 * 
	 * @param type
	 *            The source identifier to be checked
	 * @return <true> if the source is a correct value
	 */
	public static boolean checkSource(final byte source) {
		return source <= getLargestAllowedSourceID();
	}

	/**
	 * 
	 * @return the largest value a source ID may have
	 */
	public static short getLargestAllowedSourceID() {
		return (short) (1 << 5) - 1;
	}

	/**
	 * Generates a Muid containing the given information. The format of the Muid
	 * is following (in big endianess):
	 * 
	 * Bit 0 is always 0 to force positive numbers for serialization
	 * implementation
	 * 
	 * Bits 1-9 define the Muid type (9 bit - 0-511 - 2 chars)
	 * 
	 * Bit 10 is always 0 to enforce the first two tokens in the alphanumeric
	 * version to be equal for each Muid with the same type
	 * 
	 * Bits 11-15 define the Source (5 bit)
	 * 
	 * Bits 16-47 define the timestamp (32 bit)
	 * 
	 * Bits 48-63 define the ID (16 bit)
	 * 
	 * @param type
	 *            The type of the Muid to be created
	 * @param sourceID
	 *            The source of the creator of the Muid (the node running this
	 *            code)
	 * @param timestamp
	 *            The timestamp to be stored in the Muid (creation time)
	 * @param ID
	 *            The relative ID within the given timestamp, source and type.
	 * @return The Muid containing all the given information
	 */
	public static long calculateMuidWithoutChecking(final short type,
			final byte sourceID, final int timestamp, final short ID) {
		return
		/* Highest bit is 0 for serialization algorithm */
		// 0l << (64 - 1) |

		/* Highest 9 bits are type */
		(((long) type & 511) << (64 - 1 - 9))

		/*
		 * Next bit is empty so that the first two alphanumerics only depend on
		 * type and not also the source ID
		 */
		/* 0l << (64 - 1 - 9 -1) */

		/* Next 5 bits are source */
		| (((long) sourceID & 31) << (64 - 1 - 9 - 1 - 5))

		/* Next 4 bytes are TS */
		| ((timestamp & 0xFFFFFFFFL) << (64 - 1 - 9 - 1 - 5 - 32))

		/* Next 2 bytes are ID */
		| ID & 0xFFFFL;
	}

	/**
	 * Generates a UrlID containing the given information. The format of the ID
	 * is following (in big endianess):
	 * 
	 * Bit 0 is always 0 to force positive numbers for serialization
	 * implementation
	 * 
	 * Bits 1-9 define the UID type (9 bit - 0-511 - 2 chars) which is
	 * UidType.URL
	 * 
	 * Bit 10 is always 0 to enforce the first two tokens in the alphanumeric
	 * version to be equal for each Muid with the same type
	 * 
	 * Bits 11-15 are not yet used and therefore also 0
	 * 
	 * Bits 16-31 define the domain ID (16 bit)
	 * 
	 * Bits 32-63 define file ID (32 bit)
	 * 
	 * @param type
	 *            The type of the Muid to be created
	 * @param sourceID
	 *            The source of the creator of the Muid (the node running this
	 *            code)
	 * @param timestamp
	 *            The timestamp to be stored in the Muid (creation time)
	 * @param ID
	 *            The relative ID within the given timestamp, source and type.
	 * @return The Muid containing all the given information
	 */
	public static long calculateUrlIdWithoutChecking(final short domainID,
			final int fileID) {
		return
		/* Highest bit is 0 for serialization algorithm */
		// 0l << (64 - 1) |

		/* Highest 9 bits are type */
		(((long) UidType.URL.getRawIdentifier() & 511) << (64 - 1 - 9))

		/*
		 * Next bit is empty so that the first two alphanumerics only depend on
		 * type and not also the source ID
		 */
		/* 0l << (64 - 1 - 9 -1) */

		/* Next 5 bits are 0 (reserved) */
		| (((long) 0 & 31) << (64 - 1 - 9 - 1 - 5))

		/* Next 2 bytes are the domain */
		| ((domainID & 0xFFFFL) << (64 - 1 - 9 - 1 - 5 - 16))

		/* Next 4 bytes define the path within that domain */
		| fileID & 0xFFFFFFFFL;
	}

	public static long calculateMuid(final short type, final byte source,
			final int timestamp, final short ID) {
		if (!checkType(type)) {
			throw new MetalconRuntimeException(
					"Muid Type may not be larger or equal to " + (1 << 9));
		}

		if (!checkSource(source)) {
			throw new MetalconRuntimeException(
					"Muid Source may not be larger or equal to " + (1 << 5));
		}

		return calculateMuidWithoutChecking(type, source, timestamp, ID);
	}

	/**
	 * Returns the type stored within the given Muid
	 * 
	 * @param muid
	 *            The Muid storing the type searched for
	 * @return The type within the given muid
	 */
	public static short getType(final long muid) {
		return (short) ((muid >>> (64 - 9 - 1)) & 511);
	}

	/**
	 * Returns the source that generated the given Muid
	 * 
	 * @param muid
	 *            The Muid storing the source searched for
	 * @return The source that created the given Muid
	 */
	public static byte getSource(final long muid) {
		return (byte) ((muid >>> (64 - 1 - 9 - 1 - 5)) & 31);
	}

	/**
	 * Returns the timestamp the given Muid has been created
	 * 
	 * @param muid
	 *            The Muid storing the timestamp searched for
	 * @return The timestamp the given Muid has been created at
	 */
	public static int getTimestamp(final long muid) {
		return (int) ((muid >>> (64 - 1 - 9 - 1 - 5 - 32)) & 0xFFFFFFFFL);
	}

	/**
	 * Returns the ID stored within the given Muid
	 * 
	 * @param muid
	 *            The Muid storing the ID searched for
	 * @return The ID within the given Muid
	 */
	public static short getFineTime(final long muid) {
		return (short) (muid & 0xFFFFL);
	}

	/**
	 * Returns the domainID the given UrlID has been created
	 * 
	 * @param muid
	 *            The UrlID storing the domainID searched for
	 * @return The domainID of the given UrlID
	 */
	public static short getDomainID(final long urlID) {
		return (short) ((urlID >>> (64 - 1 - 9 - 1 - 5 - 16)) & 0xFFFFL);
	}

	/**
	 * Returns the fileID stored within the given UrlID
	 * 
	 * @param muid
	 *            The UrlID storing the fileID searched for
	 * @return The fileID of the given UrlID
	 */
	public static int getFileID(final long urlID) {
		return (int) (urlID & 0xFFFFFFFFL);
	}

	/**
	 * Parses the given Muid to an alphanumeric string
	 * 
	 * @param muid
	 *            The Muid to be parsed
	 * @return The alphanumeric string corresponding to the given Muid
	 */
	public static String serialize(long muid) {
		StringBuilder string = new StringBuilder(13);
		for (int i = 0; i != Muid_LENGTH; ++i) {
			int rest = (int) (muid % base);
			string.append(DIGITS[rest]);
			muid = muid / base;
		}
		return string.toString();
	}

	/**
	 * Parses the given Muid in alphanumeric string format to it's corresponding
	 * long version
	 * 
	 * @param idString
	 *            The alphanumeric string describing the Muid to be parsed
	 * @return The Muid in it's long format
	 */
	public static long deserialize(final String idString) {
		long tmp = 0;
		for (int i = idString.length() - 1; i >= 0; --i) {
			tmp *= base;
			char c = idString.charAt(i);
			if (reverseTokens[c] == -1) {
				throw new NumberFormatException();
			}
			tmp += reverseTokens[c];
		}
		return tmp;
	}

	/**
	 * Returns all characters a Muid path may consist of
	 * 
	 * @return All allowed characters
	 */
	public static char[] getAllowedFolderNames() {
		return folderChars;
	}

	/**
	 * 
	 * @return The number for characters an alphanumeric version of any Muid
	 *         consist of
	 */
	public static short getMuidLength() {
		return Muid_LENGTH;
	}

	/**
	 * Returns the path corresponding to the given muid. The path consist of 3
	 * folders with one hex character as name (0-f). The letters are generated
	 * from 4 consecutive bits in the 32-bit hash of the Muid. The path will be
	 * in the format [0-9a-f]/[0-9a-f]/[0-9a-f]/
	 * 
	 * @param muid
	 *            The Muid to be taken for the path generation
	 * @return The path that should be used to store data coresponding to the
	 *         given Muid
	 */
	public static String getMuidStoragePath(final long muid) {
		/*
		 * Split the muid into shorts and xor them
		 */
		short hash = generatePersistentHash(muid);
		char[] paths = new char[3];
		paths[0] = (char) ('a' + (hash & 15));
		paths[1] = (char) ('a' + ((hash >> 4) & 15));
		paths[2] = (char) ('a' + ((hash >> 8) & 15));

		return folderChars[(hash & 15)] + "/" + folderChars[((hash >> 4) & 15)]
				+ "/" + folderChars[((hash >> 8) & 15)] + "/";
	}

	public static short generatePersistentHash(final long muid) {
		return (short) ((muid ^ (muid >>> 16)) ^ ((muid >>> 32) ^ (muid >>> 48)));
	}
}