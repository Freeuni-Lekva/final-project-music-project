package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.model.User;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserDAO implements UserDAO {
    private final List<User> users;

    public InMemoryUserDAO() {
        this.users = new ArrayList<>();
    }

    @Override
    public void add(User user) {
        users.add(user);
    }

    @Override
    public boolean doesExist() {
        return false;
    }
}
