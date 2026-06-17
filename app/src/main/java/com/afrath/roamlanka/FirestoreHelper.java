package com.afrath.roamlanka;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.*;

public class FirestoreHelper {

    private final FirebaseFirestore db;  // Firestore database reference
    private final String userId;  // Current logged-in user's ID

    // Constructor
   public FirestoreHelper() {
       db = FirebaseFirestore.getInstance();  // Get Firestore instance
       FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();   // Get currently logged-in Firebase user

       // If no user is logged in
       if (user == null) {
           userId = null;
           return;
       }

       userId = user.getUid();  // Store user ID for later database operations
   }

    // Generic callback used for operations that return true or false.
    public interface BooleanCallback {
        void onResult(boolean value);
    }

    // SAVE PLACE (used by adapter)  users/{userId}/savedPlaces/{placeName}
    public void savePlace(String placeName, BooleanCallback callback) {
        if (!isValidUser()) return;  // Stop if user is not valid

        Map<String, Object> data = new HashMap<>();
        data.put("name", placeName);

        db.collection("users")
                .document(userId)
                .collection("savedPlaces")
                .document(placeName)
                .set(data)
                // Success
                .addOnSuccessListener(unused -> callback.onResult(true))
                // Failure
                .addOnFailureListener(e -> {

                    e.printStackTrace();

                    callback.onResult(false);
                });
    }


    // Delete a saved place from Firestore
    public void removePlace(String placeName, BooleanCallback callback) {
        if (!isValidUser()) return;

        db.collection("users")
                .document(userId)
                .collection("savedPlaces")
                .document(placeName)
                .delete()

                .addOnSuccessListener(unused -> callback.onResult(true))
                .addOnFailureListener(e -> callback.onResult(false));
    }

   // Check whether a place already exists in the savedPlaces collection.
    public void isPlaceSaved(String placeName, BooleanCallback callback) {
        if (!isValidUser()) return;

        db.collection("users")
                .document(userId)
                .collection("savedPlaces")
                .document(placeName)
                .get()
                .addOnSuccessListener(doc -> callback.onResult(doc.exists()))
                .addOnFailureListener(e -> callback.onResult(false));
    }

    // GET ALL SAVED PLACES

    // Callback for returning a list of places
    public interface PlacesCallback {
        void onResult(List<String> places);
    }

    // Read all saved places of current user.
    public void getAllPlaces(PlacesCallback callback) {
        if (!isValidUser()) return;

        db.collection("users")
                .document(userId)
                .collection("savedPlaces")
                .get()
                .addOnSuccessListener(query -> {

                    List<String> list = new ArrayList<>();

                    // Loop through all documents
                    for (DocumentSnapshot doc : query) {
                        String name = doc.getString("name");
                        if (name != null) list.add(name);
                    }

                    callback.onResult(list);
                });
    }

    // START LOCATION CALLBACK

    // Used when returning:location name, latitude and longitude.
    public interface StartCallback {
        void onResult(String name, double lat, double lng);
    }

    // GET START LOCATION

    // Read user's saved starting location.
    public void getStartLocation(StartCallback callback) {
        if (!isValidUser()) return;

        db.collection("users")
                .document(userId)
                .collection("startLocation")
                .document("current")
                .get()
                .addOnSuccessListener(doc -> {

                    if (doc.exists()) {

                        String name = doc.getString("name");
                        Double lat = doc.getDouble("lat");
                        Double lng = doc.getDouble("lng");

                        callback.onResult(name, lat != null ? lat : 0,
                                lng != null ? lng : 0);
                    }
                });
    }

    // SAVE START LOCATION

    // Save user's selected start location.
    public void saveStartLocation(String name, double lat, double lng) {
        if (!isValidUser()) return;

        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("lat", lat);
        data.put("lng", lng);

        db.collection("users")
                .document(userId)
                .collection("startLocation")
                .document("current")
                .set(data);
    }

    // SAVE TRIP PLAN

    //  Save a complete trip plan.
    public void saveTrip(SavedTripPlan plan) {
        if (!isValidUser()) return;

        Map<String, Object> data = new HashMap<>();
        data.put("tripName", plan.getTripName());
        data.put("startingLocation", plan.getStartingLocation());
        data.put("places", plan.getPlaces());

        db.collection("users")
                .document(userId)
                .collection("trips")
                .document(plan.getTripName())
                .set(data);
    }

    // CLEAR ALL SAVED PLACES

    // Delete every place inside savedPlaces collection.
    public void clearAllPlaces(BooleanCallback callback) {
        if (!isValidUser()) return;

        db.collection("users")
                .document(userId)
                .collection("savedPlaces")
                .get()
                .addOnSuccessListener(query -> {

                    for (DocumentSnapshot doc : query.getDocuments()) {
                        doc.getReference().delete();
                    }

                    callback.onResult(true);
                })
                .addOnFailureListener(e -> callback.onResult(false));
    }

    // GET ALL SAVED TRIPS

    //  Callback used to return a list of trips.
    public interface TripsCallback {
        void onResult(List<SavedTripPlan> trips);
    }

    // Read all saved trips from Firestore.
    public void getAllTrips(TripsCallback callback) {
        if (!isValidUser()) return;

        db.collection("users")
                .document(userId)
                .collection("trips")
                .get()
                .addOnSuccessListener(query -> {

                    List<SavedTripPlan> trips = new ArrayList<>();

                    for (DocumentSnapshot doc : query.getDocuments()) {

                        String tripName = doc.getString("tripName");
                        String startingLocation = doc.getString("startingLocation");

                        List<String> places =
                                (List<String>) doc.get("places");

                        trips.add(new SavedTripPlan(
                                tripName,
                                startingLocation,
                                places
                        ));
                    }

                    callback.onResult(trips);
                });
    }

    // DELETE TRIP

    //  Delete one trip using trip name.
    public void deleteTrip(String tripName, BooleanCallback callback) {
        if (!isValidUser()) return;

        db.collection("users")
                .document(userId)
                .collection("trips")
                .document(tripName)
                .delete()

                .addOnSuccessListener(unused -> callback.onResult(true))
                .addOnFailureListener(e -> callback.onResult(false));
    }

    // CHECK USER VALIDITY

    // Returns true if a user is logged in.
    private boolean isValidUser() {
        return userId != null && !userId.isEmpty();
    }


}