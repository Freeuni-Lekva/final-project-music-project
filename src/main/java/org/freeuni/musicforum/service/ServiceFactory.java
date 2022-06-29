package org.freeuni.musicforum.service;

import org.freeuni.musicforum.dao.InMemoryUserDAO;
import org.freeuni.musicforum.dao.UserDAO;

public final class ServiceFactory {
    private ServiceFactory() {}

    private static UserService userService;
    private static final UserDAO DAO = new InMemoryUserDAO(); //will be changed to sql

    public static UserService getUserService() {
        if(userService != null) return userService;
        return userService = new UserService(DAO);
    }
}
