package org.freeuni.musicforum.Activity;

import java.util.*;

public class ActivityLog {

    private final int MAX_LOGS  = 100;
    // String -> album id
    private LinkedList<String> activity;


    public ActivityLog() {
        this.activity = new LinkedList<>();
    }

    public void addLog(String id) {
        if(activity.size() >= MAX_LOGS) deleteOldActivity();
        activity.add(id);
    }

    public LinkedList<String> getLogs() {
        return activity;
    }

    private void deleteOldActivity() {
        activity.removeFirst();
    }

}
