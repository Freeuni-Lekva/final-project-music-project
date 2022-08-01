package org.freeuni.musicforum.model;

public record Song(
        String name,
        String songName,
        String albumName,
        String artistName,
        String songBase64
) {}
