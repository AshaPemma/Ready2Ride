package com.warrous.ready2ride.invitebuddies.model;

public class BuddiesKnown {


    String MobileNumber;
    String Email;
    boolean IsBuddy;
    int OwnerId;
    int UserId;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public boolean isBuddy() {
        return IsBuddy;
    }

    public void setBuddy(boolean buddy) {
        IsBuddy = buddy;
    }

    public int getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(int ownerId) {
        OwnerId = ownerId;
    }
}
