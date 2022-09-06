package org.freeuni.musicforum.Activity;

import org.freeuni.musicforum.model.FeedCard;

import java.util.*;

public class ActivityLog {

    private final int MAX_LOGS  = 100;
    // String -> album id
    private LinkedList<FeedCard> activity;

    public enum ActivityType {
        NEW_ALBUM,
        NEW_REVIEW
    }

    // when using to display old activities pay attention if albums or reviews got deleted;
    public ActivityLog() {
        this.activity = new LinkedList<>();
    }

    public void addLog(ActivityType type, String id) {
        if(activity.size() >= MAX_LOGS) deleteOldActivity();
        activity.add(new FeedCard(type, id));
    }

    public LinkedList<FeedCard> getLogs() {
        return activity;
    }

    private void deleteOldActivity() {
        activity.removeFirst();
    }

}
