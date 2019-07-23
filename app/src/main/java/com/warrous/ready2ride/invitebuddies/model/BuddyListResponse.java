package com.warrous.ready2ride.invitebuddies.model;

public class BuddyListResponse {

    int BuddyId;
    int UserID;
    int SenderId;
    int OwnerId;
    String FName;
    String LName;
    String Photo;
    String PhotoPath;
    String Mobile;
    String Email;

    public int getBuddyId() {
        return BuddyId;
    }

    public void setBuddyId(int buddyId) {
        BuddyId = buddyId;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getSenderId() {
        return SenderId;
    }

    public void setSenderId(int senderId) {
        SenderId = senderId;
    }

    public int getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(int ownerId) {
        OwnerId = ownerId;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        PhotoPath = photoPath;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
