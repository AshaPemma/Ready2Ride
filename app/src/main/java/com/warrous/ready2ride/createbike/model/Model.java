package com.warrous.ready2ride.createbike.model;

public class Model {
    private int BrandId, ModelId;

    private String ModelName;

    public int getBrandId() {
        return BrandId;
    }

    public void setBrandId(int brandId) {
        BrandId = brandId;
    }

    public int getModelId() {
        return ModelId;
    }

    public void setModelId(int modelId) {
        ModelId = modelId;
    }

    public String getModelName() {
        return ModelName;
    }

    public void setModelName(String modelName) {
        ModelName = modelName;
    }
}
