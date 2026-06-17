package com.afrath.roamlanka;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class TravelStoriesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TravelStoryAdapter adapter;

    ArrayList<TravelStory> list = new ArrayList<>();
    ArrayList<TravelStory> filtered = new ArrayList<>(); // List used to show filtered search results

    EditText searchBox;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_stories);

        recyclerView = findViewById(R.id.recyclerStories);
        searchBox = findViewById(R.id.searchBox);

        db = FirebaseFirestore.getInstance();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create adapter and handle long-click delete action
        adapter = new TravelStoryAdapter(this, filtered, story -> {

            // Show confirmation dialog before deleting
            new AlertDialog.Builder(this)
                    .setTitle("Delete Story")
                    .setMessage("Do you want to delete this story?")
                    .setPositiveButton("Delete", (dialog, which) -> {

                        // Delete selected story from Firestore
                        db.collection("stories").document(story.getId()).delete();

                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        recyclerView.setAdapter(adapter); // Attach adapter to RecyclerView

        Button btnUpload = findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(v ->
                startActivity(new Intent(this, UploadStoryActivity.class))
        );

        loadRealtime();  // Load stories from Firestore in real time
        setupSearch();  // Enable search functionality
    }

    // Load stories from Firestore and update automatically
    private void loadRealtime() {

        db.collection("stories")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener((snap, e) -> {
                    // Stop if an error occurs
                    if (e != null) return;

                    list.clear();  // Clear old data before loading new data

                    if (snap != null) {
                        // Loop through all documents in Firestore
                        for (DocumentSnapshot d : snap.getDocuments()) {
                             // Get nickname from document
                            String nickname = d.getString("nickname");

                             // Get timestamp or use current time if null
                            long time = d.getLong("timestamp") != null
                                    ? d.getLong("timestamp")
                                    : System.currentTimeMillis();

                            // Create TravelStory object and add to list
                            list.add(new TravelStory(
                                    d.getId(),
                                    d.getString("title"),
                                    d.getString("description"),
                                    d.getString("location"),
                                    d.getString("userId"),
                                    nickname,
                                    time
                            ));
                        }
                    }
                    // Show all stories by default
                    filtered.clear();
                    filtered.addAll(list);

                    adapter.notifyDataSetChanged();  // Refresh RecyclerView
                });
    }

    // Set up search box listener
    private void setupSearch() {
        searchBox.addTextChangedListener(new TextWatcher() {

            // Called whenever user types text
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    // Filter stories based on title or location
    private void filter(String q) {

        filtered.clear();  // Clear previous search results

        // Check each story
        for (TravelStory s : list) {
            // Add story if title or location matches search text
            if (s.getTitle().toLowerCase().contains(q.toLowerCase()) ||
                    s.getLocation().toLowerCase().contains(q.toLowerCase())) {

                filtered.add(s);
            }
        }

        adapter.notifyDataSetChanged();  // Refresh RecyclerView with filtered data
    }
}