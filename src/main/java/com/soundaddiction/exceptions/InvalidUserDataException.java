package com.soundaddiction.exceptions;

public class InvalidUserDataException extends Exception {

    public InvalidUserDataException() {
        super();
    }

    public InvalidUserDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserDataException(String message) {
        super(message);
    }

    public InvalidUserDataException(Throwable cause) {
        super(cause);
    }

}
