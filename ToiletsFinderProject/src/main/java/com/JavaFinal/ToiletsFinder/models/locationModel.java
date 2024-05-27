package com.JavaFinal.ToiletsFinder.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class locationModel {
	private double longitude;
	private double latitude;
	
	
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(@JsonProperty("longtitude") double longtitude) {
		this.longitude = longtitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(@JsonProperty("latutide") double latutide) {
		this.latitude = latutide;
	}
	
	
}
