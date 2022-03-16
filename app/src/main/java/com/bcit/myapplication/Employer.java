package com.bcit.myapplication;

import java.io.Serializable;

public class Employer extends User implements Serializable {
    private final int employerID;
    public Employer (String firstName, String lastName, String email, int employerID) {
        super(firstName, lastName, email);
        this.employerID = employerID;
    }

    public int getEmployerID() {
        return employerID;
    }
}
