package org.freeuni.musicforum.model;

import java.util.Date;

public record User(
        String firstName,
        String lastName,
        Date birthDate,
        Gender gender,
        String username,
        Password password,
        Badge badge
) {}
