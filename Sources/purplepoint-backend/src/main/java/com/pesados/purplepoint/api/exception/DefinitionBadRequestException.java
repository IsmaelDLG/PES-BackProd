package com.pesados.purplepoint.api.exception;

public class DefinitionBadRequestException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public DefinitionBadRequestException() {
		super("The information provided for the registration of the definition is incorrect.");
	}
}