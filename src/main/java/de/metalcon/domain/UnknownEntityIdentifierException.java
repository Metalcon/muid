package de.metalcon.domain;

import de.metalcon.exceptions.MetalconException;

public class UnknownEntityIdentifierException extends MetalconException {
	private static final long serialVersionUID = 2216138425176070447L;

	public UnknownEntityIdentifierException(final short identifier) {
		super("Unknown entity identifier: " + identifier);
	}
}
