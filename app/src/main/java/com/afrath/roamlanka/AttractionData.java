package com.afrath.roamlanka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttractionData {
    private static Map<String, List<Attraction>> cityAttractionsMap = new HashMap<>();

    // This method is used to load data into the map
    public static void setCityAttractionsMap(Map<String, List<Attraction>> map) {
        cityAttractionsMap = map;
    }

    public static Map<String, List<Attraction>> getCityAttractionsMap() {
        return cityAttractionsMap;
    }

    // This method searches ALL cities and finds an attraction by its name
    public static Attraction getAttractionByName(String name) {
        // Loop through all cities
        for (List<Attraction> attractions : cityAttractionsMap.values()) {
            // Loop through all attractions in each city
            for (Attraction attraction : attractions) {
                // Check if name matches
                if (attraction.getName().equals(name)) {
                    return attraction;   // Return matched attraction
                }
            }
        }
        return null;  // If not found, return null
    }
}
