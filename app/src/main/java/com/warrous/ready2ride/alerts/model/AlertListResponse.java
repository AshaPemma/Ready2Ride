package com.warrous.ready2ride.alerts.model;

import java.io.Serializable;

public class AlertListResponse implements Serializable {
    int AlertId;
 int OwnerId;
 String AlertBy;
 String LastServiced;
 String Every;
 String Remainder;
 int ServiceCategoryId;

    public int getAlertId() {
        return AlertId;
    }

    public void setAlertId(int alertId) {
        AlertId = alertId;
    }

    public int getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(int ownerId) {
        OwnerId = ownerId;
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

    public int getServiceCategoryId() {
        return ServiceCategoryId;
    }

    public void setServiceCategoryId(int serviceCategoryId) {
        ServiceCategoryId = serviceCategoryId;
    }
}
