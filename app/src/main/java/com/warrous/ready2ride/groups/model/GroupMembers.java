package com.warrous.ready2ride.groups.model;

import java.io.Serializable;

public class GroupMembers implements Serializable {


    int GroupCount;
    String FirstName;
    String LastName;
    String ImageURL;

    public int getGroupCount() {
        return GroupCount;
    }

    public void setGroupCount(int groupCount) {
        GroupCount = groupCount;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }
}
