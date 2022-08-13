package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.model.FriendshipStatus;
import org.freeuni.musicforum.model.User;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class SQLUserDAO implements UserDAO {

    private final Connection con;

    public SQLUserDAO() {
        con = DataSource.INSTANCE.getConnection();
    }

    @Override
    public void add(User user) {

    }

    @Override
    public Optional<User> getByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public boolean doesExist(String username) {
        return false;
    }

    @Override
    public boolean correctCredentials(String username, String passwordHash) {
        return false;
    }

    @Override
    public List<String> getUsersByFriendshipStatus(String username, FriendshipStatus fs) {
        return null;
    }
    @Override
    public boolean updateFriendshipStatus(String fromUsername, String toUsername, FriendshipStatus fs) {
         return false;
    }

    @Override
    public boolean deleteFriendshipStatus(String fromUsername, String toUsername) {return false; }
}
