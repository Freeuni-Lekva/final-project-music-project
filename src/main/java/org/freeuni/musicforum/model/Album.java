package org.freeuni.musicforum.model;

import java.util.ArrayList;

public record Album(
        String albumName,
        String artistName,
        String coverImageBase64,
        ArrayList<Song> songs,
        AlbumIdentifier id
) {
    public void addSong(Song song) {
        songs.add(song);
    }
    public boolean existsSong(Song song) {
        return songs.contains(song);
    }
}
