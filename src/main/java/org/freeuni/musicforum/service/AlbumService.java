package org.freeuni.musicforum.service;

import org.freeuni.musicforum.dao.AlbumDAO;
import org.freeuni.musicforum.exception.AlbumExistsException;
import org.freeuni.musicforum.exception.NonexistentAlbumException;
import org.freeuni.musicforum.filter.Filter;
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
        if(dao.exists(id.hashed())) throw new AlbumExistsException();
        dao.add(album);
    }

    public void addSongs(String id, List<Song> songs) {
        if(!dao.exists(id)) throw new NonexistentAlbumException();
        Album album = dao.getById(id);
        album.songs().addAll(songs);
    }

    public Album getAlbum(String id) {
        if(!dao.exists(id)) throw new NonexistentAlbumException();
        Album album = dao.getById(id);
        return album;
    }

    public List<Album> getAllAlbumsUploadedBy(String username) {
        return dao.getAllByUser(username);
    }

    public int getAverageStarFor(String id) {
        return dao.getAverageStar(id);
    }

    public boolean doesSongExist(String id, String name) {
        Album album = getAlbum(id);
        return album.songs().stream().filter(song -> song.songName().equals(name)).findAny().isPresent();
    }

    public int getAlbumPrestigeFor(String username) {
        List<Album> albums = getAllAlbumsUploadedBy(username);
        int res = 0;
        for (Album a : albums) {
            res += dao.calculatePrestigeFor(a.id().hashed());
        }
        return res;
    }

    public void deleteAlbum(String id) {
        dao.delete(id);
        ServiceFactory.getReviewService().deleteAllReviewsFor(id);
        ServiceFactory.getVotingDataService().deleteAllVotingDataForAlbum(id);
    }

    public List<Album> filter(Filter f){ return dao.getFiltered(f); }

}
