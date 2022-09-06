package org.freeuni.musicforum.Activity;



public class Activity {


    private static ActivityLog activityLog;

    private Activity() {
        activityLog = new ActivityLog();
    }

    public static ActivityLog getActivityLog() {
        if(activityLog == null) new Activity();
        return activityLog;
    }

}
