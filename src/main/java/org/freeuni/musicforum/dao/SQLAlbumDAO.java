package org.freeuni.musicforum.dao;


import org.freeuni.musicforum.filter.Filter;
import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.SearchRequest;
import org.freeuni.musicforum.model.Song;


import javax.sql.rowset.serial.SerialBlob;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
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
            byte[] decodedByte = Base64.getDecoder().decode(album.coverImageBase64());
            Blob image = new SerialBlob(decodedByte);
            add.setBlob(5, image);
            add.setDate(6, new java.sql.Date(album.uploadDate().getTime()));
            add.executeUpdate();


            PreparedStatement addSongs = con.prepareStatement(
                    "INSERT INTO songs (album_id, name, full_name, album_name, artist_name)" +
                            "VALUES (?, ?, ?, ?, ?);");
            ArrayList<Song> songs = album.songs();
            for (Song song:songs) {
                addSongs.setString(1, album.id());
                addSongs.setString(2, song.name());
                addSongs.setString(3, song.songName());
                addSongs.setString(4, album.albumName());
                addSongs.setString(5, album.artistName());
                addSongs.executeUpdate();

            }
            addSongs.close();
            add.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void addSongs(String id, ArrayList<Song> songs) {
        try{
            PreparedStatement addSongs = con.prepareStatement(
                    "INSERT INTO songs (album_id, name, full_name, album_name, artist_name)" +
                            "VALUES (?, ?, ?, ?, ?);");
            Album alb = getById(id);
            for (Song song:songs) {
                addSongs.setString(1, alb.id());
                addSongs.setString(2, song.name());
                addSongs.setString(3, song.songName());
                addSongs.setString(4, alb.albumName());
                addSongs.setString(5, alb.artistName());
                addSongs.executeUpdate();
            }
            addSongs.close();

        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Album getById(String id) {
        Album alb = null;
        ArrayList<Song> songs = new ArrayList<>();
        try{
            PreparedStatement songStm = con.prepareStatement(
                "SELECT * FROM songs WHERE album_id=?;");
            songStm.setString(1, id);
            ResultSet songRs = songStm.executeQuery();
            while(songRs.next()) {
                Song song = new Song(songRs.getString(2), songRs.getString(3), songRs.getString(4),
                        songRs.getString(5));
                songs.add(song);

            }
            songRs.close();
            songStm.close();
            PreparedStatement albStm = con.prepareStatement(
                    "SELECT * FROM albums WHERE id=?;");
            albStm.setString(1, id);
            ResultSet rs = albStm.executeQuery();
            if(rs.next()) {
                Blob b = rs.getBlob("cover_image");
                byte[] ba = b.getBytes(1L, (int) b.length());
                byte[] img64 = Base64.getEncoder().encode(ba);
                String photo64 = new String(img64);
                alb = new Album(rs.getString(2), rs.getString(3), rs.getString(4),
                        photo64, songs, rs.getString(1), rs.getDate(6));
            }
            rs.close();
            albStm.close();
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return alb;
    }

    @Override
    public List<Album> getAllByUser(String username) {
        List<Album> albums = new ArrayList<>();
        try{
            PreparedStatement getIDs = con.prepareStatement(
                    "SELECT id FROM albums WHERE username=?;");
            getIDs.setString(1, username);
            ResultSet rs = getIDs.executeQuery();
            albums = makeListById(rs);
            rs.close();
            getIDs.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return albums;
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
    public void delete(String id) {
        try{
            PreparedStatement removeSongs = con.prepareStatement(
                    "DELETE FROM songs WHERE album_id=?;");
            removeSongs.setString(1, id);
            removeSongs.executeUpdate();
            removeSongs.close();

            PreparedStatement remove = con.prepareStatement(
                    "DELETE FROM albums WHERE id=?;");
            remove.setString(1, id);
            remove.executeUpdate();
            remove.close();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Album> getFiltered(Filter f) {
        List<Album> list = new ArrayList<>();
        try {
            Statement getStm = con.createStatement();
            ResultSet rs = getStm.executeQuery("SELECT id " +
                    "FROM albums;");
            list = makeListById(rs);
            rs.close();
            getStm.close();
            return list.stream().filter(album ->
                    f.doFilter(new SearchRequest(album))).toList();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }


    private List<Album> makeListById(ResultSet rs) {
        List<Album> list = new ArrayList<>();
        try {
        while (rs.next()) {
            list.add(getById(rs.getString(1)));
        }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return list;

    }


}
