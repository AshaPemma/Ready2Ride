package com.warrous.ready2ride.tracksearch.Model;

public class ParkingResponse {


    int ParkingId;
    String  PhotoPath;
    String ParkingDistance;
    String ParkingName;

    public int getParkingId() {
        return ParkingId;
    }

    public void setParkingId(int parkingId) {
        ParkingId = parkingId;
    }

    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        PhotoPath = photoPath;
    }

    public String getParkingDistance() {
        return ParkingDistance;
    }

    public void setParkingDistance(String parkingDistance) {
        ParkingDistance = parkingDistance;
    }

    public String getParkingName() {
        return ParkingName;
    }

    public void setParkingName(String parkingName) {
        ParkingName = parkingName;
    }
}
