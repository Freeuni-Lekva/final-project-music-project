package org.freeuni.musicforum.exception;

public class NonexistentAlbumException extends IllegalArgumentException{
    public NonexistentAlbumException() {
        super("The Album You Are Trying To Reach Doesn't Exist");
    }
}
