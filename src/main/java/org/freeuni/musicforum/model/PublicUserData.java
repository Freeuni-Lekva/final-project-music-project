package org.freeuni.musicforum.model;

public record PublicUserData(
        String firstName,
        String lastName,
        String username,
        String profileImageBase64,
        Badge badge,
        Status status,
        int prestige
) { }
