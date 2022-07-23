package org.freeuni.musicforum.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public record User(
        String firstName,
        String lastName,
        Date birthDate,
        Gender gender,
        String username,
        Password password,
        Badge badge,
        HashMap<String, FriendshipStatus> friends
) {
    public User(String firstName,
                String lastName,
                Date birthDate,
                Gender gender,
                String username,
                Password password,
                Badge badge) {
        this(firstName, lastName, birthDate, gender, username, password, badge, new HashMap<>());
    }
}
