package service;

import org.freeuni.musicforum.dao.AlbumDAO;
import org.freeuni.musicforum.dao.InMemoryAlbumDAO;
import org.freeuni.musicforum.exception.AlbumExistsException;
import org.freeuni.musicforum.exception.NonexistentAlbumException;
import org.freeuni.musicforum.model.Album;
import org.freeuni.musicforum.model.Review;
import org.freeuni.musicforum.model.Song;
import org.freeuni.musicforum.service.AlbumService;
import org.freeuni.musicforum.service.ServiceFactory;
import org.freeuni.musicforum.util.Utils;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlbumServiceTest   {

    @Test
    void TestAddAlbum() {
        AlbumDAO dao = new InMemoryAlbumDAO();
        AlbumService service = new AlbumService(dao);
        String username = "eva";
        String albumName = "firstAlbum";
        String artistName = "firstArtist";
        String id = Utils.hashText(albumName + artistName);
        Album firstAlb = new Album(username, albumName, artistName,
                null, new ArrayList<>(), id, new Date());

        // same username diff album
        albumName = "secondAlbum";
        artistName = "secondArtist";
        id = Utils.hashText(albumName + artistName);
        Album secondAlb = new Album(username, albumName, artistName,
                null, new ArrayList<>(), id, new Date());

        // diff username too
        username = "melanie1996";
        albumName = "thirdAlbum";
        artistName = "thirdArtist";
        id = Utils.hashText(albumName + artistName);
        Album thirdAlb = new Album(username, albumName, artistName,
                null, new ArrayList<>(), id, new Date());

        assertDoesNotThrow(() -> service.addNewAlbum(firstAlb));
        assertDoesNotThrow(() -> service.addNewAlbum(secondAlb));
        assertDoesNotThrow(() -> service.addNewAlbum(thirdAlb));
    }

    @Test
    void TestAddDuplicateAlbum() {
        AlbumDAO dao = new InMemoryAlbumDAO();
        AlbumService service = new AlbumService(dao);
        String username = "eva";
        String albumName = "firstAlbum";
        String artistName = "firstArtist";
        String id = Utils.hashText(albumName + artistName);
        Album firstAlb = new Album(username, albumName, artistName,
                null, new ArrayList<>(), id, new Date());

        // having same uploader
        Album firstAlbDuplicate = new Album(username, albumName, artistName,
                null, new ArrayList<>(), id, new Date());

        //having only same id (albumName and artistName)
        username = "melanie1996";
        Album firstAlbSAme = new Album(username, albumName, artistName,
                null, new ArrayList<>(), id, new Date());

        assertDoesNotThrow(() -> service.addNewAlbum(firstAlb));
        assertThrows(AlbumExistsException.class, (()-> service.addNewAlbum(firstAlbDuplicate)));
        assertThrows(AlbumExistsException.class, (()-> service.addNewAlbum(firstAlbSAme)));
    }

    @Test
    void TestGetAlbum() {
        AlbumDAO dao = new InMemoryAlbumDAO();
        AlbumService service = new AlbumService(dao);
        String username = "eva";
        String albumName = "addedAlbum";
        String artistName = "addedArtist";
        String id1 = Utils.hashText(albumName + artistName);
        Album firstAlb = new Album(username, albumName, artistName,
                null, new ArrayList<>(), id1, new Date());
        service.addNewAlbum(firstAlb);

        albumName = "secondAlbum";
        artistName = "secondArtist";
        String id2 = Utils.hashText(albumName + artistName);
        Album secondAlb = new Album(username, albumName, artistName,
                null, new ArrayList<>(), id2, new Date());
        service.addNewAlbum(secondAlb);

        username = "melanie1996";
        albumName = "thirdAlbum";
        artistName = "thirdArtist";
        String id3 = Utils.hashText(albumName + artistName);
        Album thirdAlb = new Album(username, albumName, artistName,
                null, new ArrayList<>(), id3, new Date());
        service.addNewAlbum(thirdAlb);


        Album same1 = service.getAlbum(id1);
        Album same2 = service.getAlbum(id2);
        Album same3 = service.getAlbum(id3);
        assertEquals(firstAlb, same1);
        assertEquals(secondAlb, same2);
        assertEquals(thirdAlb, same3);

    }

    @Test
    void TestGetNonexistentAlbum() {
        AlbumDAO dao = new InMemoryAlbumDAO();
        AlbumService service = new AlbumService(dao);
        assertThrows(NonexistentAlbumException.class, (()-> service.getAlbum("nonId1")));
        assertThrows(NonexistentAlbumException.class, (()-> service.getAlbum("nonId2")));
    }

    @Test
    void TestGetAlbumsUploadedBy() {
        AlbumDAO dao = new InMemoryAlbumDAO();
        AlbumService service = new AlbumService(dao);
        String username = "eva";
        String albumName = "addedAlbum";
        String artistName = "addedArtist";
        String id1 = Utils.hashText(albumName + artistName);
        Album firstAlb = new Album(username, albumName, artistName,
                null, new ArrayList<>(), id1, new Date());
        service.addNewAlbum(firstAlb);

        albumName = "secondAlbum";
        artistName = "secondArtist";
        String id2 = Utils.hashText(albumName + artistName);
        Album secondAlb = new Album(username, albumName, artistName,
                null, new ArrayList<>(), id2, new Date());
        service.addNewAlbum(secondAlb);

        username = "melanie1996";
        albumName = "thirdAlbum";
        artistName = "thirdArtist";
        String id3 = Utils.hashText(albumName + artistName);
        Album thirdAlb = new Album(username, albumName, artistName,
                null, new ArrayList<>(), id3, new Date());
        service.addNewAlbum(thirdAlb);

        List<Album> evaAlbs = service.getAllAlbumsUploadedBy("eva");
        assertEquals(2, evaAlbs.size());
        assertTrue(evaAlbs.contains(firstAlb));
        assertTrue(evaAlbs.contains(secondAlb));

        List<Album> melsAlbs = service.getAllAlbumsUploadedBy("melanie1996");
        assertEquals(1, melsAlbs.size());
        assertTrue(melsAlbs.contains(thirdAlb));

        List<Album> nobodysAlbs = service.getAllAlbumsUploadedBy("noUploader");
        assertEquals(0, nobodysAlbs.size());
    }

    @Test
    void TestExists() {
        AlbumDAO dao = new InMemoryAlbumDAO();
        AlbumService service = new AlbumService(dao);
        String username = "eva";
        String albumName = "addedAlbum";
        String artistName = "addedArtist";
        String id1 = Utils.hashText(albumName + artistName);
        Album firstAlb = new Album(username, albumName, artistName,
                null, new ArrayList<>(), id1, new Date());
        service.addNewAlbum(firstAlb);

        albumName = "secondAlbum";
        artistName = "secondArtist";
        String id2 = Utils.hashText(albumName + artistName);
        Album secondAlb = new Album(username, albumName, artistName,
                null, new ArrayList<>(), id2, new Date());
        service.addNewAlbum(secondAlb);

        username = "melanie1996";
        albumName = "thirdAlbum";
        artistName = "thirdArtist";
        String id3 = Utils.hashText(albumName + artistName);
        Album thirdAlb = new Album(username, albumName, artistName,
                null, new ArrayList<>(), id3, new Date());
        service.addNewAlbum(thirdAlb);

        assertTrue(service.exists(id1));
        assertTrue(service.exists(id2));
        assertTrue(service.exists(id3));
        assertFalse(service.exists("noId"));
    }

    @Test
    void TestAddAndExistsSongs() {
        AlbumDAO dao = new InMemoryAlbumDAO();
        AlbumService service = new AlbumService(dao);
        String username = "eva";
        String albumName = "addedAlbum";
        String artistName = "addedArtist";
        String id1 = Utils.hashText(albumName + artistName);
        Album firstAlb = new Album(username, albumName, artistName,
                null, new ArrayList<>(), id1, new Date());
        service.addNewAlbum(firstAlb);

        Song song1 = new Song("song1", "song1FullName", albumName, artistName);
        Song song2 = new Song("song2", "song2FullName", albumName, artistName);
        Song song3 = new Song("song3", "song3FullName", albumName, artistName);
        ArrayList<Song> songs = new ArrayList<>();
        songs.add(song1);
        songs.add(song2);

        service.addSongs(id1, songs);
        assertEquals(2, firstAlb.songs().size());
        assertTrue(firstAlb.songs().contains(song1));
        assertTrue(firstAlb.songs().contains(song2));

        assertTrue(service.doesSongExist(id1, "song1FullName"));
        assertTrue(service.doesSongExist(id1, "song2FullName"));
        assertFalse(service.doesSongExist(id1, "song3FullName"));
    }

    @Test
    void TestAverageStar() {
        AlbumDAO dao = new InMemoryAlbumDAO();
        AlbumService service = new AlbumService(dao);
        String username = "eva";
        String albumName = "addedAlbum";
        String artistName = "addedArtist";
        String id1 = Utils.hashText(albumName + artistName);
        Album firstAlb = new Album(username, albumName, artistName,
                null, new ArrayList<>(), id1, new Date());
        service.addNewAlbum(firstAlb);

        assertEquals(0, service.getAverageStarFor(id1));

        Review great = new Review(id1, "poster", "Great!", 5);
        ServiceFactory.getReviewService().postReview(great);

        assertEquals(5, service.getAverageStarFor(id1));

        Review mid = new Review(id1, "poster2", "meh", 3);
        ServiceFactory.getReviewService().postReview(mid);

        assertEquals(4, service.getAverageStarFor(id1));

        Review bad = new Review(id1, "poster3", "nah", 1);
        ServiceFactory.getReviewService().postReview(bad);

        assertEquals(3, service.getAverageStarFor(id1));

        // needs rounding down
        Review bad2 = new Review(id1, "poster4", "naah", 0);
        ServiceFactory.getReviewService().postReview(bad2);

        assertEquals(2, service.getAverageStarFor(id1));

        // needs rounding up
        Review great2 = new Review(id1, "poster5", "Great!!", 5);
        ServiceFactory.getReviewService().postReview(great2);

        assertEquals(3, service.getAverageStarFor(id1));
    }

    @Test
    void TestDeleteAlbum() {
        AlbumDAO dao = new InMemoryAlbumDAO();
        AlbumService service = new AlbumService(dao);
        String username = "eva";
        String albumName = "addedAlbum";
        String artistName = "addedArtist";
        String id1 = Utils.hashText(albumName + artistName);
        Album firstAlb = new Album(username, albumName, artistName,
                null, new ArrayList<>(), id1, new Date());
        service.addNewAlbum(firstAlb);

        albumName = "secondAlbum";
        artistName = "secondArtist";
        String id2 = Utils.hashText(albumName + artistName);
        Album secondAlb = new Album(username, albumName, artistName,
                null, new ArrayList<>(), id2, new Date());
        service.addNewAlbum(secondAlb);

        username = "melanie1996";
        albumName = "thirdAlbum";
        artistName = "thirdArtist";
        String id3 = Utils.hashText(albumName + artistName);
        Album thirdAlb = new Album(username, albumName, artistName,
                null, new ArrayList<>(), id3, new Date());
        service.addNewAlbum(thirdAlb);

        assertTrue(service.exists(id1));
        service.deleteAlbum(id1);
        assertFalse(service.exists(id1));

        assertTrue(service.exists(id2));
        service.deleteAlbum(id2);
        assertFalse(service.exists(id2));

        assertTrue(service.exists(id3));
        service.deleteAlbum(id3);
        assertFalse(service.exists(id3));
    }

}
