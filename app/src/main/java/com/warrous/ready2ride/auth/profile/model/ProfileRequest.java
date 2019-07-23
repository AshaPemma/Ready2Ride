package com.warrous.ready2ride.auth.profile.model;

public class ProfileRequest {

    int OwnerId;
    String FirstName;
    String LastName;
    String ProfileImage;
    String BGroungImage;
    String Mobile;

    public int getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(int ownerId) {
        OwnerId = ownerId;
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

    public String getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }

    public String getBGroungImage() {
        return BGroungImage;
    }

    public void setBGroungImage(String BGroungImage) {
        this.BGroungImage = BGroungImage;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }
}
