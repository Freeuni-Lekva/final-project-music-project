package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.AlbumIdentifier;

import java.sql.Connection;
import java.util.List;

public class SQLAlbumDAO implements AlbumDAO {

    private final Connection con;
    public SQLAlbumDAO() {
        con = DataSource.INSTANCE.getConnection();
    }
    @Override
    public void add(Album album) {

    }

    @Override
    public Album getById(String id) {
        return null;
    }

    @Override
    public List<Album> getAllByUser(String username) {
        return null;
    }

    @Override
    public int getAverageStar(String id) {
        return 0;
    }

    @Override
    public boolean exists(String id) { return false; }

    @Override
    public int calculatePrestigeFor(String id) { return 0; }

    @Override
    public void delete(String id) {

    }

}
