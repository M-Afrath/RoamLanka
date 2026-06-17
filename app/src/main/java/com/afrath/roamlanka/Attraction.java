package com.afrath.roamlanka;

import com.google.android.gms.maps.model.LatLng;
import java.io.Serializable;
import java.util.List;

public class Attraction implements Serializable {

    private String name;
    private String description;
    private List<Integer> imageResIds;
    private double latitude;
    private double longitude;
    private double distanceFromStart = -1;

    // CONSTRUCTOR 1 (FULL DATA)
    public Attraction(String name, String description, List<Integer> imageResIds, double latitude, double longitude) {
        this.name = name;
        this.description = description;
        this.imageResIds = imageResIds;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // CONSTRUCTOR 2 (WITHOUT LOCATION)
    public Attraction(String name, String description, List<Integer> imageResIds) {
        this(name, description, imageResIds, 0.0, 0.0);
    }

    public double getDistanceFromStart() {

        return distanceFromStart;
    }

    private boolean saved = false;   // Used to check if place is saved in user favorites

    public void setDistanceFromStart(double distanceFromStart) {
        this.distanceFromStart = distanceFromStart;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Integer> getImageResIds() {
        return imageResIds;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    //SAVED STATUS (FAVORITES FEATURE)
    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}


