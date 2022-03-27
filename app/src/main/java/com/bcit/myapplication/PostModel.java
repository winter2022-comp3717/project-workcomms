package com.bcit.myapplication;

import java.io.Serializable;

/**
 * This is a Model class for Posts. The instance variables match those found in the fireStore
 * document Posts.
 *
 */
public class PostModel implements Serializable {
    private final String companyID;
    private final String posterName;
    private final String senderID;
    private final String message;

    public PostModel(final String companyID,
                     final String posterName,
                     final String senderID,
                     final String message) {

        this.companyID = companyID;
        this.posterName = posterName;
        this.senderID = senderID;
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

    public String getMessage() {
        return message;
    }
}
