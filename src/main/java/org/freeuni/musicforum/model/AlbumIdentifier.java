package org.freeuni.musicforum.model;

import org.freeuni.musicforum.util.Utils;

public class AlbumIdentifier {
    private final String hashed;

    public AlbumIdentifier(String albumName, String artistName) {
        hashed = Utils.hashText(albumName+artistName);
    }

    public String hashed() {
        return hashed;
    }
}
