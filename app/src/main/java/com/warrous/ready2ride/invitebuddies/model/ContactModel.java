package com.warrous.ready2ride.invitebuddies.model;

import java.io.Serializable;

public class ContactModel implements Serializable {

    public String id;
    public String name;
    public String mobileNumber;
    public boolean isBuddy;
   // public Bitmap photo;
  //  public Uri photoURI;

    public   int OwnerId;
    public int UserId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public boolean isBuddy() {
        return isBuddy;
    }

    public void setBuddy(boolean buddy) {
        isBuddy = buddy;
    }

    public int getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(int ownerId) {
        OwnerId = ownerId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
