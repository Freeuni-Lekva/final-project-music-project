package org.freeuni.musicforum.service;

import org.freeuni.musicforum.dao.UserDAO;
import org.freeuni.musicforum.exception.NoSuchUserExistsException;
import org.freeuni.musicforum.exception.UnsuccessfulSignupException;
import org.freeuni.musicforum.model.FriendshipStatus;
import org.freeuni.musicforum.model.PublicUserData;
import org.freeuni.musicforum.model.User;
import org.freeuni.musicforum.util.Utils;

import java.util.List;
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
        AlbumService as = ServiceFactory.getAlbumService();
        count += as.getAlbumPrestigeFor(username);
        return count;
    }

    public PublicUserData getProfileData(String username) {
        Optional<User> userOptional = dao.getByUsername(username);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            return new PublicUserData(user.firstName(), user.lastName(), username, user.profileImageBase64(),
                    user.badge(), getUserPrestige(username));
        }
        throw new NoSuchUserExistsException("" +
                "User with provided username " +  username + " does not exist");
    }


    public FriendshipStatus getFriendshipStatus(String fromUsername, String toUsername){
        User user = getUserIfExists(fromUsername);
        if(!dao.doesExist(toUsername)){
            throw new NoSuchUserExistsException("" +
                    "User with provided username " +  toUsername + " does not exist");
        }
        if (!user.friends().containsKey(toUsername)) {
            return null;
        }
        return user.friends().get(toUsername);
    }

    public void sendFriendRequest(String fromUsername, String toUsername){
        FriendshipStatus currentStatus = getFriendshipStatus(fromUsername, toUsername);
        if(currentStatus==null){
            dao.updateFriendshipStatus(fromUsername, toUsername, FriendshipStatus.REQUEST_SENT);
            dao.updateFriendshipStatus(toUsername, fromUsername, FriendshipStatus.ACCEPT_REQUEST);
        }
    }

    public void acceptFriendRequest(String fromUsername, String toUsername){
        FriendshipStatus currentStatus = getFriendshipStatus(fromUsername, toUsername);
        if(currentStatus==FriendshipStatus.ACCEPT_REQUEST){
            dao.updateFriendshipStatus(fromUsername, toUsername, FriendshipStatus.FRIENDS);
            dao.updateFriendshipStatus(toUsername, fromUsername, FriendshipStatus.FRIENDS);
        }
    }

    public void removeFriendshipStatus(String firstUsername, String secondUsername){
        FriendshipStatus currentStatus = getFriendshipStatus(firstUsername, secondUsername);
        if(currentStatus==FriendshipStatus.FRIENDS){
            dao.deleteFriendshipStatus(firstUsername, secondUsername);
            dao.deleteFriendshipStatus(secondUsername, firstUsername);
        }
    }

    public List<PublicUserData> getUsersFriends(String username){
        List<String> friends = dao.getUsersByFriendshipStatus(username, FriendshipStatus.FRIENDS);
        if(friends==null){
            throw new NoSuchUserExistsException("" +
                    "User with provided username " +  username + " does not exist");
        }
        return friends.stream().map(currUsername->getProfileData(currUsername)).toList();
    }

    public List<PublicUserData> getUsersFriendRequests(String username){
        List<String> friendRequests = dao.getUsersByFriendshipStatus(username, FriendshipStatus.ACCEPT_REQUEST);
        if(friendRequests==null){
            throw new NoSuchUserExistsException("" +
                    "User with provided username " +  username + " does not exist");
        }
        return friendRequests.stream().map(currUsername->getProfileData(currUsername)).toList();
    }

    private User getUserIfExists(String username){
        Optional<User>  userOptional = dao.getByUsername(username);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new NoSuchUserExistsException("" +
                "User with provided username " +  username + " does not exist");
    }

}
