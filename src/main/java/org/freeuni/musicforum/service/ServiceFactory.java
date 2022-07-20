package org.freeuni.musicforum.service;

import org.freeuni.musicforum.dao.*;

public final class ServiceFactory {
    private ServiceFactory() {}

    private static UserService userService;

    private static AlbumService albumService;

    private static ReviewService reviewService;

    private static final UserDAO USER_DAO = new InMemoryUserDAO();

    private static final AlbumDAO ALBUM_DAO = new InMemoryAlbumDAO();

    private static final ReviewDAO REVIEW_DAO = new InMemoryReviewDAO();

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
}
