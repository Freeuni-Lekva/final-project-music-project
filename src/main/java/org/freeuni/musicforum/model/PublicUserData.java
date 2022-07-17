package org.freeuni.musicforum.model;

public record PublicUserData(
        String firstName,
        String lastName,
        String username,
        Badge badge,
        int prestige
        ) { }
