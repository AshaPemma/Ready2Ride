package com.warrous.ready2ride.navigation.model;

public class MessagesResponse {


    int OwnerId;
    int DealerId;
    String Mesage;
    boolean IsOwner;
    boolean IsDealer;

    public int getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(int ownerId) {
        OwnerId = ownerId;
    }

    public int getDealerId() {
        return DealerId;
    }

    public void setDealerId(int dealerId) {
        DealerId = dealerId;
    }

    public String getMesage() {
        return Mesage;
    }

    public void setMesage(String mesage) {
        Mesage = mesage;
    }

    public boolean isOwner() {
        return IsOwner;
    }

    public void setOwner(boolean owner) {
        IsOwner = owner;
    }

    public boolean isDealer() {
        return IsDealer;
    }

    public void setDealer(boolean dealer) {
        IsDealer = dealer;
    }
}
