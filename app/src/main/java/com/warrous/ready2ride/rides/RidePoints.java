package com.warrous.ready2ride.rides;

public class RidePoints {



     int RideId;
    int SubRideId;
   double StartLatitude;
     double StartLongitude;
     double EndLatitude;
     double EndLongitude;
     double Distance ;
     double Time ;
    double Speed ;
  String Color ;
     boolean IsPaused;


    public int getRideId() {
        return RideId;
    }

    public void setRideId(int rideId) {
        RideId = rideId;
    }

    public int getSubRideId() {
        return SubRideId;
    }

    public void setSubRideId(int subRideId) {
        SubRideId = subRideId;
    }

    public double getStartLatitude() {
        return StartLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        StartLatitude = startLatitude;
    }

    public double getStartLongitude() {
        return StartLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        StartLongitude = startLongitude;
    }

    public double getEndLatitude() {
        return EndLatitude;
    }

    public void setEndLatitude(double endLatitude) {
        EndLatitude = endLatitude;
    }

    public double getEndLongitude() {
        return EndLongitude;
    }

    public void setEndLongitude(double endLongitude) {
        EndLongitude = endLongitude;
    }

    public double getDistance() {
        return Distance;
    }

    public void setDistance(double distance) {
        Distance = distance;
    }

    public double getTime() {
        return Time;
    }

    public void setTime(double time) {
        Time = time;
    }

    public double getSpeed() {
        return Speed;
    }

    public void setSpeed(double speed) {
        Speed = speed;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public boolean isPaused() {
        return IsPaused;
    }

    public void setPaused(boolean paused) {
        IsPaused = paused;
    }
}
