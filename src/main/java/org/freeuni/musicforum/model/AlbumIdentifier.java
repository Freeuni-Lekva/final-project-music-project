package org.freeuni.musicforum.model;

import org.freeuni.musicforum.util.Utils;

public class AlbumIdentifier {
    private final String hashed;

    public AlbumIdentifier(String text) {
        hashed = Utils.hashText(text);
    }

    public String hashed() {
        return hashed;
    }
}
