package com.bcit.myapplication;

import java.io.Serializable;

/**
 * This is a Model class for Posts. The instance variables match those found in the fireStore
 * document Posts.
 *
 */
public class ChatModel implements Serializable {
    private final String groupID;
    private final String posterName;
    private final String senderID;
    private final long dateTime;
    private final String message;

    public ChatModel(final String groupID,
                     final String posterName,
                     final String senderID,
                     long dateTime, final String message) {

        this.groupID = groupID;
        this.posterName = posterName;
        this.senderID = senderID;
        this.dateTime = dateTime;
        this.message = message;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getPosterName() {
        return posterName;
    }

    public String getSenderID() {
        return senderID;
    }

    public long getDateTime() {
        return dateTime;
    }

    public String getMessage() {
        return message;
    }
}
