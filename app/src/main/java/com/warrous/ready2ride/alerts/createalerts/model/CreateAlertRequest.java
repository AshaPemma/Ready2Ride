package com.warrous.ready2ride.alerts.createalerts.model;

public class CreateAlertRequest {

    int ownerId;
     int ServiceCategoryId;
     String AlertBy;
     String LastServiced;
     String Every;
     String Remainder;
     int AlertId;


    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getServiceCategoryId() {
        return ServiceCategoryId;
    }

    public void setServiceCategoryId(int serviceCategoryId) {
        ServiceCategoryId = serviceCategoryId;
    }

    public String getAlertBy() {
        return AlertBy;
    }

    public void setAlertBy(String alertBy) {
        AlertBy = alertBy;
    }

    public String getLastServiced() {
        return LastServiced;
    }

    public void setLastServiced(String lastServiced) {
        LastServiced = lastServiced;
    }

    public String getEvery() {
        return Every;
    }

    public void setEvery(String every) {
        Every = every;
    }

    public String getRemainder() {
        return Remainder;
    }

    public void setRemainder(String remainder) {
        Remainder = remainder;
    }

    public int getAlertId() {
        return AlertId;
    }

    public void setAlertId(int alertId) {
        AlertId = alertId;
    }
}
