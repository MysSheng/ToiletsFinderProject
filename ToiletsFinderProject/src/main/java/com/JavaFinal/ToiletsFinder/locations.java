package com.JavaFinal.ToiletsFinder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class locations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    public locations(double lat, double lng, String name, String comment, boolean isFree, int floor, boolean accessibility, boolean isGenderFriendly, boolean isDisabledFriendly) {
        this.latitude = lat;
        this.longitude = lng;
        this.name = name;
        this.comment = comment;
        this.isFree = isFree;
        this.floor = floor;
        this.accessibility = accessibility;
        this.isDisabledFriendly = isDisabledFriendly;
        this.isGenderFriendly = isGenderFriendly;
    }
}

