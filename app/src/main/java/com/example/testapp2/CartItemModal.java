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
    private int productImage;
    private String productTitle;
    private String cuttedPrice,originalPrice;
    private int productQunatity;
    private int discount;

    public CartItemModal(int type, int productImage, String productTitle, String cuttedPrice, String originalPrice, int productQunatity, int discount) {
        this.type = type;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.cuttedPrice = cuttedPrice;
        this.originalPrice = originalPrice;
        this.productQunatity = productQunatity;
        this.discount = discount;
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

    public int getProductQunatity() {
        return productQunatity;
    }

    public void setProductQunatity(int productQunatity) {
        this.productQunatity = productQunatity;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
    // Cart Item

    //cart Total
    private int totalNumberOfItems;
    private String totalAmount,deliveryPrice,savedAmount,amountAfterDiscount;

    public CartItemModal(int type, int totalNumberOfItems, String totalAmount, String deliveryPrice, String savedAmount,String amountAfterDiscount) {
        this.type = type;
        this.totalNumberOfItems = totalNumberOfItems;
        this.totalAmount = totalAmount;
        this.deliveryPrice = deliveryPrice;
        this.savedAmount = savedAmount;
        this.amountAfterDiscount=amountAfterDiscount;
    }

    public String getAmountAfterDiscount() {
        return amountAfterDiscount;
    }

    public void setAmountAfterDiscount(String amountAfterDiscount) {
        this.amountAfterDiscount = amountAfterDiscount;
    }

    public int getTotalNumberOfItems() {
        return totalNumberOfItems;
    }

    public void setTotalNumberOfItems(int totalNumberOfItems) {
        this.totalNumberOfItems = totalNumberOfItems;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(String savedAmount) {
        this.savedAmount = savedAmount;
    }
    //cart Total




}
