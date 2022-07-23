package org.freeuni.musicforum.service;

import org.freeuni.musicforum.dao.UserDAO;
import org.freeuni.musicforum.exception.NoSuchUserExistsException;
import org.freeuni.musicforum.exception.UnsuccessfulSignupException;
import org.freeuni.musicforum.model.FriendshipStatus;
import org.freeuni.musicforum.model.PublicUserData;
import org.freeuni.musicforum.model.User;
import org.freeuni.musicforum.util.Utils;

import java.util.Optional;

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
        return dao.correctCredentials(username, Utils.hashText(password));
    }

    public int getUserPrestige(String username) {
        ReviewService rs = ServiceFactory.getReviewService();
        int count = rs.getReviewPrestigeFor(username);
        // do the same for albums.
        return count;
    }

    public PublicUserData getProfileData(String username) {
        Optional<User> userOptional = dao.getByUsername(username);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            return new PublicUserData(user.firstName(), user.lastName(), username,
                    user.badge(), getUserPrestige(username));
        }
        throw new NoSuchUserExistsException("" +
                "User with provided username " +  username + " does not exist");
    }

    public FriendshipStatus getFriendshipStatus(String fromUsername, String toUsername){

        User u = dao.getByUsername(fromUsername).get();

        if(!u.friends().containsKey(toUsername)){
            return null;
        }

        return u.friends().get(toUsername);

    }
}
