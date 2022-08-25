package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.model.Review;

import java.util.List;

public interface ReviewDAO {
    boolean addReview(Review review);
    Review getById(String reviewId);
    List<Review> getAllByUser(String username);
    List<Review> getAllByAlbum(String albumId);
    void deleteAllByAlbum(String albumId);
    boolean upvoteReview(String reviewId);
    boolean downvoteReview(String reviewId);
    boolean hasReviewFor(String username, String albumId);
}
