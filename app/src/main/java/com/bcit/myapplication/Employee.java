package com.bcit.myapplication;

import java.io.Serializable;

public class Employee extends User implements Serializable {
    private final int employeeID;
    public Employee (String firstName, String lastName, String email, int employeeID) {
        super(firstName, lastName, email);
        this.employeeID = employeeID;
    }

    public int getEmployeeID() {
        return employeeID;
    }
}
