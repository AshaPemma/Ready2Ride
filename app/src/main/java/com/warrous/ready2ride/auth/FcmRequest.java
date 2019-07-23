package com.warrous.ready2ride.auth;

public class FcmRequest {


    String FcmToken;
    int UserId;
    int Id;
     String Platform;




    public String getFcmToken() {
        return FcmToken;
    }

    public void setFcmToken(String fcmToken) {
        FcmToken = fcmToken;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPlatform() {
        return Platform;
    }

    public void setPlatform(String platform) {
        Platform = platform;
    }
}
