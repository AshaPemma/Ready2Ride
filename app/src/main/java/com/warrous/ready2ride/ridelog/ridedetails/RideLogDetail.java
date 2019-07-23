package com.warrous.ready2ride.ridelog.ridedetails;

public class RideLogDetail {

    double Distance;
    double Time;
    double Speed;
    boolean isPaused;
    String Color;


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

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }
}
