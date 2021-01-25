package com.example.testapp2;

public class ProductSpecificationModal {
    //specification body
    private String featureName;
    private String featureValue;
    private int type;

    public ProductSpecificationModal(String featureName, String featureValue, int type) {
        this.featureName = featureName;
        this.featureValue = featureValue;
        this.type = type;
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

    //specification body
     // Multiple selection of layout on single fragment
    //specification title
    public static final int SPECIFICATION_TITLE=0;
    public static final int SPECIFICATION_BODY=1;

    private String title;
    public ProductSpecificationModal(int type, String title) {
        this.type = type;
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
