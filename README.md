muid
====

MUIDs (Metalcon Unique Identifier) are used to identify all metalcon internal entities.

## Description
A MUID is a 64 bit long identifier containing following information:

- Bit 0 is always 0 to force positive numbers for serialization implementation
- Bits 1-9 define the MUID type (9 bits, 0-511, 2 chars in serialized format)
- Bit 10 is always 0 to enforce the first two tokens in the alphanumeric version to be equal for each MUID with the same type
- Bits 11-15 define the Source ID (5 bit)
- Bits 16-47 define the timestamp (32 bit)
- Bits 48-63 define the ID (16 bit)

The Source ID defines the process that has created the MUID.

The two byte long ID word within a MUID is used to separate MUIDs created within the same second (timestamp) by the same source. 
	 

## Dependency

    <dependency>
      <groupId>de.metalcon</groupId>
      <artifactId>muid</artifactId>
      <version>0.2.0</version>
    </dependency>

# Usage

To create a new unique MUID you should call something like following:
	Muid.create(MuidType.BAND);
