package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.filter.Filter;
import org.freeuni.musicforum.model.User;

import java.sql.Connection;
import java.util.List;

public class SQLUserDAO implements UserDAO {

    private final Connection con;

    public SQLUserDAO() {
        con = DataSource.INSTANCE.getConnection();
    }

    @Override
    public void add(User user) {

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
    public List<User> getFiltered(Filter f){
        return null;
    }

}
