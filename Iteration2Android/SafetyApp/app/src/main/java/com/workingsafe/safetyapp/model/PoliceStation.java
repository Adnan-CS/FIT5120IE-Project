package com.workingsafe.safetyapp.model;

public class PoliceStation {
    private String station_address;
    private String suburb;
    private String postcode;
    private String phone;
    private double latitude;
    private double longitude;
    private double calculatedDistance;
    public PoliceStation(){}

    public PoliceStation(String station_address, String suburb, String postcode, String phone, double latitude, double longitude, double calculatedDistance) {
        this.station_address = station_address;
        this.suburb = suburb;
        this.postcode = postcode;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.calculatedDistance = calculatedDistance;
    }

    public String getStation_address() {
        return station_address;
    }

    public void setStation_address(String station_address) {
        this.station_address = station_address;
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
