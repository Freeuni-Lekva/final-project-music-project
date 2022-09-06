package org.freeuni.musicforum.Activity;

import org.freeuni.musicforum.model.FeedCard;
import org.freeuni.musicforum.service.ServiceFactory;

import java.util.*;

public class ActivityLog {

    private final int MAX_LOGS  = 100;
    // String -> album id
    private LinkedList<FeedCard> activity;

    public enum ActivityType {
        NEW_ALBUM,
        NEW_REVIEW
    }

    public ActivityLog() {
        this.activity = new LinkedList<>();
    }

    public void addLog(String id) {
        if(activity.size() >= MAX_LOGS) deleteOldActivity();
        activity.add(new FeedCard(getType(id), id));

    }

    public LinkedList<FeedCard> getLogs() {
        return activity;
    }

    private void deleteOldActivity() {
        activity.removeFirst();
    }

    private ActivityType getType(String id) {
       if(ServiceFactory.getReviewService().getAllReviewsFor(id).isEmpty()) return ActivityType.NEW_ALBUM;
       return ActivityType.NEW_REVIEW;
    }

}
