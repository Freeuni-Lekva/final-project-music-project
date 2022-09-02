package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.filter.Filter;
import org.freeuni.musicforum.model.Review;

import java.sql.Connection;
import java.util.List;

public class SQLReviewDAO implements ReviewDAO {

    private final Connection con;

    public SQLReviewDAO() { con = DataSource.INSTANCE.getConnection(); }

    @Override
    public boolean addReview(Review review) {
        return false;
    }

    @Override
    public Review getById(String reviewId) {
        return null;
    }

    @Override
    public List<Review> getAllByUser(String username) {
        return null;
    }

    @Override
    public List<Review> getAllByAlbum(String albumId) {
        return null;
    }

    @Override
    public void deleteAllByAlbum(String albumId) {

    }

    @Override
    public boolean upvoteReview(String reviewId) {
        return false;
    }

    @Override
    public boolean downvoteReview(String reviewId) {
        return false;
    }

    @Override
    public boolean hasReviewFor(String username, String albumId) {
        return false;
    }

    @Override
    public List<Review> getFiltered(Filter f) {
        return null;
    }

    @Override
    public void deleteReview(String id) {

    }
}
