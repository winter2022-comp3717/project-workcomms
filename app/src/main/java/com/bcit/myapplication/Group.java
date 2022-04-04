package com.bcit.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is a model class for the Group object.
 */
public class Group implements Serializable {
    private final String name;
    private final ArrayList<String> memberID;

    public Group(final String name, final ArrayList<String> memberID){
        this.name = name;
        this.memberID = memberID;
    }

    /**
     * Access the name for group object
     * @return a String
     */
    public String getName() {
        return name;
    }

    /**
     * Access the MemberList for Group object.
     * @return an Arraylist of Strings
     */
    public ArrayList<String> getMemberID() {
        return memberID;
    }
}
