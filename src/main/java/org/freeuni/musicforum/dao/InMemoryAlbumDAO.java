package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.filter.Filter;
import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.SearchRequest;
import org.freeuni.musicforum.util.Utils;

import java.util.*;

public class InMemoryAlbumDAO implements AlbumDAO{

    Map<String, Album> albums;

    public InMemoryAlbumDAO() {
        albums = new HashMap<>();
        Album al = new Album("guri", "Body Riddle", "Clark", null, new ArrayList<>(), Utils.hashText("Body Riddle"+"Clark"),new Date());
        albums.put(Utils.hashText("Body Riddle"+"Clark"), al);

    }

    @Override
    public void add(Album album) {
        albums.put(album.id(), album);
    }

    @Override
    public Album getById(String id) { return albums.get(id); }

    @Override
    public List<Album> getAllByUser(String username) {
        List<Album> albumsBy = new ArrayList<>();
        albums.forEach((k, v) -> {
            if (v.username().equals(username)) albumsBy.add(v);
        });
        return albumsBy;
    }

    @Override
    public boolean exists(String id) { return albums.containsKey(id); }


    @Override
    public void delete(String id) {
        albums.remove(id);
    }

    @Override
    public List<Album> getFiltered(Filter f) {
        return albums.values().stream().filter(album->f.doFilter(new SearchRequest(album))).toList();
    }

}
