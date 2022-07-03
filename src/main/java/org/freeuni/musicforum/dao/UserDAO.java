package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.filter.Filter;
import org.freeuni.musicforum.model.User;

import java.util.List;

public interface UserDAO {
    void add(User user);
    boolean doesExist(String username);
    boolean correctCredentials(String username, String passwordHash);
    List<User> getFiltered(Filter f);
}
