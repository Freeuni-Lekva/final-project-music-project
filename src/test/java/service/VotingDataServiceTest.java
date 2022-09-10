package service;

import org.freeuni.musicforum.dao.InMemoryVotingDataDAO;
import org.freeuni.musicforum.model.VoteType;
import org.freeuni.musicforum.service.VotingDataService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VotingDataServiceTest {

    private static final VotingDataService service = new VotingDataService(new InMemoryVotingDataDAO());

    @BeforeAll
    static void init() {
        // for user vote getter
        service.clickUpvote("Contort", "review_1");
        service.clickDownvote("Maria", "review_1");
        // for upvote
        service.clickDownvote("Contort", "review_2");
        service.clickUpvote("Maria", "review_3");
        service.clickUpvote("Demna", "review_3");
        // for downvote
        service.clickDownvote("Maria", "review_4");
        service.clickUpvote("Elma", "review_4");
        // for delete
        service.clickUpvote("Contort", "review_5");
        service.clickDownvote("Elma", "review_5");
    }

    @Test
    void testUserVoteGetter() {
        VoteType v1 = service.getUserVoteForReview("Contort", "review_1");
        VoteType v2 = service.getUserVoteForReview("Maria", "review_1");
        VoteType v3 = service.getUserVoteForReview("Maria", "review_2");

        assertEquals(VoteType.UPVOTE, v1);
        assertEquals(VoteType.DOWNVOTE, v2);
        assertEquals(VoteType.NONE, v3);
    }

    @Test
    void testClickUpvote() {
        service.clickUpvote("Contort", "review_2");
        service.clickUpvote("Maria", "review_3");
        service.clickUpvote("Contort", "review_3");

        VoteType v1 = service.getUserVoteForReview("Contort", "review_2");
        VoteType v2 = service.getUserVoteForReview("Maria", "review_3");
        VoteType v3 = service.getUserVoteForReview("Contort", "review_3");

        assertEquals(VoteType.UPVOTE, v1);
        assertEquals(VoteType.NONE, v2);
        assertEquals(VoteType.UPVOTE, v3);
    }

    @Test
    void testClickDownvote() {
        service.clickDownvote("Maria", "review_4");
        service.clickDownvote("Elma", "review_4");
        service.clickDownvote("Doritos", "review_4");

        VoteType v1 = service.getUserVoteForReview("Maria", "review_4");
        VoteType v2 = service.getUserVoteForReview("Elma", "review_4");
        VoteType v3 = service.getUserVoteForReview("Doritos", "review_4");

        assertEquals(VoteType.NONE, v1);
        assertEquals(VoteType.DOWNVOTE, v2);
        assertEquals(VoteType.DOWNVOTE, v3);
    }

    @Test
    void testDeleteDataForReview() {
        service.deleteAllVotingDataFor("review_5");

        assertEquals(VoteType.NONE, service.getUserVoteForReview("Contort", "review_5"));
        assertEquals(VoteType.NONE, service.getUserVoteForReview("Elma", "review_5"));
    }

}
