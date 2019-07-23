package com.warrous.ready2ride.bike.model;

import java.io.Serializable;
import java.util.ArrayList;

public class DefaultBikeDetailsResponse implements Serializable {



//    int CycleId;
//            int OwnerId;
    String Photo;
           String PhotoPath;
  //  String OwnerName;
    int BrandId;
    int ModelId;
 //   double Year;
    int OdometerReading;
    int DealerId;
    String ModelName;
    String CategoryName;
    String BrandName;
    int CycleId;
    String VIn;
    String PurchaseDate;



   // String LastServiceDate;
   // int PrimaryBikeId;
  //  String HealthDescription;
    String CycleName;
    String Year;

    ArrayList<RideList> RideList;
    int position;
//
//    public int getCycleId() {
//        return CycleId;
//    }
//
//    public void setCycleId(int cycleId) {
//        CycleId = cycleId;
//    }
//
//    public int getDealerId() {
//        return OwnerId;
//    }
//
//    public void setDealerId(int ownerId) {
//        OwnerId = ownerId;
//    }

//    public String getPhoto() {
//        return Photo;
//    }
//
//    public void setPhoto(String photo) {
//        Photo = photo;
//    }
//
//    public String getPhotoPath() {
//        return PhotoPath;
//    }
//
//    public void setPhotoPath(String photoPath) {
//        PhotoPath = photoPath;
//    }
//
//    public String getOwnerName() {
//        return OwnerName;
//    }
//
//    public void setOwnerName(String ownerName) {
//        OwnerName = ownerName;
//    }

//    public int getBrandId() {
//        return BrandId;
//    }
//
//    public void setBrandId(int brandId) {
//        BrandId = brandId;
//    }
//
//    public int getModelId() {
//        return ModelId;
//    }
//
//    public void setModelId(int modelId) {
//        ModelId = modelId;
//    }

//    public double getYear() {
//        return Year;
//    }
//
//    public void setYear(double year) {
//        Year = year;
//    }


//    public int getOdometerReading() {
//        return OdometerReading;
//    }
//
//    public void setOdometerReading(int odometerReading) {
//        OdometerReading = odometerReading;
//    }

//    public int getDealerId() {
//        return DealerId;
//    }
//
//    public void setDealerId(int dealerId) {
//        DealerId = dealerId;
//    }


    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        PhotoPath = photoPath;
    }

    public int getBrandId() {
        return BrandId;
    }

    public void setBrandId(int brandId) {
        BrandId = brandId;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public int getModelId() {
        return ModelId;
    }

    public void setModelId(int modelId) {
        ModelId = modelId;
    }

    public int getDealerId() {
        return DealerId;
    }

    public void setDealerId(int dealerId) {
        DealerId = dealerId;
    }

    public String getModelName() {
        return ModelName;
    }

    public void setModelName(String modelName) {
        ModelName = modelName;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public ArrayList<com.warrous.ready2ride.bike.model.RideList> getRideList() {
        return RideList;
    }

    public void setRideList(ArrayList<com.warrous.ready2ride.bike.model.RideList> rideList) {
        RideList = rideList;
    }

    public int getCycleId() {
        return CycleId;
    }

    public void setCycleId(int cycleId) {
        CycleId = cycleId;
    }

    public int getOdometerReading() {
        return OdometerReading;
    }

    public String getPurchaseDate() {
        return PurchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        PurchaseDate = purchaseDate;
    }

    public void setOdometerReading(int odometerReading) {
        OdometerReading = odometerReading;
    }

    //    public String getLastServiceDate() {
//        return LastServiceDate;
//    }
//
//    public void setLastServiceDate(String lastServiceDate) {
//        LastServiceDate = lastServiceDate;
//    }
//
//    public int getPrimaryBikeId() {
//        return PrimaryBikeId;
//    }
//
//    public void setPrimaryBikeId(int primaryBikeId) {
//        PrimaryBikeId = primaryBikeId;
//    }
//
//    public String getHealthDescription() {
//        return HealthDescription;
//    }
//
//    public void setHealthDescription(String healthDescription) {
//        HealthDescription = healthDescription;
//    }

    public String getCycleName() {
        return CycleName;
    }

    public void setCycleName(String cycleName) {
        CycleName = cycleName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getVIn() {
        return VIn;
    }

    public void setVIn(String VIn) {
        this.VIn = VIn;
    }

    //    public ArrayList getRideList() {
//        return RideList;
//    }
//
//    public void setRideList(ArrayList rideList) {
//        RideList = rideList;
//    }
}
