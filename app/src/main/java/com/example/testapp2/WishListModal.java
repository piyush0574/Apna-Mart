package com.example.testapp2;

public class WishListModal {
    private  int productImage;
    private String productTitle,discountPrice,cuttedPrice,percentDiscount,paymentMethod;

    public WishListModal(int productImage, String productTitle, String discountPrice, String cuttedPrice, String percentDiscount, String paymentMethod) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.discountPrice = discountPrice;
        this.cuttedPrice = cuttedPrice;
        this.percentDiscount = percentDiscount;
        this.paymentMethod = paymentMethod;
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

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public String getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(String percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
