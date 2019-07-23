package com.warrous.ready2ride.tracksearch.restaurants;

import com.warrous.ready2ride.rides.LatLongPoints;

public class RestaurantResponse {

    int RestaurantId;
    String  PhotoPath;
    String RestaurantDistance;
    String RestaurantName;
    double latitude;
    double longitude;
    String Address;


    public int getRestaurantId() {
        return RestaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        RestaurantId = restaurantId;
    }

    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        PhotoPath = photoPath;
    }

    public String getRestaurantDistance() {
        return RestaurantDistance;
    }

    public void setRestaurantDistance(String restaurantDistance) {
        RestaurantDistance = restaurantDistance;
    }

    public String getRestaurantName() {
        return RestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        RestaurantName = restaurantName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
