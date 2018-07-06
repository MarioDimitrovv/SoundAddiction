package com.soundaddiction.exceptions;

public class InvalidSongDataException extends Exception {

    public InvalidSongDataException() {
        super();
    }

    public InvalidSongDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSongDataException(String message) {
        super(message);
    }

    public InvalidSongDataException(Throwable cause) {
        super(cause);
    }

}
