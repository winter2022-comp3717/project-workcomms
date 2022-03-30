package com.bcit.myapplication;

public class Employee extends User{
    private final String designation;
    private final String groupID;
    public Employee(String name, String type, String UID, String email, String companyID, String designation, String groupID ) {
        super(name, type, UID, email, companyID);
        this.designation = designation;
        this.groupID = groupID;
    }

    public String getDesignation() {
        return designation;
    }

    public String getGroupID() {
        return groupID;
    }
}
