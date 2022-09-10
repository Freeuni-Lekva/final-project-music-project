package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.filter.Filter;
import org.freeuni.musicforum.model.Review;
import org.freeuni.musicforum.model.SearchRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLReviewDAO implements ReviewDAO {

    private final Connection con;

    public SQLReviewDAO() { con = DataSource.INSTANCE.getConnection(); }

    @Override
    public boolean addReview(Review review) {

        try {
            PreparedStatement checkExistStm = con.prepareStatement("" +
                    "SELECT id FROM reviews WHERE id = ?;");
            checkExistStm.setString(1, review.getId());
            ResultSet rs = checkExistStm.executeQuery();
            if (rs.next()) return false;
            rs.close();
            checkExistStm.close();

            PreparedStatement insertStm = con.prepareStatement("INSERT INTO reviews" +
                    "(id, review_text, album_id, username, stars, prestige, upload_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?);");
            insertStm.setString(1, review.getId());
            insertStm.setString(2, review.getText());
            insertStm.setString(3, review.getAlbumId());
            insertStm.setString(4, review.getAuthorUsername());
            insertStm.setInt(5, review.getStarCount());
            insertStm.setInt(6, review.getPrestige());
            insertStm.setDate(7, new java.sql.Date(review.getUploadDate().getTime()));
            insertStm.executeUpdate();
            insertStm.close();
        } catch(SQLException exc) {
            exc.printStackTrace();
        }
        return true;

    }

    @Override
    public Review getById(String reviewId) {

        try {
            PreparedStatement getStm = con.prepareStatement("SELECT * " +
                    "FROM reviews WHERE id = ?;");
            getStm.setString(1, reviewId);
            ResultSet rs = getStm.executeQuery();
            if (rs.next()) {
                Review rev = makeReview(rs);
                rs.close();
                getStm.close();
                return rev;
            }
            rs.close();
            getStm.close();
        } catch(SQLException exc) {
            exc.printStackTrace();
        }
        return null;

    }

    @Override
    public List<Review> getAllByUser(String username) {

        try {
            PreparedStatement getStm = con.prepareStatement("SELECT * " +
                    "FROM reviews WHERE username = ?;");
            getStm.setString(1, username);
            ResultSet rs = getStm.executeQuery();
            List<Review> list = makeList(rs);
            rs.close();
            getStm.close();
            return list;
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return null;

    }

    @Override
    public List<Review> getAllByAlbum(String albumId) {

        try {
            PreparedStatement getStm = con.prepareStatement("SELECT * " +
                    "FROM reviews WHERE album_id = ?;");
            getStm.setString(1, albumId);
            ResultSet rs = getStm.executeQuery();
            List<Review> list = makeList(rs);
            rs.close();
            getStm.close();
            return list;
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return null;

    }

    @Override
    public void deleteAllByAlbum(String albumId) {

        try {
            PreparedStatement deleteStm = con.prepareStatement("DELETE FROM reviews " +
                    "WHERE album_id = ?;");
            deleteStm.setString(1, albumId);
            deleteStm.executeUpdate();
            deleteStm.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }

    }

    @Override
    public boolean upvoteReview(String reviewId) {

        return vote(reviewId, true);

    }

    @Override
    public boolean downvoteReview(String reviewId) {

        return vote(reviewId, false);

    }

    @Override
    public boolean hasReviewFor(String username, String albumId) {

        boolean res = false;
        try {
            PreparedStatement getStm = con.prepareStatement("SELECT * " +
                    "FROM reviews WHERE username= ? AND album_id = ?;");
            getStm.setString(1, username);
            getStm.setString(2, albumId);
            ResultSet rs = getStm.executeQuery();
            if (rs.next()) {
                Review rev = makeReview(rs);
                if (rev != null) res = true;
            }
            rs.close();
            getStm.close();
        } catch(SQLException exc) {
            exc.printStackTrace();
        }
        return res;

    }

    @Override
    public List<Review> getFiltered(Filter f) {

        try {
            Statement getStm = con.createStatement();
            ResultSet rs = getStm.executeQuery("SELECT * " +
                    "FROM reviews;");
            List<Review> list = makeList(rs);
            rs.close();
            getStm.close();
            return list.stream().filter(review->
                    f.doFilter(new SearchRequest(review))).toList();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return null;

    }

    @Override
    public void deleteReview(String id) {

        try {
            PreparedStatement deleteStm = con.prepareStatement("DELETE FROM reviews " +
                    "WHERE id = ?;");
            deleteStm.setString(1, id);
            deleteStm.executeUpdate();
            deleteStm.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }

    }

    private List<Review> makeList(ResultSet rs) {

        List<Review> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(makeReview(rs));
            }
        } catch(SQLException exc) {
            exc.printStackTrace();
        }
        return list;

    }

    private Review makeReview(ResultSet rs) {

        try {
            Review rev = new Review(rs.getString(3), rs.getString(4),
                    rs.getString(2), rs.getInt(5), rs.getInt(6),
                    rs.getDate(7));
            return rev;
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return null;

    }

    private boolean vote(String reviewId, boolean vote) {

        try {
            PreparedStatement getStm = con.prepareStatement("SELECT prestige FROM " +
                    "reviews WHERE id = ?;");
            getStm.setString(1, reviewId);
            ResultSet rs = getStm.executeQuery();
            int prestige;
            if (rs.next()) {
                prestige = rs.getInt(1);
            } else {
                rs.close();
                getStm.close();
                return false;
            }
            rs.close();
            getStm.close();

            if (vote) {
                prestige++;
            } else {
                prestige--;
            }

            PreparedStatement updateStm = con.prepareStatement("UPDATE reviews SET prestige = ? " +
                    "WHERE id = ?");
            updateStm.setInt(1, prestige);
            updateStm.setString(2, reviewId);
            updateStm.executeUpdate();
            updateStm.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return true;

    }

}
