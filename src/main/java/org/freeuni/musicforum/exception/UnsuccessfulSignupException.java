package org.freeuni.musicforum.exception;

public class UnsuccessfulSignupException extends RuntimeException {
    public UnsuccessfulSignupException(String message) {
        super(message);
    }
}
