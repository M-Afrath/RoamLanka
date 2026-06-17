package com.afrath.roamlanka;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


// It connects image data with RecyclerView UI
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private final Context context;  // Context is needed to access activities and resources
    private List<Integer> imageList;

    // Constructor - called when creating adapter object
    public ImageAdapter(Context context, List<Integer> imageList) {
        this.context = context;  // save context
        this.imageList = imageList;  // save image list
    }

    // Method to update image list dynamically
    public void updateImages(List<Integer> newImages) {
        this.imageList = newImages;   // replace old list with new one
        notifyDataSetChanged();  // refresh RecyclerView
    }

    // Create new item view (called when RecyclerView needs a new item)
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);  // Inflate XML layout file (item_image.xml) into a View object
        return new ImageViewHolder(view);   // Return a new ViewHolder with the created view
    }

    // Bind data to each item (set image for each position)
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        int imageResId = imageList.get(position);  // Get image resource ID from list
        holder.imageView.setImageResource(imageResId);  // Set image into ImageView

        holder.imageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullScreenImageActivity.class); // Create intent to open FullScreenImageActivity
            intent.putExtra("image_res_id", imageResId);  // Send clicked image ID to next activity
            context.startActivity(intent);  // Start new activity
        });
    }

    // Return total number of items in list
    @Override
    public int getItemCount() {

        return imageList.size();
    }

    // ViewHolder class - holds views for each item
    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;  // ImageView inside item layout

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageGalleryItem);
        }
    }
}
