package com.bcit.myapplication;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This is a Model class for Posts. The instance variables match those found in the fireStore
 * document Posts. It implements Serializable for the use of being used inside an adapter for a
 * Recycler view
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

    /**
     * Get company ID
     * @return a String
     */
    public String getCompanyID() {
        return companyID;
    }

    /**
     * Get poster's name.
     *
     * @return a String
     */
    public String getPosterName() {
        return posterName;
    }

    /**
     * Get Sender's ID.
     *
     * @return a String
     */
    public String getSenderID() {
        return senderID;
    }

    /**
     * Get the date time of the post.
     *
     * @return a long
     */
    public long getDateTime() {
        return dateTime;
    }

    /**
     * Get the posted message.
     *
     * @return a String
     */
    public String getMessage() {
        return message;
    }
}
