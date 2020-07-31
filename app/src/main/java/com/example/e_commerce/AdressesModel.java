package com.example.e_commerce;

public class AdressesModel {
    private String fullname;
    private String pincode;
    private String address;
    private Boolean selectedAddress ;

    public AdressesModel(String fullname,  String address,String pincode,Boolean Selected) {
        this.fullname = fullname;
        this.address = address;
        this.pincode = pincode;
        this.selectedAddress = Selected;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Boolean getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(Boolean selectedAddress) {
        this.selectedAddress = selectedAddress;
    }
}
