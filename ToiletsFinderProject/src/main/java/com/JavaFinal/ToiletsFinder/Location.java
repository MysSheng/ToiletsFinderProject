package com.JavaFinal.ToiletsFinder;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

public class Location {
    private double longitude;
    private double latitude;
    private String name;
    private String comment;
    private boolean isFree;
    private int floor;
    private boolean accessibility;
    private boolean isGenderFriendly;
    private boolean isDisabledFriendly;



    // Getters and Setters
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean isAccessibility() {
        return accessibility;
    }

    public void setAccessibility(boolean accessibility) {
        this.accessibility = accessibility;
    }

    public boolean isGenderFriendly() {
        return isGenderFriendly;
    }

    public void setGenderFriendly(boolean genderFriendly) {
        isGenderFriendly = genderFriendly;
    }

    public boolean isDisabledFriendly() {
        return isDisabledFriendly;
    }

    public void setDisabledFriendly(boolean disabledFriendly) {
        isDisabledFriendly = disabledFriendly;
    }


    public Location(double lng, double lat) {
        longitude = lng;
        latitude = lat;
        name = "user";
    }

    public Location(double lng, double lat, String name) {
        longitude = lng;
        latitude = lat;
        this.name = name;
    }
}
