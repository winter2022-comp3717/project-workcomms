package com.bcit.myapplication;

import java.io.Serializable;

public class Company implements Serializable {
    private final String companyName;
    private final String license;
    private final String location;

    public Company(String companyName, String license, String location) {
        this.companyName = companyName;
        this.license = license;
        this.location = location;
    }

    public String getName() {
        return companyName;
    }

    public String getLocation() {
        return location;
    }

    public String getLicense() {
        return license;
    }
}
