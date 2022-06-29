package org.freeuni.musicforum.model;

import org.freeuni.musicforum.util.UserUtils;

public class AlbumIdentifier {
    private final String hashed;

    public AlbumIdentifier(String text) {
        hashed = UserUtils.hashPassword(text);
    }

    public String hashed() {
        return hashed;
    }
}
