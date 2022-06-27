package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.model.Badge;
import org.freeuni.musicforum.model.Gender;
import org.freeuni.musicforum.model.Password;
import org.freeuni.musicforum.model.User;
import org.freeuni.musicforum.util.UserUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUserDAO implements UserDAO {
    private final List<User> users;

    public InMemoryUserDAO() {
        this.users = new ArrayList<>(List.of(
                new User("guri", "getsadze", null, Gender.MAN,
                        "guri", new Password("guri"), Badge.NEWCOMER)));
    }

    @Override
    public void add(User user) {
        users.add(user);
    }

    @Override
    public boolean doesExist(String username) {
        return getByUsername(username).isPresent();
    }

    private Optional<User> getByUsername(String username) {
        return users.stream()
                .filter(user -> user.username().equals(username))
                .findFirst();
    }

    @Override
    public boolean correctCredentials(String username, String passwordHash) {
        Optional<User> user = getByUsername(username);

        return user.isPresent() &&
                user.get().password().hashed().equals(passwordHash);
    }
}
