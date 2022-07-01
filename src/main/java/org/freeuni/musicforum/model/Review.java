package org.freeuni.musicforum.model;

import org.freeuni.musicforum.util.UserUtils;

public class Review {

    private int upvote;
    private int downvote;
    private final String text;
    private final String album_id;
    private final String username;
    private final String id;

    public Review(String album_id, String username, String text) {
        this.album_id = album_id;
        this.username = username;
        this.text = text;
        // this will later need to be changed to Utils.hashText(text)
        this.id = UserUtils.hashPassword(text);
        this.upvote = 1;
        this.downvote = 0;
    }

    public int upvote() {
        return ++this.upvote;
    }

    public int downvote() {
        return ++this.downvote;
    }

    public int getPrestige() {
        return this.upvote - this.downvote;
    }

    public int getFame() {
        return this.upvote + this.downvote;
    }

}
