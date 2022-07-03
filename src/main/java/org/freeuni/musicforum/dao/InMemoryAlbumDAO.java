package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.AlbumIdentifier;

import java.util.HashMap;

public class InMemoryAlbumDAO implements AlbumDAO{

    HashMap<String, Album> albums;

    public InMemoryAlbumDAO() {
        albums = new HashMap<>();
    }

    @Override
    public void add(Album album) {
        albums.put(album.id().hashed(), album);
    }

    @Override
    public Album getById(AlbumIdentifier id) {
        return albums.get(id.hashed());
    }

    @Override
    public boolean exists(AlbumIdentifier id) {
        return albums.containsKey(id.hashed());
    }

    public int albumCount() {
        return albums.size();
    }
}
