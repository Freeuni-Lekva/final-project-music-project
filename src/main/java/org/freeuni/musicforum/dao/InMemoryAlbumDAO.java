package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.AlbumIdentifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryAlbumDAO implements AlbumDAO{

    Map<String, Album> albums;

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
    public List<Album> getAllByUser(String username) {
        List<Album> albumsBy = new ArrayList<>();
        albums.forEach((k, v) -> {
            if (v.username().equals(username)) albumsBy.add(v);
        });
        return albumsBy;
    }

    @Override
    public boolean exists(AlbumIdentifier id) {
        return albums.containsKey(id.hashed());
    }

    public int albumCount() {
        return albums.size();
    }
}
