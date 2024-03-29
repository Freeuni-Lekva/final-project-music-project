package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.filter.Filter;
import org.freeuni.musicforum.model.Review;
import org.freeuni.musicforum.model.SearchRequest;
import org.freeuni.musicforum.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryReviewDAO implements ReviewDAO {

    private final List<Review> reviews;

    public InMemoryReviewDAO() {
        this.reviews = new ArrayList<>();
        reviews.add(new Review(Utils.hashText("Body Riddle" + "Clark"),"guri", "wow",4));
        reviews.add(new Review(Utils.hashText("Body Riddle" + "Clark"),"eva", "damn",4));

        /*
        reviews.add(new Review(
                "1", "guri", "nice review", 2
        ));
        reviews.add(new Review(
                "2", "guri", "extremely nice very good review", 4
        ));
        reviews.add(new Review(
                "2", "guri", "I disliked this album, but not too bad", 3
        ));
        reviews.add(new Review(
           "3", "eva", "Good album", 4
        ));
         */
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
                .filter(review -> review.getAuthorUsername().equals(username))
                .collect(Collectors.toList());
    }

    @Override
    public List<Review> getAllByAlbum(String albumId) {
        return reviews.stream()
                .filter(review -> review.getAlbumId().equals(albumId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllByAlbum(String albumId) {
        reviews.removeIf(review -> review.getAlbumId().equals(albumId));
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

    @Override
    public boolean hasReviewFor(String username, String albumId) {
        Optional<Review> review = findReviewWrittenByUserFor(username, albumId);
        return review.isPresent();
    }

    private Optional<Review> findById(String reviewId) {
        return reviews.stream()
                .filter(review -> review.getId().equals(reviewId))
                .findFirst();
    }

    private Optional<Review> findReviewWrittenByUserFor(String username, String albumId) {
        return reviews.stream().
                filter(review -> review.getAuthorUsername().equals(username) &&
                review.getAlbumId().equals(albumId)).findFirst();
    }

    @Override
    public List<Review> getFiltered(Filter f) {
        return reviews.stream().filter(review->f.doFilter(new SearchRequest(review))).toList();
    }

    @Override
    public void deleteReview(String id) {
        reviews.removeIf(rv -> rv.getId().equals(id));
    }

}
