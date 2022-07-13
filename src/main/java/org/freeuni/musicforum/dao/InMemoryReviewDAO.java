package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.model.Review;
import org.freeuni.musicforum.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryReviewDAO implements ReviewDAO {

    private final List<Review> reviews;

    public InMemoryReviewDAO() {
        this.reviews = new ArrayList<>();
        reviews.add(new Review(
                "1", "Vader", "nice review"
        ));
        reviews.add(new Review(
                "2", "Vader", "extremely nice very good review"
        ));
    }

    @Override
    public boolean addReview(Review review) {
        if (getById(review.getId()) != null) return false;
        reviews.add(review);
        return true;
    }

    @Override
    public Review getById(String reviewId) {
        Optional<Review> review = findById(reviewId);
        return review.orElse(null);
    }

    @Override
    public List<Review> getAllByUser(String username) {
        return reviews.stream()
                .filter(review -> review.getUsername().equals(username))
                .collect(Collectors.toList());
    }

    @Override
    public List<Review> getAllByAlbum(String albumId) {
        return reviews.stream()
                .filter(review -> review.getAlbumId().equals(albumId))
                .collect(Collectors.toList());
    }

    @Override
    public boolean upvoteReview(String reviewId) {
        Review review = getById(reviewId);
        if (review == null) return false;
        review.upvote();
        return true;
    }

    @Override
    public boolean downvoteReview(String reviewId) {
        Review review = getById(reviewId);
        if (review == null) return false;
        review.downvote();
        return true;
    }

    private Optional<Review> findById(String reviewId) {
        return reviews.stream()
                .filter(review -> review.getId().equals(reviewId))
                .findFirst();
    }
}