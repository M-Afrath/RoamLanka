package com.afrath.roamlanka;

import android.content.*;
import android.view.*;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TravelStoryAdapter extends RecyclerView.Adapter<TravelStoryAdapter.VH> {
    Context context;  // Context is used to access activities and resources
    ArrayList<TravelStory> list;  // List of travel stories to display
    OnStoryDeleteListener listener;

    public interface OnStoryDeleteListener {
        void onDelete(TravelStory story);  // This method will be called when user wants to delete a story
    }

    // Constructor
    // Receives context, story list, and delete listener
    public TravelStoryAdapter(Context c, ArrayList<TravelStory> l, OnStoryDeleteListener d) {
             context = c;
             list = l;
             listener = d;
    }
    // Creates a new item view when RecyclerView needs one
    @Override
    public VH onCreateViewHolder(ViewGroup p, int v) {
        return new VH(LayoutInflater.from(context)
                .inflate(R.layout.item_travel_story, p, false));
    }

    // Binds data to each RecyclerView item
    @Override
    public void onBindViewHolder(VH h, int i) {

        TravelStory s = list.get(i);   // Get current story from list

        h.title.setText(s.getTitle());   // Set story title
        h.desc.setText(s.getDescription());
        h.location.setText("📍 " + s.getLocation());

        //Check if nickname exists
        String name = (s.getNickname() != null && !s.getNickname().isEmpty())
                ? s.getNickname()
                : "Anonymous";

        h.user.setText("👤 " + name); // Display username

        // Create date format
        SimpleDateFormat sdf =
                new SimpleDateFormat("dd MMM yyyy • HH:mm", Locale.getDefault());

        h.time.setText(sdf.format(new Date(s.getTimestamp())));   // Convert timestamp to readable date and display it

        h.itemView.setOnClickListener(v -> {
            Intent in = new Intent(context, StoryDetailActivity.class);
            in.putExtra("storyId", s.getId());  // Send story ID to detail activity
            context.startActivity(in);
        });

        // Delete story when item is long pressed
        h.itemView.setOnLongClickListener(v -> {
            listener.onDelete(s);
            return true;
        });
    }

    // Returns total number of stories in the list
    @Override
    public int getItemCount() {
        return list.size();
    }


    // Holds references to views inside each RecyclerView item
    static class VH extends RecyclerView.ViewHolder {

        TextView title, desc, location, user, time;
        VH(View v) {
            super(v);
            title = v.findViewById(R.id.storyTitle);
            desc = v.findViewById(R.id.storyDescription);
            location = v.findViewById(R.id.storyLocation);

            user = v.findViewById(R.id.storyUser);
            time = v.findViewById(R.id.storyTime);
        }
    }
}