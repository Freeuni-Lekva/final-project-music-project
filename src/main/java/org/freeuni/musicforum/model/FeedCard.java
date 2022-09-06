package org.freeuni.musicforum.model;


import org.freeuni.musicforum.Activity.ActivityLog;

public record FeedCard(
        ActivityLog.ActivityType type,
        String id
) {
}
