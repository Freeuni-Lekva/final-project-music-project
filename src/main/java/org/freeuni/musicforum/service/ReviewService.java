package org.freeuni.musicforum.service;

import org.freeuni.musicforum.dao.ReviewDAO;
import org.freeuni.musicforum.exception.UnsuccessfulReviewException;
import org.freeuni.musicforum.filter.Filter;
import org.freeuni.musicforum.model.Review;

import java.util.List;

public class ReviewService {
    private final ReviewDAO dao;

    ReviewService(ReviewDAO dao) {
        this.dao = dao;
    }

    public void postReview(Review review) {
        if (!dao.addReview(review)) {
            throw new UnsuccessfulReviewException("This review already exists");
        }
    }

    public List<Review> getAllReviewsBy(String username) {
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

    public void deleteAllReviewsFor(String albumId) {
        dao.deleteAllByAlbum(albumId);
    }

    public boolean userHasReviewForAlbum(String username, String albumId) { return dao.hasReviewFor(username, albumId); }

    public void upvoteReview(String reviewId) {
        if (!dao.upvoteReview(reviewId)) {
            throw new UnsuccessfulReviewException("No such review exists");
        }
    }

    public void downvoteReview(String reviewId) {
        if (!dao.downvoteReview(reviewId)) {
            throw new UnsuccessfulReviewException("No such review exists");
        }
    }

    public List<Review> filter(Filter f){ return dao.getFiltered(f); }

}
