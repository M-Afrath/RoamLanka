package com.afrath.roamlanka;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TravelCommunityActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TravelCommunityAdapter adapter;  // Adapter for RecyclerView

    private List<CommunityPost> postList = new ArrayList<>();   // Final list displayed in RecyclerView
    private List<CommunityPost> sampleList = new ArrayList<>();  // List containing default sample questions
    private List<CommunityPost> firebaseList = new ArrayList<>();   // List containing questions loaded from Firestore

    private FloatingActionButton fabAsk;  // Floating button used to ask a question
    private FirebaseFirestore db;  // Firestore database instance

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_community);

        recyclerView = findViewById(R.id.recyclerCommunity);
        fabAsk = findViewById(R.id.fabAsk);

        db = FirebaseFirestore.getInstance();

        adapter = new TravelCommunityAdapter(this, postList);  // Create adapter

        recyclerView.setLayoutManager(new LinearLayoutManager(this));  // Set RecyclerView layout manager
        recyclerView.setAdapter(adapter);   // Attach adapter to RecyclerView

        loadSampleOnce();  // Load sample questions
        loadQuestions(); // Load Firestore questions

        fabAsk.setOnClickListener(v -> showAskDialog()); // Open ask-question dialog when FAB is clicked
    }

    // SAMPLE QUESTIONS
    private void loadSampleOnce() {

        if (!sampleList.isEmpty()) return; // Prevent duplicate sample questions

        // Add predefined question 1
        sampleList.add(new CommunityPost(
                "s1",
                "Travel Lanka",
                "system",
                "What are the best places to visit in Sri Lanka?",
                System.currentTimeMillis()
        ));

        // Add predefined question 2
        sampleList.add(new CommunityPost(
                "s2",
                "Safety Guide",
                "system",
                "Is Sri Lanka safe for solo travelers?",
                System.currentTimeMillis()
        ));

        // Add predefined question 3
        sampleList.add(new CommunityPost(
                "s3",
                "Beach Guide",
                "system",
                "Mirissa or Unawatuna – which is better?",
                System.currentTimeMillis()
        ));
    }

    // LOAD QUESTIONS FROM FIRESTORE
    private void loadQuestions() {

        db.collection("community")
                .orderBy("time", Query.Direction.DESCENDING) // Sort by newest questions first
                // Real-time listener
                .addSnapshotListener((snapshots, error) -> {

                    // Show error if loading fails
                    if (error != null) {
                        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    firebaseList.clear();  // Clear previous Firestore data

                    if (snapshots != null) {

                        // Loop through all documents
                        for (DocumentSnapshot doc : snapshots.getDocuments()) {

                            Long time = doc.getLong("time"); // Get post time
                            if (time == null) time = System.currentTimeMillis(); // Use current time if null

                            // Create CommunityPost object
                            firebaseList.add(new CommunityPost(
                                    doc.getString("id"),
                                    doc.getString("username"),
                                    doc.getString("userId"),
                                    doc.getString("question"),
                                    time
                            ));
                        }
                    }

                    // Combine Firestore posts and sample posts
                    postList.clear();

                    postList.addAll(firebaseList);  // latest posts TOP
                    postList.addAll(sampleList);    // sample bottom

                    adapter.notifyDataSetChanged();  // Refresh RecyclerView
                });
    }

    // ASK QUESTION DIALOG
    private void showAskDialog() {

        // Create input field
        EditText input = new EditText(this);
        input.setHint("Enter your question");

        new AlertDialog.Builder(this)
                .setTitle("Ask Question")
                .setView(input)
                .setPositiveButton("Post", (dialog, which) -> {

                    String text = input.getText().toString().trim();  // Get entered question

                    if (text.isEmpty()) {
                        Toast.makeText(this, "Empty question not allowed", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    addQuestion(text);  // Save question
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // ADD NEW QUESTION
    private void addQuestion(String text) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();  // Get currently logged-in user

        // User must be logged in
        if (user == null) {
            Toast.makeText(this, "Login required", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = user.getUid();  // Get user ID

        // Retrieve user's nickname from Firestore
        db.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener(doc -> {

                    String nickname = doc.getString("nickname"); // Get nickname
                    if (nickname == null) nickname = "Anonymous";  // Use default name if nickname is missing

                    String id = db.collection("community").document().getId();  // Generate unique post ID

                    // Create post data map
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", id);
                    map.put("userId", uid);
                    map.put("username", nickname);
                    map.put("question", text);
                    map.put("time", System.currentTimeMillis());

                    // Save post to Firestore
                    db.collection("community")
                            .document(id)
                            .set(map)
                            .addOnSuccessListener(unused ->
                                    Toast.makeText(this, "Posted!", Toast.LENGTH_SHORT).show()
                            );
                });
    }
}