package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.filter.Filter;
import org.freeuni.musicforum.model.Album;

import java.util.List;

public interface AlbumDAO {
    void add(Album album);
    Album getById(String id);
    List<Album> getAllByUser(String username);
    int getAverageStar(String id);
    boolean exists(String id);
    int calculatePrestigeFor(String id);
    void delete(String id);
    List<Album> getFiltered(Filter f);
}
