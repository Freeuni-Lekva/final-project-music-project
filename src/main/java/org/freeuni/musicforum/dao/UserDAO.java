package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.model.User;

public interface UserDAO {
    void add(User user);
    boolean doesExist();
}
