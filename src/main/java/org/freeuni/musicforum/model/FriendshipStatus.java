package org.freeuni.musicforum.model;

public enum FriendshipStatus {

    REQUEST_SENT,
    ACCEPT_REQUEST,
    FRIENDS;

    public String getButtonText(){
        switch (this) {
            case REQUEST_SENT: return "Request Sent";
            case ACCEPT_REQUEST: return "Accept Request";
            case FRIENDS: return "Delete Friend";
        }
        return null;
    }
}
