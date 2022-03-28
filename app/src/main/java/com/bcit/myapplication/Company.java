package com.bcit.myapplication;

import java.io.Serializable;

/**
 * Creates the company class.
 */
public class Company implements Serializable {
    private final String companyName;
    private final String license;
    private final String location;

    /**
     * Creates the constructor of the company class.
     *
     * @param companyName name of the company
     * @param license company license
     * @param location company location
     */
    public Company(String companyName, String license, String location) {
        this.companyName = companyName;
        this.license = license;
        this.location = location;
    }

    /**
     * Returns the name of the company.
     *
     * @return the name of the company
     */
    public String getName() {
        return companyName;
    }

    /**
     * Returns the location of the company.
     *
     * @return the location of the company
     */
    public String getLocation() {
        return location;
    }

    /**
     * Returns the company licence.
     *
     * @return the company licence
     */
    public String getLicense() {
        return license;
    }
}
