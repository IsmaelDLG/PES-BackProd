package com.pesados.purplepoint.api.exception;

public class NewQuestionBadRequest extends Throwable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NewQuestionBadRequest() {
        super("The information provided for the registration of the question is incorrect.");
    }
}
