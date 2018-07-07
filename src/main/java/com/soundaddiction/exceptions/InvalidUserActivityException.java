package com.soundaddiction.exceptions;

public class InvalidUserActivityException extends Exception {

    public InvalidUserActivityException() {
        super();
    }

    public InvalidUserActivityException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserActivityException(String message) {
        super(message);
    }

    public InvalidUserActivityException(Throwable cause) {
        super(cause);
    }

}
