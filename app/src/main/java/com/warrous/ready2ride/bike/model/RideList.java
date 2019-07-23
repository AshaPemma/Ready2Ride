package com.warrous.ready2ride.bike.model;

import java.io.Serializable;

public class RideList implements Serializable {

int TotalRides;
String RideName;
String RideDescription;
String Duration;
String RideDistance;
String RideHappened;
String Start_geo_lat;
String Start_geo_lon;
String End_geo_lat;
String End_geo_long;
int RideId;
    public int getTotalRides() {
        return TotalRides;
    }

    public void setTotalRides(int totalRides) {
        TotalRides = totalRides;
    }

    public String getRideName() {
        return RideName;
    }

    public void setRideName(String rideName) {
        RideName = rideName;
    }

    public String getRideDescription() {
        return RideDescription;
    }

    public void setRideDescription(String rideDescription) {
        RideDescription = rideDescription;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getRideDistance() {
        return RideDistance;
    }

    public void setRideDistance(String rideDistance) {
        RideDistance = rideDistance;
    }

    public String getRideHappened() {
        return RideHappened;
    }

    public void setRideHappened(String rideHappened) {
        RideHappened = rideHappened;
    }

    public String getStart_geo_lat() {
        return Start_geo_lat;
    }

    public void setStart_geo_lat(String start_geo_lat) {
        Start_geo_lat = start_geo_lat;
    }

    public String getStart_geo_lon() {
        return Start_geo_lon;
    }

    public void setStart_geo_lon(String start_geo_lon) {
        Start_geo_lon = start_geo_lon;
    }

    public String getEnd_geo_lat() {
        return End_geo_lat;
    }

    public void setEnd_geo_lat(String end_geo_lat) {
        End_geo_lat = end_geo_lat;
    }

    public String getEnd_geo_long() {
        return End_geo_long;
    }

    public void setEnd_geo_long(String end_geo_long) {
        End_geo_long = end_geo_long;
    }

    public int getRideId() {
        return RideId;
    }

    public void setRideId(int rideId) {
        RideId = rideId;
    }
}
