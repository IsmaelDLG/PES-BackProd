package com.pesados.purplepoint.api.exception;

public class UnexpectedSQLError extends RuntimeException {
/**
	 *
	 */
    private static final long serialVersionUID = 1L;
    
    public UnexpectedSQLError(String msg) {
        super(msg);
    }
}
