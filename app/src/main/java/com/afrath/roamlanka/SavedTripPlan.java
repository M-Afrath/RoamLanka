package com.afrath.roamlanka;

import java.util.List;

public class SavedTripPlan {
    private String tripName;
    private String startingLocation;
    private List<String> places;
    public SavedTripPlan() {
    }

    public SavedTripPlan(String tripName, String startingLocation, List<String> places) {
        this.tripName = tripName;
        this.startingLocation = startingLocation;
        this.places = places;
    }

    public String getTripName() {
        return tripName;
    }

    public String getStartingLocation() {
        return startingLocation;
    }

    public List<String> getPlaces() {
        return places;
    }
}
