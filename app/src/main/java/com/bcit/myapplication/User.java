package com.bcit.myapplication;

import java.io.Serializable;

public class User implements Serializable {
    private final String name;
    private final String email;
    private final String userType;
    public User (String name, String type, String email) {
        this.name = name;
        this.userType = type;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getUserType() {
        return userType;
    }

    public String getEmail() {
        return email;
    }
}
