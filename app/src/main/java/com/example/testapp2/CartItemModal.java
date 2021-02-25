package com.example.testapp2;

public class CartItemModal  {
    public static final int CART_ITEM=0;
    public static final int TOTAL_AMOUNT=1;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    // Cart Item
    private String productImage;
    private String productTitle;
    private String cuttedPrice,originalPrice;
    private long productQunatity;
    private long discount;
    private String productID;

    public CartItemModal(int type, String productImage, String productTitle, String cuttedPrice, String originalPrice, long productQunatity, long discount,String productID) {
        this.type = type;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.cuttedPrice = cuttedPrice;
        this.originalPrice = originalPrice;
        this.productQunatity = productQunatity;
        this.discount = discount;
        this.productID=productID;
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

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public long getProductQunatity() {
        return productQunatity;
    }

    public void setProductQunatity(Long productQunatity) {
        this.productQunatity = productQunatity;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }
    // Cart Item

    //cart Total


    public CartItemModal(int type) {
        this.type = type;
    }
}
