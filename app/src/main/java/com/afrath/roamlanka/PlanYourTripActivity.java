package com.afrath.roamlanka;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.InputType;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class PlanYourTripActivity extends AppCompatActivity implements TripPlanAdapter.OnItemInteractionListener {

    private RecyclerView recyclerView;
    private EditText editStartingLocation;
    private Button btnSuggestRoute, btnOpenInMaps;
    private TripPlanAdapter adapter; // Adapter for RecyclerView
    private List<Attraction> selectedAttractions;
    private double startLat, startLng;
    private boolean hasValidStartLocation = false;  // To check if valid location is found
    private FirestoreHelper firestoreHelper;  // Helper class for Firebase Firestore operations
    private String savedTripJson;  // JSON string for saved trip (when opened from saved data)


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_your_trip);

        // Link UI elements with XML
        recyclerView = findViewById(R.id.recyclerTripPlan);
        editStartingLocation = findViewById(R.id.editStartingLocation);
        btnSuggestRoute = findViewById(R.id.btnSuggestRoute);
        btnOpenInMaps = findViewById(R.id.btnOpenInMaps);
        ImageButton btnSavePlan = findViewById(R.id.btnSavePlan);
        ImageButton btnViewSavedTrips = findViewById(R.id.btnViewSavedTrips);
        ImageButton btnClearAll = findViewById(R.id.btnClearAll);
        ImageButton btnAddPlace = findViewById(R.id.btnAddPlace);
        firestoreHelper = new FirestoreHelper();  // Initialize Firebase helper

        // Load attraction data into memory
        AttractionData.setCityAttractionsMap(DataProvider.getCityAttractionsMap());
        selectedAttractions = new ArrayList<>();   // Create empty list for selected places

        // Setup RecyclerView adapter
        adapter = new TripPlanAdapter(this, selectedAttractions, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        btnSuggestRoute.setOnClickListener(v -> {
            hideKeyboard();  // hide keyboard
            handleRouteSuggestion();  // calculate best route
        });

        btnOpenInMaps.setOnClickListener(v -> {
            hideKeyboard();
            openMapWithRoute();  // open map screen
        });

        btnSavePlan.setOnClickListener(v -> {
            showSaveTripNameDialog();  // ask trip name
        });

        btnViewSavedTrips.setOnClickListener(v -> {
            Intent intent = new Intent(PlanYourTripActivity.this, SavedTripsActivity.class);
            startActivity(intent);
        });

        btnAddPlace.setOnClickListener(v -> {
            Intent intent = new Intent(PlanYourTripActivity.this, DiscoverActivity.class);
            startActivity(intent);
        });


        //loadSavedPlan();
        savedTripJson = getIntent().getStringExtra("saved_trip");   // Get saved trip JSON from previous screen (if exists)

        if (savedTripJson != null) {
            try {
                // Convert JSON → Java object
                Gson gson = new Gson();
                SavedTripPlan trip = gson.fromJson(savedTripJson, SavedTripPlan.class);

                editStartingLocation.setText(trip.getStartingLocation());  // Set starting location in input box

                selectedAttractions.clear(); // Clear old list

                // Convert place names → Attraction objects
                for (String placeName : trip.getPlaces()) {
                    Attraction a = AttractionData.getAttractionByName(placeName);
                    if (a != null) {
                        selectedAttractions.add(a);
                    }
                }

                adapter.updateList(selectedAttractions); // Update UI

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            loadSavedPlan(); // If no saved trip, load from Firestore
        }

        // This block loads trip again and calculates coordinates
        if (savedTripJson != null) {
            try {
                Gson gson = new Gson();
                SavedTripPlan trip = gson.fromJson(savedTripJson, SavedTripPlan.class);

                // Set starting location
                editStartingLocation.setText(trip.getStartingLocation());
                Geocoder geocoder = new Geocoder(this, Locale.getDefault()); // Convert location name → latitude & longitude
                List<Address> addresses = geocoder.getFromLocationName(trip.getStartingLocation(), 1);
                if (addresses != null && !addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    startLat = address.getLatitude();
                    startLng = address.getLongitude();
                    hasValidStartLocation = true;
                }

                // Set selected places
                selectedAttractions.clear();
                for (String placeName : trip.getPlaces()) {
                    Attraction a = AttractionData.getAttractionByName(placeName);
                    if (a != null) selectedAttractions.add(a);
                }

                sortAttractionsByDistance(); // Sort route by distance
                adapter.updateList(selectedAttractions);  // Update UI

                Toast.makeText(this, "Loaded saved trip: " + trip.getTripName(), Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load saved trip", Toast.LENGTH_SHORT).show();
            }

        }

        btnClearAll.setOnClickListener(v -> {

            new AlertDialog.Builder(this)
                    .setTitle("⚠ Clear Trip Plan")
                    .setMessage("This will remove all places from the current trip. Do you want to continue?")
                    .setPositiveButton("Yes", (dialog, which) -> {

                        selectedAttractions.clear(); // Clear list
                        adapter.updateList(selectedAttractions);

                        editStartingLocation.setText("");  // Clear UI

                        // Reset location
                        startLat = 0;
                        startLng = 0;
                        hasValidStartLocation = false;

                        if (savedTripJson == null) {
                            firestoreHelper.clearAllPlaces(success -> {});
                        }

                        Toast.makeText(PlanYourTripActivity.this, "Current trip cleared", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });


    }


    // SHOW DIALOG TO ENTER TRIP NAME
    private void showSaveTripNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Trip Name");

        // Create input field inside dialog
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);  // Add input field to dialog

        // SAVE button in dialog
        builder.setPositiveButton("Save", (dialog, which) -> {
            String tripName = input.getText().toString().trim();
            if (!tripName.isEmpty()) {
                saveCurrentPlan(tripName);
            } else {
                Toast.makeText(this, "Trip name required.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    // SAVE CURRENT TRIP
    private void saveCurrentPlan(String tripName) {

        if (selectedAttractions.isEmpty()) {
            Toast.makeText(this, "No places to save.", Toast.LENGTH_SHORT).show();
            return;
        }

        String startLocationName = editStartingLocation.getText().toString().trim(); // Get starting location from input field

        // Check if starting location is valid
        if (startLocationName.isEmpty() || !hasValidStartLocation) {
            Toast.makeText(this, "Please suggest a valid starting location before saving.", Toast.LENGTH_SHORT).show();
            return;
        }

        firestoreHelper.saveStartLocation(startLocationName, startLat, startLng);  // Save starting location (lat/lng) to Firestore

        List<String> places = new ArrayList<>();  // Create list of place names (String list)

        // Convert Attraction objects → names
        for (Attraction a : selectedAttractions) {
            places.add(a.getName());
        }

        // Create trip object to store all data
        SavedTripPlan plan = new SavedTripPlan(
                tripName,
                startLocationName,
                places
        );

        firestoreHelper.saveTrip(plan);  // Save trip object to Firestore

        Toast.makeText(this, "Trip saved: " + tripName, Toast.LENGTH_SHORT).show();
    }


    // ROUTE SUGGESTION
    private void handleRouteSuggestion() {
        String locationName = editStartingLocation.getText().toString().trim();
        if (locationName.isEmpty()) {
            Toast.makeText(this, "Please enter your starting location", Toast.LENGTH_SHORT).show();
            return;
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());  // Convert location name → coordinates
        try {
            List<Address> addresses = geocoder.getFromLocationName(locationName, 1);
            if (addresses == null || addresses.isEmpty()) {
                Toast.makeText(this, "Location not found. Please try again.", Toast.LENGTH_SHORT).show();
                return;
            }

            Address address = addresses.get(0);
            startLat = address.getLatitude();
            startLng = address.getLongitude();
            hasValidStartLocation = true;

            // Calculate distance from start to each place
            for (Attraction a : selectedAttractions) {
                double distance = calculateDistance(
                        startLat, startLng,
                        a.getLatitude(), a.getLongitude()
                );
                a.setDistanceFromStart(distance);
            }
            // Sort by nearest place
            Collections.sort(selectedAttractions, Comparator.comparingDouble(Attraction::getDistanceFromStart));

            // update UI
            adapter.setStartLocation(startLat, startLng);
            adapter.updateList(selectedAttractions);

            Toast.makeText(this, "Route suggested successfully", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error finding location.", Toast.LENGTH_SHORT).show();
        }
    }

    private void openMapWithRoute() {
        String locationName = editStartingLocation.getText().toString().trim();
        if (!hasValidStartLocation || locationName.isEmpty()) {
            Toast.makeText(this, "Please suggest a route first", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, MapLocationActivity.class);  // Open map screen
        intent.putExtra("startLat", startLat);
        intent.putExtra("startLng", startLng);
        intent.putExtra("startName", locationName);
        intent.putExtra("attractions", new ArrayList<>(selectedAttractions));
        startActivity(intent);
    }

    // LOAD SELECTED ATTRACTIONS FROM FIRESTORE
    public void loadSelectedAttractions() {

        firestoreHelper.getAllPlaces(places -> {
            selectedAttractions.clear();  // Clear old list first to avoid duplicates

            for (String name : places) {
                Attraction a = AttractionData.getAttractionByName(name); // Convert place name → full Attraction object

                // Add only if attraction exists in local data
                if (a != null) {
                    selectedAttractions.add(a);
                }
            }

            // Update UI on main thread (
            runOnUiThread(() -> {
                Toast.makeText(this, "Places found : " + selectedAttractions.size(), Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            });
        });
    }

    // DISTANCE CALCULATION
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;  // Earth radius in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    // HIDE KEYBOARD
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    // REMOVE PLACE
    @Override
    public void onRemove(String placeName) {

        new AlertDialog.Builder(this)
                .setTitle("Remove Place")
                .setMessage("Remove " + placeName + " from trip plan?")
                .setPositiveButton("Remove", (dialog, which) -> {

                    // Remove from list
                    boolean removed = selectedAttractions.removeIf(a -> a.getName().equals(placeName));
                    if (removed) {
                        adapter.updateList(selectedAttractions);

                        firestoreHelper.removePlace(placeName, success -> {
                            if (success) {
                                Toast.makeText(this, placeName + " removed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // SORT USING NEAREST NEIGHBOR METHOD
    private void sortAttractionsByDistance() {
        if (!hasValidStartLocation || selectedAttractions.isEmpty()) {
            return;
        }
        List<Attraction> sortedRoute = new ArrayList<>();
        List<Attraction> remaining = new ArrayList<>(selectedAttractions);

        double currentLat = startLat;
        double currentLng = startLng;

        // Find nearest place step by step
        while (!remaining.isEmpty()) {
            Attraction nearest = null;
            double minDistance = Double.MAX_VALUE;

            for (Attraction attraction : remaining) {

                double distance = calculateDistance(
                        currentLat,
                        currentLng,
                        attraction.getLatitude(),
                        attraction.getLongitude()
                );

                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = attraction;
                }
            }

            if (nearest != null) {
                nearest.setDistanceFromStart(minDistance);

                sortedRoute.add(nearest);
                remaining.remove(nearest);

                currentLat = nearest.getLatitude();
                currentLng = nearest.getLongitude();
            }
        }

        selectedAttractions.clear();
        selectedAttractions.addAll(sortedRoute);

        adapter.setStartLocation(startLat, startLng);
        adapter.updateList(selectedAttractions);
    }


    // LOAD FROM FIREBASE
    private void loadSavedPlan() {

      selectedAttractions.clear();

      firestoreHelper.getStartLocation((name, lat, lng) -> {

          if (name != null) {
              editStartingLocation.setText(name);
              startLat = lat;
              startLng = lng;
              hasValidStartLocation = true;
          }

          firestoreHelper.getAllPlaces(places -> {
              selectedAttractions.clear();

              for (String placeName : places) {
                  Attraction a = AttractionData.getAttractionByName(placeName);
                  if (a != null) {
                      selectedAttractions.add(a);
                  }
              }
              adapter.notifyDataSetChanged();
          });
      });
  }

    // Reload data when coming back to this screen
   @Override
   protected void onResume() {
       super.onResume();

       if (savedTripJson == null) {
           loadSelectedAttractions();
       }
   }
}



