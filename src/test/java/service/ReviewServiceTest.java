package service;

import org.freeuni.musicforum.exception.UnsuccessfulReviewException;
import org.freeuni.musicforum.model.Review;
import org.freeuni.musicforum.service.ServiceFactory;
import org.freeuni.musicforum.service.ReviewService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewServiceTest {

    private static final ReviewService service = ServiceFactory.getReviewService();

    @BeforeAll
    static void init() {
        Review r3 = new Review("badAlbum", "Contort", "bad", 2);
        Review r4 = new Review("badAlbum", "Milly", "very bad", 1);
        Review r5 = new Review("aveAlbum", "Contort", "meh", 3);
        Review r6 = new Review("goodAlbum", "Eva", "great", 5);
        Review r7 = new Review("amazAlbum", "Damian", "wow", 5);
        Review r8 = new Review("horrAlbum", "Damian", "worst", 1);
        Review r9 = new Review("worstAlbum", "Calina", "omg", 1);
        Review r10 = new Review("terrAlbum", "Zara", "horrendous", 1);
        service.postReview(r3);
        service.postReview(r4);
        service.postReview(r5);
        service.postReview(r6);
        service.postReview(r7);
        service.postReview(r8);
    }

    @Test
    void testPostReviews() {
        Review r1 = new Review("706456", "Nikifore", "Wow", 5);
        Review r2 = new Review("543563", "Lome", "Love it", 4);

        assertDoesNotThrow(() -> service.postReview(r1));
        assertDoesNotThrow(() -> service.postReview(r2));
    }

    @Test
    void testIncorrectPostReviews() {
        Review r1 = new Review("badAlbum", "Contort", "bad", 2);
        Review r2 = new Review("goodAlbum", "Eva", "great", 4);
        String expectedErrorMessage = "This review already exists";

        Exception ex1 = assertThrows(UnsuccessfulReviewException.class, (()-> service.postReview(r1)));
        assertEquals(expectedErrorMessage, ex1.getMessage());
        Exception ex2 = assertThrows(UnsuccessfulReviewException.class, (()-> service.postReview(r2)));
        assertEquals(expectedErrorMessage, ex2.getMessage());
    }

    @Test
    void testGetById() {
        Review r1 = new Review("badAlbum", "Contort", "bad", 2);
        Review r1same = service.getReview(r1.getId());
        assertEquals(r1, r1same);

        Review r2 = new Review("badAlbum", "Milly", "very bad", 1);
        Review r2same = service.getReview(r2.getId());
        assertEquals(r2, r2same);
    }

    @Test
    void testReviewsByNonExistent() {
        List<Review> l1 = service.getAllReviewsBy("a7f9f9essdf");
        List<Review> l2 = service.getAllReviewsBy("Bambi");

        assertEquals(0, l1.size());
        assertEquals(0, l2.size());
    }

    @Test
    void testReviewsByExisting() {
        List<Review> l1 = service.getAllReviewsBy("Contort");
        List<Review> l2 = service.getAllReviewsBy("Milly");
        Review l2r0 = new Review(
                "badAlbum", "Milly", "very bad", 1
        );

        assertEquals(2, l1.size());
        assertEquals(1, l2.size());
        assertEquals(l2r0, l2.get(0));
    }

    @Test
    void testReviewsForNonExistent() {
        List<Review> l1 = service.getAllReviewsFor("766");
        List<Review> l2 = service.getAllReviewsFor("956");

        assertEquals(0, l1.size());
        assertEquals(0, l2.size());
    }

    @Test
    void testReviewsForExisting() {
        List<Review> l1 = service.getAllReviewsFor("badAlbum");
        List<Review> l2 = service.getAllReviewsFor("goodAlbum");
        Review l2r0 = new Review(
                "goodAlbum", "Eva", "great", 5
        );

        assertEquals(2, l1.size());
        assertEquals(1, l2.size());
        assertEquals(l2r0, l2.get(0));
    }

    @Test
    void testIncorrectVoting() {
        String r1Id = "Random_Nothing1";
        String r2Id = "Random_Nothing2";
        String expectedErrorMessage = "No such review exists";

        Exception ex1 = assertThrows(UnsuccessfulReviewException.class, (()-> service.upvoteReview(r1Id)));
        assertEquals(expectedErrorMessage, ex1.getMessage());
        Exception ex2 = assertThrows(UnsuccessfulReviewException.class, (()-> service.downvoteReview(r2Id)));
        assertEquals(expectedErrorMessage, ex2.getMessage());
    }

    @Test
    void testCorrectVoting() {
        Review r1 = new Review("0", "0", "norm", 3);
        Review r2 = new Review("4", "11", "meh", 2);
        String r1Id = r1.getId();
        String r2Id = r2.getId();

        service.postReview(r1);
        service.postReview(r2);

        assertDoesNotThrow(() -> service.upvoteReview(r1Id));
        assertDoesNotThrow(() -> service.downvoteReview(r2Id));
        assertEquals(2, r1.getPrestige());
        assertEquals(0, r2.getPrestige());
    }

    @Test
    void testPrestige() {
        assertEquals(2, service.getReviewPrestigeFor("Contort"));
        assertEquals(1, service.getReviewPrestigeFor("Milly"));
        assertEquals(1, service.getReviewPrestigeFor("Eva"));
    }

    @Test
    void testDeleteByAlbum() {
        service.deleteAllReviewsFor("amazAlbum");
        service.deleteAllReviewsFor("horrAlbum");
        assertEquals(0, service.getAllReviewsFor("amazAlbum").size());
        assertEquals(0, service.getAllReviewsBy("Damian").size());
    }

    @Test
    void testDelete() {
        Review r1 = new Review("worstAlbum", "Calina", "omg", 1);
        Review r2 = new Review("terrAlbum", "Zara", "horrendous", 1);
        service.deleteReview(r1.getId());
        service.deleteReview(r2.getId());
        assertEquals(0, service.getAllReviewsFor("terrAlbum").size());
        assertEquals(0, service.getAllReviewsBy("Calina").size());
    }

}
