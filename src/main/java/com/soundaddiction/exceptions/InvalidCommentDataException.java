package com.soundaddiction.exceptions;

public class InvalidCommentDataException extends Exception {

    public InvalidCommentDataException() {
        super();
    }

    public InvalidCommentDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCommentDataException(String message) {
        super(message);
    }

    public InvalidCommentDataException(Throwable cause) {
        super(cause);
    }

}
