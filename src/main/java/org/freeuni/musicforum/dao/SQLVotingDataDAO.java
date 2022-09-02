package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.model.Vote;
import org.freeuni.musicforum.model.VoteType;

import java.sql.Connection;

public class SQLVotingDataDAO implements VotingDataDAO {
    private final Connection con;

    public SQLVotingDataDAO() { con = DataSource.INSTANCE.getConnection(); }

    @Override
    public void addVote(Vote vote) {
    }

    @Override
    public boolean removeVote(Vote vote) {
        return false;
    }

    @Override
    public VoteType getUserVoteFor(String username, String reviewId) {
        return null;
    }

    @Override
    public void deleteVotingDataFor(String reviewId) {

    }
}
