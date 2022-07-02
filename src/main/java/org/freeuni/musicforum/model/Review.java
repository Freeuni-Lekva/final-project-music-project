package org.freeuni.musicforum.model;

import org.freeuni.musicforum.util.UserUtils;

public class Review {

    private int upvote;
    private int downvote;
    private final String text;
    private final String albumId;
    private final String username;
    private final String id;

    public Review(String albumId, String username, String text) {
        this.albumId = albumId;
        this.username = username;
        this.text = text;
        // this will later need to be changed to Utils.hashText(text)
        this.id = UserUtils.hashPassword(albumId + text);
        this.upvote = 1;
        this.downvote = 0;
    }

    public String getId() { return this.id; }

    public String getAlbumId() { return this.albumId; }

    public String getUsername() { return this.username; }

    public String getText() { return this.text; }

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
