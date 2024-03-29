package org.freeuni.musicforum.dao;


import org.freeuni.musicforum.filter.Filter;
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
                        "melanie1996", new Password("A_B_C*"), new Badge(Badge.BadgeEnum.NEWCOMER))));

        users.get(0).getFriends().put("eva", FriendshipStatus.FRIENDS);
        users.get(1).getFriends().put("guri", FriendshipStatus.FRIENDS);

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
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public boolean correctCredentials(String username, String passwordHash) {
        Optional<User> user = getByUsername(username);
        return user.isPresent() &&
                user.get().getPassword().hashed().equals(passwordHash)&&
                user.get().getStatus().equals(Status.ACTIVE);
    }

    @Override
    public void updateBadgeAccordingTo(String username, int prestige) {
        Optional<User> user = getByUsername(username);
        if (user.isPresent()) user.get().getBadge().modifyAccordingTo(prestige);
    }

    @Override
    public void delete(String username) {
        Optional<User> user = getByUsername(username);
        if (user.isPresent()) users.remove(user.get());
    }

    @Override
    public void banUser(String username) {
        Optional<User> user = getByUsername(username);
        if(user.isPresent()) user.get().setStatus(Status.BANNED);
    }

    @Override
    public FriendshipStatus getFriendshipStatus(String fromUsername, String toUsername) {
        Optional<User> user = getByUsername(fromUsername);

        if(user.isPresent()){
            return user.get().getFriends().get(toUsername);
        }
        return null;

    }

    @Override
    public List<String> getUsersByFriendshipStatus(String username, FriendshipStatus fs) {
        Optional<User> user = getByUsername(username);
        if(user.isPresent()){
            return user.get().getFriends().entrySet().stream().filter(entry->{
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
            u.get().getFriends().put(toUsername, fs);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteFriendshipStatus(String fromUsername, String toUsername) {
        Optional<User> user = getByUsername(fromUsername);
        if(user.isPresent()&&doesExist(toUsername)){
            user.get().getFriends().remove(toUsername);
            return true;
        }
        return false;
    }

    @Override
    public void setProfileImageBase64(String username, String base64String) {
        Optional<User> user = getByUsername(username);
        if(user.isPresent()){
            user.get().setProfileImageBase64(base64String);
        }
    }

    public List<User> getFiltered(Filter f){
        return users.stream().filter(user->f.doFilter(new SearchRequest(user))).toList();
    }

}