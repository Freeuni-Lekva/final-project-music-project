package org.freeuni.musicforum.model;

import java.util.ArrayList;
import java.util.Date;

public record User(
        String firstName,
        String lastName,
        Date birthDate,
        Gender gender,
        String username,
        Password password,
        Badge badge,
        ArrayList<String> friendList
) {
    public User(String firstName,
                String lastName,
                Date birthDate,
                Gender gender,
                String username,
                Password password,
                Badge badge) {
        this(firstName, lastName, birthDate, gender, username, password, badge, new ArrayList<>());
    }
}
