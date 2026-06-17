package com.afrath.roamlanka;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.ViewHolder> {

    private final Context context;   // activity context
    private final List<Attraction> attractionList;  // List of attractions

    private final FirestoreHelper firestoreHelper;   // Handles Firestore database operations


    // Constructor
    public AttractionAdapter(Context context, List<Attraction> attractionList) {
        this.context = context;  // Assign context received from activity
        this.attractionList = attractionList;  // Assign attraction list received from activity
        this.firestoreHelper = new FirestoreHelper();    // Create Firestore helper
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Load item layout
        View view = LayoutInflater.from(context).inflate(R.layout.item_attraction, parent, false);
        return new ViewHolder(view); // Create and return ViewHolder object
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Attraction attraction = attractionList.get(position);  // Get current attraction
        String placeName = attraction.getName();

        holder.textTitle.setText(placeName);  // Show attraction title
        holder.textDescription.setText(attraction.getDescription());
        holder.textPlaceNumber.setText(String.valueOf(position + 1) + ".");  // Show attraction number

        // Get attraction images
        List<Integer> images = attraction.getImageResIds();

        // Show image gallery if images exist
        if (images != null && !images.isEmpty()) {
            holder.recyclerImageGallery.setVisibility(View.VISIBLE);
            holder.imageAdapter.updateImages(images);
        } else {
            holder.recyclerImageGallery.setVisibility(View.GONE);  // Hide gallery if no images
        }

        // Check whether attraction is already saved
        firestoreHelper.isPlaceSaved(placeName, isSaved -> {

            if (holder.getAdapterPosition() == RecyclerView.NO_POSITION) {
                return;
            }

            attraction.setSaved(isSaved); // Update attraction save status

            holder.btnSaveToPlan.setTag(isSaved);  // Store save status
            updateHeartIcon(holder.btnSaveToPlan, isSaved);  // Update heart icon
        });

        // Save button click
        holder.btnSaveToPlan.setOnClickListener(v -> {

            // Get current save status
            boolean isSaved = holder.btnSaveToPlan.getTag() != null
                    && (boolean) holder.btnSaveToPlan.getTag();
            // If already saved
            if (isSaved) {

                attraction.setSaved(false); // Mark as not saved

                holder.btnSaveToPlan.setTag(false);  // Update button state
                updateHeartIcon(holder.btnSaveToPlan, false);  // Change heart icon

                // Remove from Firestore
                firestoreHelper.removePlace(placeName, success -> {

                    if (holder.getAdapterPosition() == RecyclerView.NO_POSITION) {
                        return;
                    }

                    // If remove failed
                    if (!success) {

                        // delete failed -> Restore previous state
                        attraction.setSaved(true);

                        holder.btnSaveToPlan.setTag(true);
                        updateHeartIcon(holder.btnSaveToPlan, true);

                        Toast.makeText(
                                context,
                                "REMOVE FAILED",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });

            } else {
                attraction.setSaved(true); // Mark as saved

                holder.btnSaveToPlan.setTag(true);  // Update button state
                updateHeartIcon(holder.btnSaveToPlan, true);  // Change heart icon

                // Save attraction to Firestore
                firestoreHelper.savePlace(placeName, success -> {

                    if (success) {

                        Toast.makeText(
                                context,
                                placeName + " SAVED",
                                Toast.LENGTH_SHORT
                        ).show();

                    } else {

                        Toast.makeText(
                                context,
                                "SAVE FAILED",
                                Toast.LENGTH_SHORT
                        ).show();

                        // Restore previous state
                        attraction.setSaved(false);
                        holder.btnSaveToPlan.setTag(false);
                        updateHeartIcon(holder.btnSaveToPlan, false);
                    }
                });
            }
        });
    }



    @Override
    public int getItemCount() {

        return attractionList.size();  // Return number of attractions
    }


    // Change heart icon based on save status
    private void updateHeartIcon(ImageButton button, boolean isSaved) {
        if (isSaved) {
            button.setImageResource(R.drawable.ic_heart_filled);
        } else {
            button.setImageResource(R.drawable.ic_heart_outline);
        }
    }

    // Holds views for each RecyclerView item
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textDescription, textPlaceNumber;
        RecyclerView recyclerImageGallery;
        ImageAdapter imageAdapter;
        ImageButton btnSaveToPlan;

        ViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textAttractionTitle);
            textDescription = itemView.findViewById(R.id.textAttractionDesc);
            textPlaceNumber = itemView.findViewById(R.id.textPlaceNumber);
            btnSaveToPlan = itemView.findViewById(R.id.btnSaveToPlan);

            recyclerImageGallery = itemView.findViewById(R.id.recyclerImageGallery);
            recyclerImageGallery.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            imageAdapter = new ImageAdapter(context, new ArrayList<>());  // Create image adapter
            recyclerImageGallery.setAdapter(imageAdapter);
        }
    }
}
