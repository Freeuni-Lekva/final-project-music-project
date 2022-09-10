package org.freeuni.musicforum.service;

import org.freeuni.musicforum.dao.*;

public final class ServiceFactory {
    private ServiceFactory() {}

    private static UserService userService;

    private static AlbumService albumService;

    private static ReviewService reviewService;

    private static VotingDataService votingDataService;

    private static final UserDAO USER_DAO = new SQLUserDAO();

    private static final AlbumDAO ALBUM_DAO = new SQLAlbumDAO();

    private static final ReviewDAO REVIEW_DAO = new SQLReviewDAO();

    private static final VotingDataDAO VOTING_DATA_DAO = new SQLVotingDataDAO();

    public static UserService getUserService() {
        if(userService != null) return userService;
        return userService = new UserService(USER_DAO);
    }

    public static AlbumService getAlbumService() {
        if(albumService != null) return albumService;
        return albumService = new AlbumService(ALBUM_DAO);
    }

    public static ReviewService getReviewService() {
        if (reviewService != null) return reviewService;
        return reviewService = new ReviewService(REVIEW_DAO);
    }

    public static VotingDataService getVotingDataService() {
        if (votingDataService != null) return votingDataService;
        return votingDataService = new VotingDataService(VOTING_DATA_DAO);
    }

}
