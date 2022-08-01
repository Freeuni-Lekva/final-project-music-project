package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.AlbumIdentifier;

import java.util.List;

public interface AlbumDAO {
    void add(Album album);
    Album getById(AlbumIdentifier id);
    Album getByHashedId(String id);
    List<Album> getAllByUser(String username);
    int getAverageStar(AlbumIdentifier id);
    boolean exists(AlbumIdentifier id);
    boolean exists(String id);
}
