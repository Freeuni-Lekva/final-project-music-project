package org.freeuni.musicforum.service;

import org.freeuni.musicforum.dao.VotingDataDAO;
import org.freeuni.musicforum.model.Review;
import org.freeuni.musicforum.model.Vote;
import org.freeuni.musicforum.model.VoteType;

import java.util.List;

public class VotingDataService {

    private final VotingDataDAO dao;

    public VotingDataService(VotingDataDAO dao) { this.dao = dao; }

    public VoteType getUserVoteForReview(String username, String reviewId) {
        return dao.getUserVoteFor(username, reviewId);
    }

    public void clickUpvote(String username, String reviewId) {
        VoteType vote = getUserVoteForReview(username, reviewId);
        ReviewService rs = ServiceFactory.getReviewService();
        if (vote.equals(VoteType.UPVOTE)) {
            rs.downvoteReview(reviewId);
            dao.removeVote(new Vote(username, reviewId, VoteType.UPVOTE));
        } else {
            if (vote.equals(VoteType.DOWNVOTE)) rs.upvoteReview(reviewId);
            rs.upvoteReview(reviewId);
            dao.addVote(new Vote(username, reviewId, VoteType.UPVOTE));
        }
    }

    public void clickDownvote(String username, String reviewId) {
        VoteType vote = getUserVoteForReview(username, reviewId);
        ReviewService rs = ServiceFactory.getReviewService();
        if (vote.equals(VoteType.DOWNVOTE)) {
            rs.upvoteReview(reviewId);
            dao.removeVote(new Vote(username, reviewId, VoteType.DOWNVOTE));
        } else {
            if (vote.equals(VoteType.UPVOTE)) rs.downvoteReview(reviewId);
            rs.downvoteReview(reviewId);
            dao.addVote(new Vote(username, reviewId, VoteType.DOWNVOTE));
        }
    }

    public void deleteAllVotingDataFor(String reviewId) {
        dao.deleteVotingDataFor(reviewId);
    }

    public void deleteAllVotingDataForAlbum(String albumId) {
        List<Review> rs = ServiceFactory.getReviewService().getAllReviewsFor(albumId);
        for (Review review : rs) {
            deleteAllVotingDataFor(review.getId());
        }
    }

}
