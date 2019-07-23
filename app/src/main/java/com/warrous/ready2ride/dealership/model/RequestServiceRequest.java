package com.warrous.ready2ride.dealership.model;

public class RequestServiceRequest {

    int CycleServiceId;
    int DealerId;
    int ServiceCategoryId;
    String Date;
    int CycleId;
    String Time;


    public int getCycleServiceId() {
        return CycleServiceId;
    }

    public void setCycleServiceId(int cycleServiceId) {
        CycleServiceId = cycleServiceId;
    }

    public int getDealerId() {
        return DealerId;
    }

    public void setDealerId(int dealerId) {
        DealerId = dealerId;
    }

    public int getServiceCategoryId() {
        return ServiceCategoryId;
    }

    public void setServiceCategoryId(int serviceCategoryId) {
        ServiceCategoryId = serviceCategoryId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getCycleId() {
        return CycleId;
    }

    public void setCycleId(int cycleId) {
        CycleId = cycleId;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
