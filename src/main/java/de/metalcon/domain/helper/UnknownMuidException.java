package de.metalcon.domain.helper;

import de.metalcon.exceptions.MetalconRuntimeException;

public class UnknownMuidException extends MetalconRuntimeException {
	private static final long serialVersionUID = 2216138425176070447L;

	public UnknownMuidException(final short identifier) {
		super("Unknown Muid type: " + identifier);
	}
}
