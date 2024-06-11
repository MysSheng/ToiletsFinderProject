package com.JavaFinal.ToiletsFinder.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class updateInput {
    private double longitude;
    private double latitude;
    private String name;
    private String comment;
    private boolean isFree;
    private int floor;
    private boolean accessibility;
    private boolean isGenderFriendly;
    private boolean isDisabledFriendly;

	public void setLongtitude(String longitude) {
		this.longitude = Double.parseDouble(longitude);
	}

    public void setLatitude(String latitude) {
		this.longitude = Double.parseDouble(latitude);
	}

    public void setName(String name) {
		this.name = name;
	}

    public void setComment(String comment) {
		this.comment = comment;
	}

    public void setIsFree(String isFree) {
		this.isFree = Boolean.parseBoolean(isFree);
	}

    public void setFloor(String floor) {
		this.floor = Integer.parseInt(floor);
	}

    public void setAccessbility(String accessibility) {
		this.accessibility = Boolean.parseBoolean(accessibility);
	}

    public void setIsGenderFriendly(String isGenderFriendly) {
		this.isGenderFriendly = Boolean.parseBoolean(isGenderFriendly);
	}

    public void setIsDisabledFriendlyy(String isDisabledFriendly) {
		this.isDisabledFriendly = Boolean.parseBoolean(isDisabledFriendly);
	}

    public double getLongtitude(){
        return longitude;
    }
    public double getLatitude(){
        return latitude;
    }
    public String getName() {
		return name;
	}
    public String getComment(){
        return comment;
    }
    public boolean getIsFree(){
        return isFree;
    }
    public int getFloor(){
        return floor;
    }
    public boolean getAccessbility(){
        return accessibility;
    }
    public boolean getIsGenderFridenly(){
        return isGenderFriendly;
    }
    public boolean getIsDisabledFriendly(){
        return isDisabledFriendly;
    }
}

