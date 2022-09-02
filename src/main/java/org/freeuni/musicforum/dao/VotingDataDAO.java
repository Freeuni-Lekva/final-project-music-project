package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.model.Vote;
import org.freeuni.musicforum.model.VoteType;

public interface VotingDataDAO {
    void addVote(Vote vote);
    boolean removeVote(Vote vote);
    VoteType getUserVoteFor(String username, String reviewId);
    void deleteVotingDataFor(String reviewId);
}
