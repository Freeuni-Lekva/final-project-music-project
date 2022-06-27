package org.freeuni.musicforum.exception;

public class UnsuccessfulSignupException extends IllegalArgumentException {
    public UnsuccessfulSignupException(String message) {
        super(message);
    }
}
