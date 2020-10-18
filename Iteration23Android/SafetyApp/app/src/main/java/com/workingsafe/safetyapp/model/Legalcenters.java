package com.workingsafe.safetyapp.model;

import java.math.BigDecimal;

public class Legalcenters {
    private String center_name;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String address;
    private String contact_details;
    private String suburbortown;
    private String calculatedDistance;

    public Legalcenters(){}

    public Legalcenters(String center_name, BigDecimal latitude, BigDecimal longitude, String address, String contact_details, String suburbortown, String calculatedDistance) {
        this.center_name = center_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.contact_details = contact_details;
        this.suburbortown = suburbortown;
        this.calculatedDistance = calculatedDistance;
    }

    public String getCenter_name() {
        return center_name;
    }

    public void setCenter_name(String center_name) {
        this.center_name = center_name;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_details() {
        return contact_details;
    }

    public void setContact_details(String contact_details) {
        this.contact_details = contact_details;
    }

    public String getSuburbortown() {
        return suburbortown;
    }

    public void setSuburbortown(String suburbortown) {
        this.suburbortown = suburbortown;
    }

    public String getCalculatedDistance() {
        return calculatedDistance;
    }

    public void setCalculatedDistance(String calculatedDistance) {
        this.calculatedDistance = calculatedDistance;
    }

    @Override
    public String toString() {
        return "Legalcenters{" +
                "center_name='" + center_name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", address='" + address + '\'' +
                ", contact_details='" + contact_details + '\'' +
                ", suburbortown='" + suburbortown + '\'' +
                ", calculatedDistance='" + calculatedDistance + '\'' +
                '}';
    }
}
