package com.warrous.ready2ride.auth.profile.model;

public class ProfileResponse {

    String FirstName;
    String LastName;

    int GroupCount;
    double RidesDistance;
    int RidesCount;
    int Garrage;
    int Buddies;
    int DealerShipCount;
     String ProfilePhoto;
     String BGroundImage;
    int wallet;
    int RidesTime;
    String Mobile;

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public int getGroupCount() {
        return GroupCount;
    }

    public void setGroupCount(int groupCount) {
        GroupCount = groupCount;
    }

    public double getRidesDistance() {
        return RidesDistance;
    }

    public void setRidesDistance(double ridesDistance) {
        RidesDistance = ridesDistance;
    }

    public int getRidesCount() {
        return RidesCount;
    }

    public void setRidesCount(int ridesCount) {
        RidesCount = ridesCount;
    }

    public int getGarrage() {
        return Garrage;
    }

    public void setGarrage(int garrage) {
        Garrage = garrage;
    }

    public int getBuddies() {
        return Buddies;
    }

    public void setBuddies(int buddies) {
        Buddies = buddies;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public int getRidesTime() {
        return RidesTime;
    }

    public void setRidesTime(int ridesTime) {
        RidesTime = ridesTime;
    }

    public int getDealerShipCount() {
        return DealerShipCount;
    }

    public void setDealerShipCount(int dealerShipCount) {
        DealerShipCount = dealerShipCount;
    }

    public String getProfilePhoto() {
        return ProfilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        ProfilePhoto = profilePhoto;
    }

    public String getBGroundImage() {
        return BGroundImage;
    }

    public void setBGroundImage(String BGroundImage) {
        this.BGroundImage = BGroundImage;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }
}
