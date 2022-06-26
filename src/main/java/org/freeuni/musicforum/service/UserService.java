package org.freeuni.musicforum.service;

import org.freeuni.musicforum.dao.UserDAO;
import org.freeuni.musicforum.exception.UserAlreadyExistsException;
import org.freeuni.musicforum.model.User;

public class UserService {
    private final UserDAO dao;

    public UserService(UserDAO dao) {
        this.dao = dao;
    }

    public void signUp(User newUser) {
        if(dao.doesExist(newUser.username())) throw new UserAlreadyExistsException();

        dao.add(newUser);
    }

    public boolean login(String username, String passwordHash) {
        return dao.correctCredentials(username, passwordHash);
    }
}
