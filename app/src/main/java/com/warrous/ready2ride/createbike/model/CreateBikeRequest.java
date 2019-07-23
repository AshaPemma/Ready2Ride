package com.warrous.ready2ride.createbike.model;

public class CreateBikeRequest {


    String Name;
     String VIN;
     int Make;
     int Model;
    String Year;
    String OwnerId;
    String Image;
    String PurchaseDate;
    int OdometerReading;
    int CycleId;


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public int getMake() {
        return Make;
    }

    public void setMake(int make) {
        Make = make;
    }

    public int getModel() {
        return Model;
    }

    public void setModel(int model) {
        Model = model;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String ownerId) {
        OwnerId = ownerId;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPurchaseDate() {
        return PurchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        PurchaseDate = purchaseDate;
    }

    public int getOdometerReading() {
        return OdometerReading;
    }

    public void setOdometerReading(int odometerReading) {
        OdometerReading = odometerReading;
    }

    public int getCycleId() {
        return CycleId;
    }

    public void setCycleId(int cycleId) {
        CycleId = cycleId;
    }
}
