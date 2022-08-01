package org.freeuni.musicforum.service;

import org.freeuni.musicforum.dao.AlbumDAO;
import org.freeuni.musicforum.exception.AlbumExistsException;
import org.freeuni.musicforum.exception.NonexistentAlbumException;
import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.AlbumIdentifier;
import org.freeuni.musicforum.model.Song;

import java.util.List;
import java.util.stream.Collectors;

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

    public Album getAlbum(String hashedId) {
        if(!dao.exists(hashedId)) throw new NonexistentAlbumException();
        Album album = dao.getByHashedId(hashedId);
        return album;
    }

    public List<Album> getAllAlbumsUploadedBy(String username) {
        return dao.getAllByUser(username);
    }

    public int getAverageStarFor(AlbumIdentifier id) {
        return dao.getAverageStar(id);
    }

    public boolean doesSongExist(AlbumIdentifier id, String name) {
        Album album = getAlbum(id);
        return album.songs().stream().filter(song -> song.songName().equals(name)).findAny().isPresent();
    }

}
