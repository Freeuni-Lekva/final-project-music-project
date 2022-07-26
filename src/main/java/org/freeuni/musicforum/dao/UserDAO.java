package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.model.FriendshipStatus;
import org.freeuni.musicforum.model.User;

import java.util.Optional;

public interface UserDAO {
    void add(User user);
    Optional<User> getByUsername(String username);
    boolean doesExist(String username);
    boolean correctCredentials(String username, String passwordHash);

    // returns true only if both users exist and update happened
    boolean updateFriendshipStatus(String fromUsername, String toUsername, FriendshipStatus fs);

}
