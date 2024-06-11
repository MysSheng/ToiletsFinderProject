package com.JavaFinal.ToiletsFinder.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class getLocationModel {
	private double longitude;
	private double latitude;
	private String countryName;
	private String principalSubdivision;
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

	public String getPrincipalSubdivision() {
		return principalSubdivision;
	}

	public void setPrincipalSubdivision(String principalSubdivision) {
		this.principalSubdivision = principalSubdivision;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getcountryName() {
		return countryName;
	}

	public void setCountryName(String countryCode) {
		this.countryName = countryCode;
	}
}
