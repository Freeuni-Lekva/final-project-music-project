package org.freeuni.musicforum.model;

import org.freeuni.musicforum.util.Utils;

import java.util.Objects;

public class Review {
    private int upvote;
    private int downvote;
    private final String text;
    private final String albumId;
    private final String authorUsername;
    private final String id;

    private final int stars;

    public Review(String albumId, String authorUsername, String text, int stars) {
        this.albumId = albumId;
        this.authorUsername = authorUsername;
        this.text = text;
        // this will later need to be changed to Utils.hashText(text)
        this.id = Utils.hashText(albumId + authorUsername + text);
        this.upvote = 1;
        this.downvote = 0;
        this.stars = stars;
    }

    public String getId() { return this.id; }

    public String getAlbumId() { return this.albumId; }

    public String getAuthorUsername() { return this.authorUsername; }

    public String getText() { return this.text; }

    public int getStarCount() { return this.stars; }

    public int upvote() {
        return ++this.upvote;
    }

    public int downvote() {
        return ++this.downvote;
    }

    public int getPrestige() {
        return this.upvote - this.downvote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review review)) return false;
        return upvote == review.upvote && downvote == review.downvote &&
                Objects.equals(getText(), review.getText()) &&
                Objects.equals(getAlbumId(), review.getAlbumId()) &&
                Objects.equals(getAuthorUsername(), review.getAuthorUsername()) &&
                Objects.equals(getId(), review.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(upvote, downvote, getText(), getAlbumId(), getAuthorUsername(), getId());
    }

    @Override
    public String toString() {
        return "Review{" +
                "upvote=" + upvote +
                ", downvote=" + downvote +
                ", text='" + text + '\'' +
                ", albumId='" + albumId + '\'' +
                ", authorUsername='" + authorUsername + '\'' +
                ", id='" + id + '\'' +
                ", stars=" + stars +
                '}';
    }
}
