package com.example.testapp2;

public class HorizonalProductScrollModel
{
    private String productID;
    private String productImage;
    private String productTitle,productPrice,getProductDescription;

    public HorizonalProductScrollModel(String productID,String productImage, String productTitle,String productPrice, String getProductDescription) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productID=productID;
        this.getProductDescription = getProductDescription;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getGetProductDescription() {
        return getProductDescription;
    }

    public void setGetProductDescription(String getProductDescription) {
        this.getProductDescription = getProductDescription;
    }
}
