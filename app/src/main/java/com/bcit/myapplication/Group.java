package com.bcit.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable {
    private final String name;
    private final ArrayList<String> memberID;

    public Group(final String name, final ArrayList<String> memberID){
        this.name = name;
        this.memberID = memberID;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getMemberID() {
        return memberID;
    }
}
