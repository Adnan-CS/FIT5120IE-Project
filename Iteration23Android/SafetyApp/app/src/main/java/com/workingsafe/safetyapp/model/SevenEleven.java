package com.workingsafe.safetyapp.model;

public class SevenEleven {
    private String store_name;
    private String address;
    private String suburb;
    private String postcode;
    private String phone;
    private double latitude;
    private double longitude;
    private double calculatedDistance;

    public SevenEleven(){}

    public SevenEleven(String store_name, String address, String suburb, String postcode, String phone, double latitude, double longitude, double calculatedDistance) {
        this.store_name = store_name;
        this.address = address;
        this.suburb = suburb;
        this.postcode = postcode;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.calculatedDistance = calculatedDistance;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getCalculatedDistance() {
        return calculatedDistance;
    }

    public void setCalculatedDistance(double calculatedDistance) {
        this.calculatedDistance = calculatedDistance;
    }
}
