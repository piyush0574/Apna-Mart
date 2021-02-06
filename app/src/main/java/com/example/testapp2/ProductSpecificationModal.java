package com.example.testapp2;

public class ProductSpecificationModal {
    //specification body
    private String featureName;
    private String featureValue;
    private int type;

    public ProductSpecificationModal(String featureName, String featureValue) {
        this.featureName = featureName;
        this.featureValue = featureValue;

    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getFeatureValue() {
        return featureValue;
    }

    public void setFeatureValue(String featureValue) {
        this.featureValue = featureValue;
    }

    private String title;
    public ProductSpecificationModal(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
//specification title


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
