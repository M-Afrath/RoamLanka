package com.afrath.roamlanka;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SavedTripsActivity extends AppCompatActivity {
    private ListView tripListView;
    private List<SavedTripPlan> savedTrips;  // This list holds all saved trips loaded from Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_trips);

        tripListView = findViewById(R.id.tripListView);
        loadSavedTrips();  // Load all saved trips from Firebase


        // CLICK ON A TRIP (OPEN TRIP)
        tripListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SavedTripPlan selectedTrip = savedTrips.get(position);  // Get selected trip from list

                Intent intent = new Intent(SavedTripsActivity.this, PlanYourTripActivity.class);
                intent.putExtra("saved_trip", new Gson().toJson(selectedTrip));
                startActivity(intent);
            }
        });

        // LONG CLICK (DELETE TRIP)
        tripListView.setOnItemLongClickListener((parent, view, position, id) -> {

            SavedTripPlan tripToDelete = savedTrips.get(position);  // Get trip user wants to delete

            // Show confirmation dialog
            new AlertDialog.Builder(SavedTripsActivity.this)
                    .setTitle("Delete Trip")
                    .setMessage("Are you sure you want to delete \"" + tripToDelete.getTripName() + "\"?")
                    // If user clicks DELETE
                    .setPositiveButton("Delete", (dialog, which) -> {
                        FirestoreHelper firestoreHelper = new FirestoreHelper();

                        // Delete trip from Firebase
                        firestoreHelper.deleteTrip(
                                tripToDelete.getTripName(),
                                success -> {

                                 runOnUiThread(() -> {

                                        if (success) {
                                            loadSavedTrips(); // Reload updated list
                                            Toast.makeText(SavedTripsActivity.this, "Trip deleted", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(SavedTripsActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                });
                    })
                    // If user clicks CANCEL
                    .setNegativeButton("Cancel", null)
                    .show();

            return true;
        });
    }

    // LOAD ALL SAVED TRIPS FROM FIREBASE
    private void loadSavedTrips() {

        FirestoreHelper firestoreHelper = new FirestoreHelper();

        // Get all saved trips from Firebase
        firestoreHelper.getAllTrips(trips -> {
            savedTrips = trips;  // Store received trips in local list
            List<String> tripNames = new ArrayList<>();   // Create a list to show only trip names in ListView

            for (SavedTripPlan trip : savedTrips) {
                tripNames.add(trip.getTripName());
            }

            // Update UI on main thread
            runOnUiThread(() -> {
                // Adapter to show trip names in ListView
                ArrayAdapter<String> adapter = new ArrayAdapter<>(SavedTripsActivity.this, R.layout.trip_list_item, R.id.tripNameTextView, tripNames);

                tripListView.setAdapter(adapter);  // Set adapter to ListView
            });
        });
    }

}

