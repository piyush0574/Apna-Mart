package com.example.testapp2;

public class HorizonalProductScrollModel
{
    private int productImage;
    private String productTitle,productPrice,getProductDescription;

    public HorizonalProductScrollModel(int productImage, String productTitle, String productPrice, String getProductDescription) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.getProductDescription = getProductDescription;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
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
