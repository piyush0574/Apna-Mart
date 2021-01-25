package com.example.testapp2;

public class MyOrderItemModal
{
    private int resource;
    private String productTitle,deliveryDate;

    public MyOrderItemModal(int resource, String productTitle, String deliveryDate) {
        this.resource = resource;
        this.productTitle = productTitle;
        this.deliveryDate = deliveryDate;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryStatus) {
        this.deliveryDate = deliveryStatus;
    }
}
