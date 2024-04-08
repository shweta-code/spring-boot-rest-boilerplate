package com.bihan.boilerplate.rest.exception;

public class ArgumentNullException extends RuntimeException {

    public ArgumentNullException(String message) {
        super(message);
    }

    public ArgumentNullException(String message, Throwable cause) {
        super(message, cause);
    }
}
