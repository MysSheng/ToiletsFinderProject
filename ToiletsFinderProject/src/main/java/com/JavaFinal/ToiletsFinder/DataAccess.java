package com.JavaFinal.ToiletsFinder;

public interface DataAccess {
    void createDatabase();
    void useDatabase();
    void createTable();
    void insertLocation(Location location);
    void updateLocation(Location location);
    void deleteLocation(Location location);
    Location getLocation(Location location);
    Location[] getAllLocations();
}
