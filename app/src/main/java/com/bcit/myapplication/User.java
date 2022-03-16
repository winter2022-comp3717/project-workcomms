package com.bcit.myapplication;

import java.io.Serializable;

public class User implements Serializable {
    private final String firstName;
    private final String lastName;
    private final String email;
    public User (String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
