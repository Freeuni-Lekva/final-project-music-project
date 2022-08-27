package service;

import org.freeuni.musicforum.exception.UnsuccessfulReviewException;
import org.freeuni.musicforum.model.Review;
import org.freeuni.musicforum.service.ServiceFactory;
import org.freeuni.musicforum.service.ReviewService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewServiceTest {

    private final ReviewService service = ServiceFactory.getReviewService();

    @Test
    void testPostReviews() {
        Review r1 = new Review("706456", "Nikifore", "Wow", 5);
        Review r2 = new Review("543563", "Lome", "Love it", 4);

        assertDoesNotThrow(() -> service.postReview(r1));
        assertDoesNotThrow(() -> service.postReview(r2));
    }

    @Test
    void testIncorrectPostReviews() {
        Review r1 = new Review("1", "Contort", "nice review", 2);
        Review r2 = new Review("3", "Lendrick", "Good album", 4);
        String expectedErrorMessage = "This review already exists";

        Exception ex1 = assertThrows(UnsuccessfulReviewException.class, (()-> service.postReview(r1)));
        assertEquals(expectedErrorMessage, ex1.getMessage());
        Exception ex2 = assertThrows(UnsuccessfulReviewException.class, (()-> service.postReview(r2)));
        assertEquals(expectedErrorMessage, ex2.getMessage());
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
        List<Review> l2 = service.getAllReviewsBy("Mandy");
        Review l2r0 = new Review(
                "2", "Mandy", "I disliked this album, but not too bad", 3
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
        List<Review> l1 = service.getAllReviewsFor("2");
        List<Review> l2 = service.getAllReviewsFor("3");
        Review l2r0 = new Review(
                "3", "Lendrick", "Good album", 4
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
        assertEquals(1, service.getReviewPrestigeFor("Lendrick"));
        assertEquals(1, service.getReviewPrestigeFor("Mandy"));
    }

}