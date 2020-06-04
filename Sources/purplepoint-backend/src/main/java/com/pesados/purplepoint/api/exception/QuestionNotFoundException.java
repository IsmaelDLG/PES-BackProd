package com.pesados.purplepoint.api.exception;

public class QuestionNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public QuestionNotFoundException(Long id) {
        super("Could not find image with id: " + id.toString());
    }
}
