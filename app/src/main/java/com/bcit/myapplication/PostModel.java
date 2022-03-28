package com.bcit.myapplication;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This is a Model class for Posts. The instance variables match those found in the fireStore
 * document Posts.
 *
 */
public class PostModel implements Serializable {
    private final String companyID;
    private final String posterName;
    private final String senderID;
    private final long dateTime;
    private final String message;

    public PostModel(final String companyID,
                     final String posterName,
                     final String senderID,
                     long dateTime, final String message) {

        this.companyID = companyID;
        this.posterName = posterName;
        this.senderID = senderID;
        this.dateTime = dateTime;
        this.message = message;
    }

    public String getCompanyID() {
        return companyID;
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
