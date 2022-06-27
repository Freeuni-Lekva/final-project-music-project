package org.freeuni.musicforum.service;

import org.freeuni.musicforum.dao.UserDAO;
import org.freeuni.musicforum.exception.UnsuccessfulSignupException;
import org.freeuni.musicforum.model.User;
import org.freeuni.musicforum.util.UserUtils;

public class UserService {
    private final UserDAO dao;

    UserService(UserDAO dao) {
        this.dao = dao;
    }

    public void signUp(User newUser) {
        if(dao.doesExist(newUser.username()))
            throw new UnsuccessfulSignupException("User with this username already exists");

        if(newUser.username().length() < 3 || newUser.password().getPasswordSize() < 3) {
            throw new UnsuccessfulSignupException("Username/password must be longer than 2 characters");
        }
        dao.add(newUser);
    }

    public boolean login(String username, String password) {
        return dao.correctCredentials(username, UserUtils.hashPassword(password));
    }
}
