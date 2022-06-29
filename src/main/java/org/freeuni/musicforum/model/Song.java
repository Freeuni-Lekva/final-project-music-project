package org.freeuni.musicforum.model;

public record Song(
        String songName,
        String albumName,
        String artistName,
        String source,
        double length
) {}
