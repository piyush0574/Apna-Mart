package com.example.testapp2;

public class AddressesModal {
    private String fullName,mobileNo,address;
    private boolean selected;

    public AddressesModal(String fullName, String mobileNo, String address,boolean selected) {
        this.fullName = fullName;
        this.mobileNo = mobileNo;
        this.address = address;
        this.selected=selected;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPincode() {
        return mobileNo;
    }

    public void setPincode(String pincode) {
        this.mobileNo = pincode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
