package com.warrous.ready2ride.rides;

import java.util.ArrayList;

public class SaveRideRequest {


    int RideId;
    int CycleId;
    int OwnerId;
    String Name;
    String Description;
    String StartGeoLat;
    String StartGeoLon;
    String EndGeoLat;
    String EndGeoLong;
    String IsRide;
    int RideTypeId;
    int OdometerReading;
    boolean IsRideSaved;
    boolean IsRideEnded;
    ArrayList<RidePoints> RideLog;





    public int getRideId() {
        return RideId;
    }

    public void setRideId(int rideId) {
        RideId = rideId;
    }

    public int getCycleId() {
        return CycleId;
    }

    public void setCycleId(int cycleId) {
        CycleId = cycleId;
    }

    public int getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(int ownerId) {
        OwnerId = ownerId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStartGeoLat() {
        return StartGeoLat;
    }

    public void setStartGeoLat(String startGeoLat) {
        StartGeoLat = startGeoLat;
    }

    public String getStartGeoLon() {
        return StartGeoLon;
    }

    public void setStartGeoLon(String startGeoLon) {
        StartGeoLon = startGeoLon;
    }

    public String getEndGeoLat() {
        return EndGeoLat;
    }

    public void setEndGeoLat(String endGeoLat) {
        EndGeoLat = endGeoLat;
    }

    public String getEndGeoLong() {
        return EndGeoLong;
    }

    public void setEndGeoLong(String endGeoLong) {
        EndGeoLong = endGeoLong;
    }

    public String getIsRide() {
        return IsRide;
    }

    public void setIsRide(String isRide) {
        IsRide = isRide;
    }

    public int getRideTypeId() {
        return RideTypeId;
    }

    public void setRideTypeId(int rideTypeId) {
        RideTypeId = rideTypeId;
    }

    public int getOdometerReading() {
        return OdometerReading;
    }

    public void setOdometerReading(int odometerReading) {
        OdometerReading = odometerReading;
    }

    public boolean isRideSaved() {
        return IsRideSaved;
    }

    public void setRideSaved(boolean rideSaved) {
        IsRideSaved = rideSaved;
    }

    public boolean isRideEnded() {
        return IsRideEnded;
    }

    public void setRideEnded(boolean rideEnded) {
        IsRideEnded = rideEnded;
    }

    public ArrayList<RidePoints> getRideLog() {
        return RideLog;
    }

    public void setRideLog(ArrayList<RidePoints> rideLog) {
        this.RideLog = rideLog;
    }
}
