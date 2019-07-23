package com.warrous.ready2ride.createbike.model;

public class Brand {
    private int BrandId;

    private String BrandName;

    public int getBrandId () {
        return BrandId;
    }

    public void setBrandId (int brandId) {
        BrandId = brandId;
    }

    public String getBrandName () {
        return BrandName;
    }

    public void setBrandName (String brandName) {
        BrandName = brandName;
    }

    @Override
    public String toString () {
        return BrandName;
    }

}
