package com.warrous.ready2ride.rides;


import java.io.Serializable;
import java.util.ArrayList;

public class SaveRideresponse implements Serializable {

    String id;
    double Distance;
    double Time;
    double Speed;
    String Color;
    boolean Ispaused;




  //  ArrayList<PathList> pathlist;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isIspaused() {
        return Ispaused;
    }

    public void setIspaused(boolean ispaused) {
        Ispaused = ispaused;
    }
}
