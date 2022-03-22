package com.bcit.myapplication;

import java.io.Serializable;

public class User implements Serializable {
    private final String name;
    private final String UID;
    private final String userType;
    private final String email;
    private final String CompanyID;
    public User(String name, String type, String UID, String email,String companyID) {
        this.name = name;
        this.userType = type;
        this.UID = UID;
        this.email = email;
        CompanyID = companyID;
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

    public String getEmail() {
        return email;
    }

    public String getCompanyID() {
        return CompanyID;
    }
}
