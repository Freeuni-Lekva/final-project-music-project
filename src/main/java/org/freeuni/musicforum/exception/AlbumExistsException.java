package org.freeuni.musicforum.exception;

public class AlbumExistsException extends IllegalArgumentException{
    public AlbumExistsException() {
        super("This Album Has Already Been Added To The Website");
    }
}
