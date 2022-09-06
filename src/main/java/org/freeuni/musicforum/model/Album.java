package org.freeuni.musicforum.model;

import java.util.ArrayList;
import java.util.Date;

public record Album(
        String username,
        String albumName,
        String artistName,
        String coverImageBase64,
        ArrayList<Song> songs,
        AlbumIdentifier id,
        Date uploadDate
) {
}
