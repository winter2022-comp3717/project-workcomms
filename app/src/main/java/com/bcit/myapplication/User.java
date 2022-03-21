package com.bcit.myapplication;

import java.io.Serializable;

public class User implements Serializable {
    private final String name;
    private final String UID;
    private final String userType;
    public User (String name, String type, String UID) {
        this.name = name;
        this.userType = type;
        this.UID = UID;
    }

    public String getName() {
        return name;
    }

    public String getUserType() {
        return userType;
    }

    public String getUID() {
        return UID;
    }
}
