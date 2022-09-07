package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.model.Vote;
import org.freeuni.musicforum.model.VoteType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLVotingDataDAO implements VotingDataDAO {
    private final Connection con;

    public SQLVotingDataDAO() { con = DataSource.INSTANCE.getConnection(); }

    @Override
    public void addVote(Vote vote) {

        try {
            PreparedStatement getVoteStm = con.prepareStatement("SELECT vote " +
                    "FROM voting_data WHERE review_id = ? AND username = ?;");
            getVoteStm.setString(1, vote.reviewId());
            getVoteStm.setString(2, vote.username());
            ResultSet rs = getVoteStm.executeQuery();
            if (rs.next()) {
                int prevVote = rs.getInt(1);
                if (prevVote != 0 && prevVote != vote.voteType().ordinal() - 1) {
                    removeVote(vote);
                }
            } else {
                PreparedStatement addVoteStm = con.prepareStatement("INSERT INTO " +
                        "voting_data(username, review_id, vote) VALUES(?, ?, ?);");
                addVoteStm.setString(1, vote.username());
                addVoteStm.setString(2, vote.reviewId());
                addVoteStm.setInt(3, vote.voteType().ordinal() - 1);
                addVoteStm.executeUpdate();
                addVoteStm.close();
            }
            rs.close();
            getVoteStm.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }

    }

    @Override
    public boolean removeVote(Vote vote) {

        try {
            PreparedStatement getVoteStm = con.prepareStatement("SELECT vote " +
                    "FROM voting_data WHERE review_id = ? AND username = ?;");
            getVoteStm.setString(1, vote.reviewId());
            getVoteStm.setString(2, vote.username());
            ResultSet rs = getVoteStm.executeQuery();
            if (rs.next()) {
                PreparedStatement deleteStm = con.prepareStatement("DELETE FROM voting_data " +
                        "WHERE username = ? AND review_id = ?;");
                deleteStm.setString(1, vote.username());
                deleteStm.setString(2, vote.reviewId());
                deleteStm.executeUpdate();
                deleteStm.close();
            } else {
                getVoteStm.close();
                return false;
            }
            rs.close();
            getVoteStm.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return true;

    }

    @Override
    public VoteType getUserVoteFor(String username, String reviewId) {

        VoteType res = VoteType.NONE;
        try {
            PreparedStatement getVoteStm = con.prepareStatement("SELECT vote " +
                    "FROM voting_data WHERE review_id = ? AND username = ?;");
            getVoteStm.setString(1, reviewId);
            getVoteStm.setString(2, username);
            ResultSet rs = getVoteStm.executeQuery();
            if (rs.next()) {
                int vote = rs.getInt(1);
                if (vote == -1) {
                    res = VoteType.DOWNVOTE;
                } else if (vote == 1) {
                    res = VoteType.UPVOTE;
                }
            }
            rs.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return res;

    }

    @Override
    public void deleteVotingDataFor(String reviewId) {

        try {
            PreparedStatement deleteStm = con.prepareStatement("DELETE FROM voting_data " +
                    "WHERE review_id = ?;");
            deleteStm.setString(1, reviewId);
            deleteStm.executeUpdate();
            deleteStm.close();
        } catch(Exception exc) {
            exc.printStackTrace();
        }

    }

}
