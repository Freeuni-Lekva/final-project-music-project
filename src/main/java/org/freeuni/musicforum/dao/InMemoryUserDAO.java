package org.freeuni.musicforum.dao;

import org.freeuni.musicforum.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUserDAO implements UserDAO {
    private final List<User> users;

    public InMemoryUserDAO() {
        this.users = new ArrayList<>(List.of(
                new User("guri", "getsadze", null, Gender.MAN,
                        "guri", new Password("guri"), Badge.NEWCOMER),
                new User("evangelina", "smith", null, Gender.WOMAN,
                        "eva", new Password("2000"), Badge.NEWCOMER),
                new User("melanie", "dorkus", null, Gender.WOMAN,
                        "melanie1996", new Password("A_B_C*"), Badge.NEWCOMER),
                new User("ushi", "hagayana", null, Gender.OTHER,
                        "u#700", new Password("ushi"), Badge.NEWCOMER)));

        users.get(0).friends().put("eva", FriendshipStatus.Pending);
        System.out.println("status" + FriendshipStatus.Pending);
    }

    @Override
    public void add(User user) {
        users.add(user);
    }

    @Override
    public boolean doesExist(String username) {
        return getByUsername(username).isPresent();
    }

    @Override
    public Optional<User> getByUsername(String username) {
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
