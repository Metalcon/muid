package de.metalcon.domain;

import de.metalcon.exceptions.MetalconRuntimeException;

public class UnknownEntityIdentifierException extends MetalconRuntimeException {
	private static final long serialVersionUID = 2216138425176070447L;

	public UnknownEntityIdentifierException(final short identifier) {
		super("Unknown entity identifier: " + identifier);
	}
}
