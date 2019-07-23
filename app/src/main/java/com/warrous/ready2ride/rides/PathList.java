package com.warrous.ready2ride.rides;

import java.io.Serializable;

public class PathList implements Serializable {

    double Latitude;
    double Longitude;
    String Color;
    boolean IsPaused;

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
