package com.workingsafe.safetyapp.model;

import java.io.Serializable;

public class ContactPerson implements Serializable {
    private int id;
    private String name;
    private String number;
    private String message;
    private boolean shareLocation;

    public ContactPerson(){}

    public ContactPerson(int id, String name, String number, String message, boolean shareLocation) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.message = message;
        this.shareLocation = shareLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isShareLocation() {
        return shareLocation;
    }

    public void setShareLocation(boolean shareLocation) {
        this.shareLocation = shareLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
