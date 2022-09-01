package org.freeuni.musicforum.model;

import org.freeuni.musicforum.util.Utils;

import java.util.Date;
import java.util.Objects;

public class Review {
    private int prestige;
    private final String text;
    private final String albumId;
    private final String authorUsername;
    private final String id;
    private final Date uploadDate;
    private final int stars;

    public Review(String albumId, String authorUsername, String text, int stars, int prestige, Date uploadDate) {
        this.albumId = albumId;
        this.authorUsername = authorUsername;
        this.text = text;
        this.id = Utils.hashText(albumId + authorUsername + text);
        this.prestige = prestige;
        this.stars = stars;
        this.uploadDate = uploadDate;
    }

    public Review(String albumId, String authorUsername, String text, int stars) {
        this(albumId, authorUsername, text, stars, 1, new Date());
    }

    public String getId() { return this.id; }

    public String getAlbumId() { return this.albumId; }

    public String getAuthorUsername() { return this.authorUsername; }

    public String getText() { return this.text; }

    public int getStarCount() { return this.stars; }

    public int upvote() {
        return ++this.prestige;
    }

    public int downvote() {
        return --this.prestige;
    }

    public int getPrestige() {
        return this.prestige;
    }

    public Date getUploadDate(){ return this.uploadDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review review)) return false;
        return prestige == review.prestige &&
                Objects.equals(getText(), review.getText()) &&
                Objects.equals(getAlbumId(), review.getAlbumId()) &&
                Objects.equals(getAuthorUsername(), review.getAuthorUsername()) &&
                Objects.equals(getId(), review.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(prestige, getText(), getAlbumId(), getAuthorUsername(), getId());
    }

    @Override
    public String toString() {
        return "Review{" +
                "prestige=" + prestige +
                ", text='" + text + '\'' +
                ", albumId='" + albumId + '\'' +
                ", authorUsername='" + authorUsername + '\'' +
                ", id='" + id + '\'' +
                ", stars=" + stars +
                '}';
    }
}
