package com.example.testapp2;

public class AddressesModal {
    private String fullName,pincode,address;
    private Boolean selected;

    public AddressesModal(String fullName, String pincode, String address,Boolean selected) {
        this.fullName = fullName;
        this.pincode = pincode;
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
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
