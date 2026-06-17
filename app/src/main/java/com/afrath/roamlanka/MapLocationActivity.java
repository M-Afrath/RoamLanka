package com.afrath.roamlanka;

import android.location.Address;
import android.location.Geocoder;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText searchEditText;
    private ImageButton searchButton;

    private LatLng startLatLng;
    private String startLocationName = "Start Point";

    private List<Attraction> attractionList = new ArrayList<>();  // List of places to show on map
    private boolean isSingleLocation = false;
    private Marker searchMarker;
    private Marker startMarker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(v -> {
            String locationName = searchEditText.getText().toString().trim();
            // Only search if input is not empty
            if (!locationName.isEmpty()) {
                searchAndMoveToLocation(locationName);
            }
        });

        // If only one place is passed (single location view)
        if (getIntent().hasExtra("lat") && getIntent().hasExtra("lng")) {
            double lat = getIntent().getDoubleExtra("lat",  Double.NaN);
            double lng = getIntent().getDoubleExtra("lng", Double.NaN);
            String name = getIntent().getStringExtra("place_name");
            // Convert lat/lng into LatLng object
            if (!Double.isNaN(lat) && !Double.isNaN(lng)) {
                startLatLng = new LatLng(lat, lng);
            }

            isSingleLocation = true; // We are showing only one place
            startLocationName = name != null ? name : "Selected Location";

        } else {
            // Multi-location trip
            double startLat = getIntent().getDoubleExtra("startLat", 0.0);
            double startLng = getIntent().getDoubleExtra("startLng", 0.0);
            startLocationName = getIntent().getStringExtra("startName");

            // Get list of attractions from previous activity
            ArrayList<Attraction> attractions = (ArrayList<Attraction>) getIntent().getSerializableExtra("attractions");

            if (attractions != null) {
                attractionList.addAll(attractions);  // Add all attractions to list
            }

            // Set starting location if valid
            if (startLat != 0.0 || startLng != 0.0) {
                startLatLng = new LatLng(startLat, startLng);
            } else {
                // Default location = Colombo if nothing passed
                startLatLng = new LatLng(6.9271, 79.8612); // Default Colombo
                startLocationName = "Colombo";
            }
        }

        // Load Google Map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(this, "Error loading map", Toast.LENGTH_SHORT).show();
        }
    }

    // Called when map is ready to use
    @Override
     public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;  // Initialize map object

        // Safety check for start location
        if (startLatLng == null || (startLatLng.latitude == 0.0 && startLatLng.longitude == 0.0)) {
            startLatLng = new LatLng(6.9271, 79.8612); // Colombo fallback
            startLocationName = "Colombo";
        }
        // Add start marker on map
        startMarker = mMap.addMarker(
                new MarkerOptions()
                        .position(startLatLng)
                        .title(startLocationName)
        );
        // SINGLE LOCATION MODE
        if (isSingleLocation) {
            mMap.addMarker(new MarkerOptions().position(startLatLng).title(startLocationName));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLatLng, 15f));
        } else {
            // MULTI LOCATION ROUTE MODE
            List<LatLng> points = new ArrayList<>();
            points.add(startLatLng);  // Start point first

            // Add all attraction markers
            for (Attraction attraction : attractionList) {
                LatLng attractionLatLng = new LatLng(attraction.getLatitude(), attraction.getLongitude());
                points.add(attractionLatLng);
                mMap.addMarker(new MarkerOptions().position(attractionLatLng).title(attraction.getName()));
            }

            // Draw route line if more than 1 point
            if (points.size() > 1) {
                PolylineOptions polylineOptions = new PolylineOptions()
                        .addAll(points)
                        .color(Color.BLUE)
                        .width(8);
                mMap.addPolyline(polylineOptions);

                // Zoom map to show all points
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (LatLng point : points) {
                    builder.include(point);
                }
               // mMap.moveCamera
                mMap.setOnMapLoadedCallback(() -> {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 120));
                });
            } else {
                // If only one point, zoom normally
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLatLng, 12f));
            }
        }
    }

    // SEARCH LOCATION AND MOVE MAP
    private void searchAndMoveToLocation(String locationName) {
        if (mMap == null) {
            Toast.makeText(this, "Map not ready yet", Toast.LENGTH_SHORT).show();
            return;
        }

        Geocoder geocoder = new Geocoder(this);
        try {
            // Convert location name → latitude & longitude
            List<Address> addressList = geocoder.getFromLocationName(locationName, 1);
            if (addressList != null && !addressList.isEmpty()) {
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                // Remove old start marker if exists
                if (startMarker != null) {
                    startMarker.remove();
                    startMarker = null;
                }

                // Remove old search marker if exists
                if (searchMarker != null) {
                    searchMarker.remove();
                }

                // Add new search marker
                searchMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(locationName));

                // Move camera to searched location
                mMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(latLng, 15f)
                );
            } else {
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error finding location", Toast.LENGTH_SHORT).show();
        }
    }

}