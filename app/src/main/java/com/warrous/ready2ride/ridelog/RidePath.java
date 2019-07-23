package com.warrous.ready2ride.ridelog;

public class RidePath {

    double Latitude;
    double Longitude;
    double StartLatitude;
    double StartLongitude;
    double EndLatitude;
    double EndLongitude;
    boolean IsPaused;
    String Color;


    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
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

    public boolean isPaused() {
        return IsPaused;
    }

    public void setPaused(boolean paused) {
        IsPaused = paused;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }
}
