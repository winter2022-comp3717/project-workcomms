package com.bcit.myapplication;

/**
 * This is a Model class for the employee which extends from Employee class.
 */
public class Employee extends User{
    private final String designation;
    private final String groupID;
    public Employee(String name, String type, String UID, String email, String companyID, String designation, String groupID ) {
        super(name, type, UID, email, companyID);
        this.designation = designation;
        this.groupID = groupID;
    }

    /**
     * Access the designation for the Employee object.
     * @return a String
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * Access the group ID for the Employee object.
     * @return a String
     */
    public String getGroupID() {
        return groupID;
    }
}
