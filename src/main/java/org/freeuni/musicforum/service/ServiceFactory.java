package org.freeuni.musicforum.service;

import org.freeuni.musicforum.dao.InMemoryReviewDAO;
import org.freeuni.musicforum.dao.InMemoryUserDAO;
import org.freeuni.musicforum.dao.ReviewDAO;
import org.freeuni.musicforum.dao.UserDAO;

public final class ServiceFactory {
    private ServiceFactory() {}

    private static UserService userService;
    private static ReviewService reviewService;
    private static final UserDAO DAO = new InMemoryUserDAO(); //will be changed to sql
    private static final ReviewDAO REVIEW_DAO = new InMemoryReviewDAO();

    public static UserService getUserService() {
        if(userService != null) return userService;
        return userService = new UserService(DAO);
    }

    public static ReviewService getReviewService() {
        if (reviewService != null) return reviewService;
        return reviewService = new ReviewService(REVIEW_DAO);
    }
}
