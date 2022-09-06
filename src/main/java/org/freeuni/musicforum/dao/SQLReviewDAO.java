package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.filter.Filter;
import org.freeuni.musicforum.model.Review;
import org.freeuni.musicforum.model.SearchRequest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLReviewDAO implements ReviewDAO {

    private final Connection con;

    public SQLReviewDAO() { con = DataSource.INSTANCE.getConnection(); }

    @Override
    public boolean addReview(Review review) {

        try {
            Statement checkExistStm = con.createStatement();
            ResultSet rs = checkExistStm.executeQuery(
                    "SELECT id FROM reviews WHERE id='" +
                    review.getId() + "';");
            if (!rs.next()) return false;
            checkExistStm.close();

            Statement insertStm = con.createStatement();
            insertStm.executeUpdate("INSERT INTO reviews" +
                    "(id, review_text, album_id, username, stars, " +
                    "prestige, upload_date) VALUES ('" + review.getId() +
                    "', '" + review.getText() + "', '" + review.getAlbumId() +
                    "', '" + review.getAuthorUsername() + "', " +
                    review.getStarCount() + ", " + review.getPrestige() +
                    ", " + new java.sql.Date(review.getUploadDate().getTime()) + ");");
            insertStm.close();
        } catch(SQLException exc) {
            exc.printStackTrace();
        }
        return true;

    }

    @Override
    public Review getById(String reviewId) {

        try {
            Statement getStm = con.createStatement();
            ResultSet rs = getStm.executeQuery("SELECT *" +
                    "FROM reviews WHERE id='" + reviewId + "';");
            if (rs.next()) {
                Review rev = makeReview(rs);
                getStm.close();
                return rev;
            }
        } catch(SQLException exc) {
            exc.printStackTrace();
        }
        return null;

    }

    @Override
    public List<Review> getAllByUser(String username) {

        try {
            Statement getStm = con.createStatement();
            ResultSet rs = getStm.executeQuery("SELECT *" +
                    "FROM reviews WHERE username='" + username + "';");
            List<Review> list = makeList(rs);
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
            Statement getStm = con.createStatement();
            ResultSet rs = getStm.executeQuery("SELECT *" +
                    "FROM reviews WHERE album_id='" + albumId + "';");
            List<Review> list = makeList(rs);
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
            Statement deleteStm = con.createStatement();
            deleteStm.executeUpdate("DELETE FROM reviews WHERE album_id='" +
                    albumId + "';");
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

        try {
            Statement getStm = con.createStatement();
            ResultSet rs = getStm.executeQuery("SELECT *" +
                    "FROM reviews WHERE username='" + username + "' AND " +
                    "album_id='" + albumId + "';");
            if (rs.next()) {
                Review rev = makeReview(rs);
                getStm.close();
                if (rev != null) return true;
            }
        } catch(SQLException exc) {
            exc.printStackTrace();
        }
        return false;

    }

    @Override
    public List<Review> getFiltered(Filter f) {

        try {
            Statement getStm = con.createStatement();
            ResultSet rs = getStm.executeQuery("SELECT *" +
                    "FROM reviews;");
            List<Review> list = makeList(rs);
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
            Statement deleteStm = con.createStatement();
            deleteStm.executeUpdate("DELETE FROM reviews WHERE id='" +
                    id + "';");
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
            Statement getStm = con.createStatement();
            ResultSet rs = getStm.executeQuery("SELECT prestige FROM" +
                    " reviews WHERE id='" + reviewId + "';");
            int prestige;
            if (rs.next()) {
                prestige = rs.getInt(1);
            } else {
                return false;
            }
            getStm.close();

            if (vote) {
                prestige++;
            } else {
                prestige--;
            }

            Statement updateStm = con.createStatement();
            updateStm.executeUpdate("UPDATE reviews SET prestige=" +
                    prestige + " WHERE id='" + reviewId + "';");
            updateStm.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return true;

    }

}
