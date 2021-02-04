package com.example.testapp2;

public class WishListModal {
    private String productImage;
    private String productTitle, discountPrice, cuttedPrice, percentDiscount;
    private boolean COD;
    private String productId;

    public WishListModal(String productImage, String productTitle, String discountPrice, String cuttedPrice, String percentDiscount, boolean COD, String productId) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.discountPrice = discountPrice;
        this.cuttedPrice = cuttedPrice;
        this.percentDiscount = percentDiscount;
        this.COD = COD;
        this.productId = productId;
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

    public boolean isCOD() {
        return COD;
    }

    public void setCOD(boolean COD) {
        this.COD = COD;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}

