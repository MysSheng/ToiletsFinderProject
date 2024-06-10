package com.JavaFinal.ToiletsFinder.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class getLocationModel {
	private double longitude;
	private double latitude;
	private String country;
	private String subDistrict;
	private String locality;
	
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

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSubDistrict() {
		return subDistrict;
	}

	public void setSubDistrict(String subDistrict) {
		this.subDistrict = subDistrict;
	}
}
