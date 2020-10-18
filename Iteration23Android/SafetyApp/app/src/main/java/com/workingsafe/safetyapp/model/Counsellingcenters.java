package com.workingsafe.safetyapp.model;

import java.math.BigDecimal;

public class Counsellingcenters {
    private String counselling_name;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String address;
    private String contact_details;
    private String suburbortown;
    private BigDecimal calculatedDistance;

    public Counsellingcenters(){}

    public Counsellingcenters(String counselling_name, BigDecimal latitude, BigDecimal longitude, String address, String contact_details, String suburbortown, BigDecimal calculatedDistance) {
        this.counselling_name = counselling_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.contact_details = contact_details;
        this.suburbortown = suburbortown;
        this.calculatedDistance = calculatedDistance;
    }

    public String getCounselling_name() {
        return counselling_name;
    }

    public void setCounselling_name(String counselling_name) {
        this.counselling_name = counselling_name;
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

    public BigDecimal getCalculatedDistance() {
        return calculatedDistance;
    }

    public void setCalculatedDistance(BigDecimal calculatedDistance) {
        this.calculatedDistance = calculatedDistance;
    }
}
