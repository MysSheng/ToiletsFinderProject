package com.JavaFinal.ToiletsFinder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DistanceOperation {
	public double getDistance(Location location1, Location location2) {
		double longitude1=location1.getLongitude();
		double longitude2=location2.getLongitude();
		double latitude1=location1.getLatitude();
		double latitude2=location2.getLatitude();
		double theta = longitude1 - longitude2;
		double distance = 60 * 1.1515 * (180/Math.PI) * Math.acos(
		        Math.sin(latitude1 * (Math.PI/180)) * Math.sin(latitude2 * (Math.PI/180)) + 
		        Math.cos(latitude1 * (Math.PI/180)) * Math.cos(latitude2 * (Math.PI/180)) * Math.cos(theta * (Math.PI/180))
		    );
		return distance * 1.609344 * distance * 1.609344;
	}
	
	public void sortByDistance(Location user,List<Location> locations) {
		Collections.sort(locations, new Comparator<Location>() {
	        @Override
	        public int compare(Location location1, Location location2) {
	            double distance1 = getDistance(user, location1);
	            double distance2 = getDistance(user, location2);

	            if (distance1 < distance2) {
	                return -1;
	            } else if (distance1 > distance2) {
	                return 1;
	            } else {
	                return 0;
	            }
	        }
	    });
	}
}

