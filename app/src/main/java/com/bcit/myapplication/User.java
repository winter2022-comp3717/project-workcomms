package com.bcit.myapplication;

import java.io.Serializable;

/**
 * Creates the user class.
 */
public class User implements Serializable {
    private final String name;
    private final String UID;
    private final String userType;
    private final String email;
    private final String CompanyID;
    private final String designation;

    /**
     * Creates the constructor of the user class.
     *  @param name name of the user
     * @param type usertype of the user
     * @param UID UID of the user
     * @param email user's email
     * @param companyID user's company ID
     * @param designation
     */
    public User(String name, String type, String UID, String email, String companyID, String designation) {
        this.name = name;
        this.userType = type;
        this.UID = UID;
        this.email = email;
        CompanyID = companyID;
        this.designation = designation;
    }

    /**
     * Returns the name of the user.
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the user type of the user.
     *
     * @return the user type of the user
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Returns the UID of the user.
     *
     * @return the UID of the user
     */
    public String getUID() {
        return UID;
    }

    /**
     * Returns the email of the user.
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the company ID of the user.
     *
     * @return the company ID of the user
     */
    public String getCompanyID() {
        return CompanyID;
    }

    /**
     * Returns the designation of the employee -> none if employer
     *
     * @return the designation
     */
    public String getDesignation() {
        return designation;
    }
}
