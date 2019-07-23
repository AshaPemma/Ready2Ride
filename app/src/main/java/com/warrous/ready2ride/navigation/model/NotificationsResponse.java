package com.warrous.ready2ride.navigation.model;

public class NotificationsResponse {

    int OwnerId;
    int DealerId;
    String Description;
    String CreatedDate;
    int NotificationId;
    String Subject;
    String NotificationType;
    String Image;
    String Name;


    public int getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(int ownerId) {
        OwnerId = ownerId;
    }

    public int getDealerId() {
        return DealerId;
    }

    public void setDealerId(int dealerId) {
        DealerId = dealerId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public int getNotificationId() {
        return NotificationId;
    }

    public void setNotificationId(int notificationId) {
        NotificationId = notificationId;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getNotificationType() {
        return NotificationType;
    }

    public void setNotificationType(String notificationType) {
        NotificationType = notificationType;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
