package org.freeuni.musicforum.service;

import org.freeuni.musicforum.dao.AlbumDAO;
import org.freeuni.musicforum.dao.InMemoryAlbumDAO;
import org.freeuni.musicforum.dao.InMemoryUserDAO;
import org.freeuni.musicforum.dao.UserDAO;

public final class ServiceFactory {
    private ServiceFactory() {}

    private static UserService userService;
    private static AlbumService albumService;
    private static final UserDAO userDAO = new InMemoryUserDAO(); //will be changed to sql
    private static final AlbumDAO albumDAO = new InMemoryAlbumDAO(); // this too

    public static UserService getUserService() {
        if(userService != null) return userService;
        return userService = new UserService(userDAO);
    }

    public static AlbumService getAlbumService() {
        if(albumService != null) return albumService;
        return albumService = new AlbumService(albumDAO);
    }
}
