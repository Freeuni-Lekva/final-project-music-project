package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.model.User;

import java.sql.Connection;

public class SQLUserDAO implements UserDAO {

    private final Connection con;

    public SQLUserDAO() {
        con = DataSource.INSTANCE.getConnection();
    }

    @Override
    public void add(User user) {

    }

    @Override
    public boolean doesExist() {
        return false;
    }
}
