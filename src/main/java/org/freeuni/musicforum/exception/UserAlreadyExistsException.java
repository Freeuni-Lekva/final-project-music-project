package org.freeuni.musicforum.exception;

public class UserAlreadyExistsException extends IllegalArgumentException {
    public UserAlreadyExistsException() {
        super("User with this username already exists");
    }
}
