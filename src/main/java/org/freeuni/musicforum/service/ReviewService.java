package org.freeuni.musicforum.service;

import org.freeuni.musicforum.dao.ReviewDAO;
import org.freeuni.musicforum.exception.UnsuccessfulReviewException;
import org.freeuni.musicforum.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewService {
    private ReviewDAO dao;

    ReviewService(ReviewDAO dao) {
        this.dao = dao;
    }

    public void uploadReview(Review review) {
        if (!dao.addReview(review)) {
            throw new UnsuccessfulReviewException("This review already exists.");
        }
    }

    public List<Review> getAllReviewsBy(String username) {
        /*return new ArrayList<Review>(List.of(
             new Review("f83d", "dondo", "I think " +
                     "this album sucks big time, like actually what is wrong" +
                     "with this singer. Sis this is like the worst thing I " +
                     "have ever seen, like whaaaaaaat. kick me in the balls, what" +
                     "the hell am I listening to, this is just killing me. Delete" +
                     "this album asap, or my ears will suffer substantial permanent" +
                     "damage and I'll die from cancerosis earlosis. Well I thinks we " +
                     "shall overlook this place from now on. To never let such atrocity " +
                     "to happen again, this is a crime against humanity. I am so so so so" +
                     "so so so sad for all the African kids."), new Review("345",
                "donda", "Sis what"), new Review("Rih",
                        "dondi", "I like this album tbh"),
                new Review("adfdf", "salute", "ugly album cover"),
                new Review("DONDA", "guri", "Bro what is this"))
        );*/
        return dao.getAllByUser(username);
    }

    public int getReviewPrestigeFor(String username) {
        List<Review> revs = getAllReviewsBy(username);
        int res = 0;
        for (Review r : revs) {
            res += r.getPrestige();
        }
        return res;
    }

    public List<Review> getAllReviewsFor(String albumId) {
        return dao.getAllByAlbum(albumId);
    }

    public void upvoteReview(String reviewId) {
        if (!dao.upvoteReview(reviewId)) {
            throw new UnsuccessfulReviewException("No such review exists.");
        }
    }

    public void downvoteReview(String reviewId) {
        if (!dao.downvoteReview(reviewId)) {
            throw new UnsuccessfulReviewException("No such review exists");
        }
    }

}
