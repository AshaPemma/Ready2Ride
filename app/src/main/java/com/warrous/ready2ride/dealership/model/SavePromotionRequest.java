package com.warrous.ready2ride.dealership.model;

public class SavePromotionRequest {


    int Id;
    int NotificationId;
    boolean Action;
    String NotificationType;
    int OwnerId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getNotificationId() {
        return NotificationId;
    }

    public void setNotificationId(int notificationId) {
        NotificationId = notificationId;
    }

    public boolean isAction() {
        return Action;
    }

    public void setAction(boolean action) {
        Action = action;
    }

    public String getNotificationType() {
        return NotificationType;
    }

    public void setNotificationType(String notificationType) {
        NotificationType = notificationType;
    }

    public int getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(int ownerId) {
        OwnerId = ownerId;
    }
}
