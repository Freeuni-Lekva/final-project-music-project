package org.freeuni.musicforum.dao;

import jdk.jshell.execution.Util;
import org.freeuni.musicforum.filter.Filter;
import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.Song;
import org.freeuni.musicforum.util.Utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SQLAlbumDAO implements AlbumDAO {

    private final Connection con;
    public SQLAlbumDAO() {
        con = DataSource.INSTANCE.getConnection();
    }
    @Override
    public void add(Album album) {
        try{
            PreparedStatement add = con.prepareStatement(
                    "INSERT INTO albums (id, username, album_name, artist_name, cover_image, upload_date) " +
                            "VALUES (?, ?, ?, ?, ?, ?);" );

            add.setString(1, album.id());
            add.setString(2, album.username());
            add.setString(3, album.albumName());
            add.setString(4, album.artistName());
            add.setString(5, album.coverImageBase64());
            add.setDate(6, new java.sql.Date(album.uploadDate().getTime()));
            add.executeUpdate();


            PreparedStatement addSongs = con.prepareStatement(
                    "INSERT INTO songs (album_id, name, full_name, album_name, artist_name, song_content)" +
                            "VALUES (?, ?, ?, ?, ?, ?);");
            ArrayList<Song> songs = album.songs();
            for (Song song:songs) {
                addSongs.setString(1, album.id());
                addSongs.setString(2, song.name());
                addSongs.setString(3, song.songName());
                addSongs.setString(4, album.albumName());
                addSongs.setString(5, album.artistName());
                addSongs.setString(6, song.songBase64());
                addSongs.executeUpdate();

            }
            addSongs.close();
            add.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
    public boolean exists(String id) {
        boolean exist = false;
        try {
            PreparedStatement check = con.prepareStatement("SELECT id FROM albums WHERE id=?;");
            check.setString(1, id);
            ResultSet rs = check.executeQuery();
            if(rs.next()) {
                exist = true;
            }
            rs.close();
            check.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return exist;
    }

    @Override
    public int calculatePrestigeFor(String id) { return 0; }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<Album> getFiltered(Filter f) {
        return null;
    }

    public static void main(String[] args) {
        ArrayList<Song> songs = new ArrayList<>();
        songs.add(new Song("1", "1111", "alb1", "art1", "111"));
        songs.add(new Song("2", "2222", "alb1", "art1", "222"));
        Album someting = new Album("us1", "alb1", "art1",
        "1", songs, Utils.hashText("alb1234" + "art1"), new java.util.Date());

        SQLAlbumDAO dao = new SQLAlbumDAO();
        dao.add(someting);
        System.out.println(dao.exists(Utils.hashText("alb1234" + "art1")));

    }

}
