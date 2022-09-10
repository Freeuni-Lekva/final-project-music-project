package org.freeuni.musicforum.service;

import org.freeuni.musicforum.Activity.Activity;
import org.freeuni.musicforum.Activity.ActivityLog;
import org.freeuni.musicforum.dao.AlbumDAO;
import org.freeuni.musicforum.exception.AlbumExistsException;
import org.freeuni.musicforum.exception.NonexistentAlbumException;
import org.freeuni.musicforum.filter.Filter;
import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.Song;

import java.util.ArrayList;
import java.util.List;

public class AlbumService {

    AlbumDAO dao;
    public AlbumService(AlbumDAO dao) {
        this.dao = dao;
    }

    public void addNewAlbum(Album album) {
        String id = album.id();
        if(dao.exists(id)) throw new AlbumExistsException();
        dao.add(album);
        Activity.getActivityLog().addLog(ActivityLog.ActivityType.NEW_ALBUM, album.id());
    }

    public void addSongs(String id, ArrayList<Song> songs) {
        if(!dao.exists(id)) throw new NonexistentAlbumException();
        dao.addSongs(id,songs);
    }

    public boolean exists(String id) {
        return dao.exists(id);
    }
    public Album getAlbum(String id) {
        if(!dao.exists(id)) throw new NonexistentAlbumException();
        return dao.getById(id);
    }

    public List<Album> getAllAlbumsUploadedBy(String username) {
        return dao.getAllByUser(username);
    }

    public int getAverageStarFor(String id) {
        return dao.getAverageStar(id);
    }

    public boolean doesSongExist(String id, String name) {
        Album album = getAlbum(id);
        return album.songs().stream().anyMatch(song -> song.songName().equals(name));
    }

    public int getAlbumPrestigeFor(String username) {
        List<Album> albums = getAllAlbumsUploadedBy(username);
        int res = 0;
        for (Album a : albums) {
            res += dao.calculatePrestigeFor(a.id());
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
