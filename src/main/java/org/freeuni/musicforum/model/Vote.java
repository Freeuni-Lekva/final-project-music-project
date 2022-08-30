package org.freeuni.musicforum.model;

public record Vote (

        String username,

        String reviewId,

        VoteType voteType
) {}
