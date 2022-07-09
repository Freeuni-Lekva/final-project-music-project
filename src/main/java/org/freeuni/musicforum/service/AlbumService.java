package org.freeuni.musicforum.service;

import org.freeuni.musicforum.dao.AlbumDAO;
import org.freeuni.musicforum.exception.AlbumExistsException;
import org.freeuni.musicforum.exception.NonexistentAlbumException;
import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.AlbumIdentifier;
import org.freeuni.musicforum.model.Song;

import java.util.List;

public class AlbumService {

    AlbumDAO dao;
    public AlbumService(AlbumDAO dao) {
        this.dao = dao;
    }

    public void addNewAlbum(Album album) {
        AlbumIdentifier id = album.id();
        if(dao.exists(id)) throw new AlbumExistsException();
        dao.add(album);
    }

    public void addSongs(AlbumIdentifier id, List<Song> songs) {
        if(!dao.exists(id)) throw new NonexistentAlbumException();
        Album album = dao.getById(id);
        album.songs().addAll(songs);
    }

    public Album getAlbum(AlbumIdentifier id) {
        if(!dao.exists(id)) throw new NonexistentAlbumException();
        Album album = dao.getById(id);
        return album;
    }

}
