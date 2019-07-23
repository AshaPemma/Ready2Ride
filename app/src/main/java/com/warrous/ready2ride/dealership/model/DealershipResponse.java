package com.warrous.ready2ride.dealership.model;

import java.io.Serializable;

public class DealershipResponse implements Serializable {


    int DealerId;
     String Name;
     String Email;
     String Phone;
     String WebSite;
     String Zip;
     String City;
     String State;
     String Address;
     int position;
     String DealerLatitude;
     String DealerLongitude;
    String DealerDistance;
    boolean IsDefault;
    boolean IsSelected;
    String BackgroundImage;

    public int getDealerId() {
        return DealerId;
    }

    public void setDealerId(int dealerId) {
        DealerId = dealerId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getWebSite() {
        return WebSite;
    }

    public void setWebSite(String webSite) {
        WebSite = webSite;
    }

    public String getZip() {
        return Zip;
    }

    public void setZip(String zip) {
        Zip = zip;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getDealerLatitude() {
        return DealerLatitude;
    }

    public void setDealerLatitude(String dealerLatitude) {
        DealerLatitude = dealerLatitude;
    }

    public String getDealerLongitude() {
        return DealerLongitude;
    }

    public void setDealerLongitude(String dealerLongitude) {
        DealerLongitude = dealerLongitude;
    }

    public String getDealerDistance() {
        return DealerDistance;
    }

    public void setDealerDistance(String dealerDistance) {
        DealerDistance = dealerDistance;
    }

    public boolean isDefault() {
        return IsDefault;
    }

    public void setDefault(boolean aDefault) {
        IsDefault = aDefault;
    }

    public boolean isSelected() {
        return IsSelected;
    }

    public void setSelected(boolean selected) {
        IsSelected = selected;
    }

    public String getBackgroundImage() {
        return BackgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        BackgroundImage = backgroundImage;
    }
}
