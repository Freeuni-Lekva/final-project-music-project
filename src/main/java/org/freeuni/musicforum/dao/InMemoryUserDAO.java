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
                        "guri", new Password("guri"), new Badge(Badge.BadgeEnum.ADMINISTRATOR)),
                new User("evangelina", "smith", null, Gender.WOMAN,
                        "eva", new Password("2000"), new Badge(Badge.BadgeEnum.NEWCOMER)),
                new User("melanie", "dorkus", null, Gender.WOMAN,
                        "melanie1996", new Password("A_B_C*"), new Badge(Badge.BadgeEnum.NEWCOMER)),
                new User("ushi", "hagayana", null, Gender.OTHER,
                        "u#700", new Password("ushi"), new Badge(Badge.BadgeEnum.NEWCOMER))));


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

    @Override
    public void updateBadgeAccordingTo(String username, int prestige) {
        Optional<User> user = getByUsername(username);

        if (user.isPresent()) user.get().badge().modifyAccordingTo(prestige);
    }

    @Override
    public void delete(String username) {
        Optional<User> user = getByUsername(username);

        if (user.isPresent()) users.remove(user.get());
    }

    @Override
    public List<String> getUsersByFriendshipStatus(String username, FriendshipStatus fs) {
        Optional<User> user = getByUsername(username);
        if(user.isPresent()){
            return user.get().friends().entrySet().stream().filter(entry->{
                if(entry.getValue().equals(fs)) return true;
                return false;
            }).map(entry->entry.getKey()).toList();
        }
        return null;
    }

    @Override
    public boolean updateFriendshipStatus(String fromUsername, String toUsername, FriendshipStatus fs) {
        Optional<User> u = getByUsername(fromUsername);
        if(u.isPresent()&&doesExist(toUsername)){
            u.get().friends().put(toUsername, fs);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteFriendshipStatus(String fromUsername, String toUsername) {
        Optional<User> user = getByUsername(fromUsername);
        if(user.isPresent()&&doesExist(toUsername)){
            user.get().friends().remove(toUsername);
            return true;
        }
        return false;
    }
}
