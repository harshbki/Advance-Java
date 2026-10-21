package com.shopsphere.model;

public class Address {
    private int addressId;
    private int userId;
    private String addressLine;
    private String city;
    private String state;
    private String pincode;
    private String addressType;

    public Address() { }
    public Address(int addressId, int userId, String addressLine, String city,
                   String state, String pincode, String addressType) {
        this.addressId = addressId; this.userId = userId; this.addressLine = addressLine;
        this.city = city; this.state = state; this.pincode = pincode; this.addressType = addressType;
    }
    public int getAddressId() { return addressId; }
    public void setAddressId(int value) { this.addressId = value; }
    public int getUserId() { return userId; }
    public void setUserId(int value) { this.userId = value; }
    public String getAddressLine() { return addressLine; }
    public void setAddressLine(String value) { this.addressLine = value; }
    public String getCity() { return city; }
    public void setCity(String value) { this.city = value; }
    public String getState() { return state; }
    public void setState(String value) { this.state = value; }
    public String getPincode() { return pincode; }
    public void setPincode(String value) { this.pincode = value; }
    public String getAddressType() { return addressType; }
    public void setAddressType(String value) { this.addressType = value; }
}
