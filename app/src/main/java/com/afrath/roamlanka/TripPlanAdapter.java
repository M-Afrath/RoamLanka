package com.afrath.roamlanka;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


// RecyclerView Adapter for showing list of selected attractions
public class TripPlanAdapter extends RecyclerView.Adapter<TripPlanAdapter.ViewHolder> {

    // Interface to communicate actions
    public interface OnItemInteractionListener {
        void onRemove(String placeName);
       // void onMapView(String placeName);
    }

    private final Context context;
    private List<Attraction> attractionList;  // List of attractions (places in trip)
    private final OnItemInteractionListener listener;  // Listener to handle remove action from Activity

    private double startLat = 0, startLng = 0;
    private boolean hasStartLocation = false;  // Flag to check if starting location is set


    // Constructor
    public TripPlanAdapter(Context context, List<Attraction> attractionList, OnItemInteractionListener listener) {
        this.context = context;
        this.attractionList = attractionList;
        this.listener = listener;
    }


    // This method is called when user enters starting location
    public void setStartLocation(double lat, double lng) {
        this.startLat = lat;
        this.startLng = lng;
        this.hasStartLocation = true;

        // Sort the list based on distance from starting location
        Collections.sort(attractionList, new Comparator<Attraction>() {
            @Override
            public int compare(Attraction a1, Attraction a2) {
                double dist1 = calculateDistance(startLat, startLng, a1.getLatitude(), a1.getLongitude()); // Calculate distance for first attraction
                double dist2 = calculateDistance(startLat, startLng, a2.getLatitude(), a2.getLongitude());  // Calculate distance for second attraction
                return Double.compare(dist1, dist2);  // Sort by nearest distance
            }
        });

        notifyDataSetChanged(); // Refresh RecyclerView after sorting
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate single row layout (item_trip_plan.xml)
        View view = LayoutInflater.from(context).inflate(R.layout.item_trip_plan, parent, false);
        return new ViewHolder(view);
    }

    // BIND DATA TO EACH ROW
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Attraction attraction = attractionList.get(position);  // Get attraction for current row
        String placeName = attraction.getName();
        holder.textPlaceName.setText(placeName); // Show place name

        // If start location is available → show distance and numbering
        if (hasStartLocation) {
            // Calculate distance from start location
            double distance = calculateDistance(startLat, startLng, attraction.getLatitude(), attraction.getLongitude());

            // Show distance in km
            holder.textDistance.setText(String.format("%.2f km", distance));
            holder.textDistance.setVisibility(View.VISIBLE);

            // Show place number in route order
            holder.textPlaceNumber.setVisibility(View.VISIBLE);
            holder.textPlaceNumber.setText(String.valueOf(position + 1));
        } else {
            holder.textDistance.setVisibility(View.GONE);    // If no start location → hide distance
            holder.textPlaceNumber.setVisibility(View.INVISIBLE);  // Hide numbering
        }

        // OPEN MAP BUTTON
        holder.btnMap.setOnClickListener(v -> {
            // Open MapLocationActivity for selected place
            Intent intent = new Intent(context, MapLocationActivity.class);
            intent.putExtra("place_name", attraction.getName());
            intent.putExtra("lat", attraction.getLatitude());
            intent.putExtra("lng", attraction.getLongitude());

            context.startActivity(intent);
        });

        holder.btnRemove.setOnClickListener(v ->
                listener.onRemove(placeName));
    }


    // Return total number of items in list
    @Override
    public int getItemCount() {

        return attractionList.size();
    }

    public void updateList(List<Attraction> updatedList) {
        this.attractionList = updatedList;
        notifyDataSetChanged();
    }

    // DISTANCE CALCULATION
    // Haversine formula (used to calculate distance between 2 GPS points)
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth radius in km
        double latDiff = Math.toRadians(lat2 - lat1);
        double lonDiff = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;  // Return distance in kilometers
    }

    // VIEW HOLDER CLASS
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textPlaceName, textDistance, textPlaceNumber;
        Button btnMap, btnRemove;

        ViewHolder(View itemView) {
            super(itemView);
            textPlaceName = itemView.findViewById(R.id.textPlaceName);
            textDistance = itemView.findViewById(R.id.textDistance);
            textPlaceNumber = itemView.findViewById(R.id.textPlaceNumber);
            btnMap = itemView.findViewById(R.id.btnMap);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
