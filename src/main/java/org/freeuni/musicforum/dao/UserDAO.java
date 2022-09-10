
package org.freeuni.musicforum.dao;


import org.freeuni.musicforum.model.FriendshipStatus;
import org.freeuni.musicforum.model.User;

import java.util.List;
import java.util.Optional;

import org.freeuni.musicforum.filter.Filter;


public interface UserDAO {
    void add(User user);
    Optional<User> getByUsername(String username);
    boolean doesExist(String username);
    boolean correctCredentials(String username, String passwordHash);
    void updateBadgeAccordingTo(String username, int prestige);
    void delete(String username);
    void banUser(String username);
    FriendshipStatus getFriendshipStatus(String fromUsername, String toUsername);
    List<String> getUsersByFriendshipStatus(String username, FriendshipStatus fs);
    boolean updateFriendshipStatus(String fromUsername, String toUsername, FriendshipStatus fs);
    boolean deleteFriendshipStatus(String fromUsername, String toUsername);
    void setProfileImageBase64(String username,String base64String);
    List<User> getFiltered(Filter f);
}
