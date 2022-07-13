package org.freeuni.musicforum.model;

import org.freeuni.musicforum.util.UserUtils;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review review)) return false;
        return upvote == review.upvote && downvote == review.downvote &&
                Objects.equals(getText(), review.getText()) &&
                Objects.equals(getAlbumId(), review.getAlbumId()) &&
                Objects.equals(getUsername(), review.getUsername()) &&
                Objects.equals(getId(), review.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(upvote, downvote, getText(), getAlbumId(), getUsername(), getId());
    }

    @Override
    public String toString() {
        return "Review{" +
                "upvote=" + upvote +
                ", downvote=" + downvote +
                ", text='" + text + '\'' +
                ", albumId='" + albumId + '\'' +
                ", username='" + username + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
