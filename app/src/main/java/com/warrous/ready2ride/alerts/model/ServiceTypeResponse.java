package com.warrous.ready2ride.alerts.model;

public class ServiceTypeResponse {

    int ServiceCategoryId;
    String ServiceCategoryName;

    public int getServiceCategoryId() {
        return ServiceCategoryId;
    }

    public void setServiceCategoryId(int serviceCategoryId) {
        ServiceCategoryId = serviceCategoryId;
    }

    public String getServiceCategoryName() {
        return ServiceCategoryName;
    }

    public void setServiceCategoryName(String serviceCategoryName) {
        ServiceCategoryName = serviceCategoryName;
    }
}
