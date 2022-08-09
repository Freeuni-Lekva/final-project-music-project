package org.freeuni.musicforum.service;

import org.freeuni.musicforum.dao.UserDAO;
import org.freeuni.musicforum.exception.NoSuchUserExistsException;
import org.freeuni.musicforum.exception.UnsuccessfulSignupException;
import org.freeuni.musicforum.model.FriendshipStatus;
import org.freeuni.musicforum.model.PublicUserData;
import org.freeuni.musicforum.model.User;
import org.freeuni.musicforum.util.Utils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

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
        if(!dao.updateFriendshipStatus(fromUsername, toUsername, FriendshipStatus.REQUEST_SENT)){
            getUserIfExists(fromUsername);
            getUserIfExists(toUsername);
        }
        dao.updateFriendshipStatus(toUsername, fromUsername, FriendshipStatus.ACCEPT_REQUEST);
    }

    public void acceptFriendRequest(String fromUsername, String toUsername){
        if(!dao.updateFriendshipStatus(fromUsername, toUsername, FriendshipStatus.FRIENDS)){
            getUserIfExists(fromUsername);
            getUserIfExists(toUsername);
        }
        dao.updateFriendshipStatus(toUsername, fromUsername, FriendshipStatus.FRIENDS);
    }

    public void deleteFriend(String firstUsername, String secondUsername){
        if(!dao.deleteFriendshipStatus(firstUsername, secondUsername)){
            getUserIfExists(firstUsername);
            getUserIfExists(secondUsername);
        }
        dao.deleteFriendshipStatus(secondUsername, firstUsername);
    }

    public List<PublicUserData> getUsersFriends(String username){
        User user = getUserIfExists(username);
        Stream<PublicUserData> friends = user.friends().entrySet().stream().filter(entry->{
            if(entry.getValue().equals(FriendshipStatus.FRIENDS)) return true;
            return false;
        }).map(entry->getProfileData(entry.getKey()));
        return friends.toList();
    }

    public List<PublicUserData> getUsersFriendRequests(String username){
        User user = getUserIfExists(username);
        Stream<PublicUserData> friendRequests = user.friends().entrySet().stream().filter(entry->{
           if(entry.getValue().equals(FriendshipStatus.ACCEPT_REQUEST)) return true;
           return false;
        }).map(entry->getProfileData(entry.getKey()));
        return friendRequests.toList();
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
