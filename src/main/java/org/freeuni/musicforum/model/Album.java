package org.freeuni.musicforum.model;

import java.util.ArrayList;

public record Album(
        String albumName,
        String artistName,
        String coverImageSrc,
        ArrayList<Song> songs,
        AlbumIdentifier id
) {}
