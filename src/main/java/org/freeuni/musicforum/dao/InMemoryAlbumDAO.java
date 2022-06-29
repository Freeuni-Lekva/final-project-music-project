package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.AlbumIdentifier;

import java.util.HashMap;

public class InMemoryAlbumDAO implements AlbumDAO{

    HashMap<AlbumIdentifier, Album> albums;

    public InMemoryAlbumDAO() {
        albums = new HashMap<>();
    }

    @Override
    public void add(Album album) {
        albums.put(album.id(), album);
    }

    @Override
    public Album getById(AlbumIdentifier id) {
        return albums.get(id);
    }

    @Override
    public boolean exists(AlbumIdentifier id) {
        return albums.containsKey(id);
    }

    public int albumCount() {
        return albums.size();
    }
}
