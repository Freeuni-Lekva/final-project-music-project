package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.AlbumIdentifier;

public interface AlbumDAO {
    public void add(Album album);
    public Album getById(AlbumIdentifier id);
    public boolean exists(AlbumIdentifier id);
}
