package com.workingsafe.safetyapp.model;

public class Hospital {
    private String hospital_name;
    private String address;
    private String suburb;
    private String postcode;
    private double latitude;
    private double longitude;
    private double calculatedDistance;
    public Hospital(){}

    public Hospital(String hospital_name, String address, String suburb, String postcode, double latitude, double longitude, double calculatedDistance) {
        this.hospital_name = hospital_name;
        this.address = address;
        this.suburb = suburb;
        this.postcode = postcode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.calculatedDistance = calculatedDistance;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
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
