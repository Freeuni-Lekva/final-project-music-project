package org.freeuni.musicforum.service;

import org.freeuni.musicforum.dao.UserDAO;
import org.freeuni.musicforum.exception.UnsuccessfulSignupException;
import org.freeuni.musicforum.model.User;
import org.freeuni.musicforum.util.UserUtils;

public class UserService {
    private final UserDAO dao;

    public UserService(UserDAO dao) {
        this.dao = dao;
    }

    public void signUp(User newUser) {
        if(dao.doesExist(newUser.username()))
            throw new UnsuccessfulSignupException("User with this username already exists");
        // passwordHash is misleading, it is just password.
        if(newUser.username().length() < 3 || newUser.passwordHash().length() < 3) {
            throw new UnsuccessfulSignupException("Username/Password must be longer than 2 characters");
        }
        User secureUser = new User(newUser.firstName(), newUser.lastName(),
                newUser.birthDate(), newUser.gender(), newUser.username(),
                UserUtils.hashPassword(newUser.passwordHash()), newUser.badge());
        dao.add(secureUser);
    }

    public boolean login(String username, String passwordHash) {
        return dao.correctCredentials(username, passwordHash);
    }
}
