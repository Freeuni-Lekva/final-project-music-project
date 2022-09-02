package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.model.Vote;
import org.freeuni.musicforum.model.VoteType;

import java.util.HashMap;
import java.util.Map;

public class InMemoryVotingDataDAO implements VotingDataDAO {

    Map<String, Map<String, VoteType>> votes;
    // key      -> reviewId
    // value    -> map of user-vote pairs

    public InMemoryVotingDataDAO() {
        votes = new HashMap<>();
    }

    @Override
    public void addVote(Vote vote) {
        if (votes.get(vote.reviewId()) == null) {
            votes.put(vote.reviewId(), new HashMap<>());
        } else {
            VoteType oldVote = getUserVoteFor(vote.username(), vote.reviewId());
            if (!oldVote.equals(VoteType.NONE) && !oldVote.equals(vote.voteType()))
                votes.get(vote.reviewId()).remove(vote.username());
        }
        votes.get(vote.reviewId()).put(vote.username(), vote.voteType());
    }

    @Override
    public boolean removeVote(Vote vote) {
        if (userHasVoteFor(vote.username(), vote.reviewId())) {
            votes.get(vote.reviewId()).remove(vote.username());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public VoteType getUserVoteFor(String username, String reviewId) {
        if (!userHasVoteFor(username, reviewId)) return VoteType.NONE;
        return votes.get(reviewId).get(username);
    }

    @Override
    public void deleteVotingDataFor(String reviewId) {
        votes.remove(reviewId);
    }

    private boolean userHasVoteFor(String username, String reviewId) {
        if (!votes.containsKey(reviewId)) return false;
        return votes.get(reviewId).containsKey(username);
    }

}
