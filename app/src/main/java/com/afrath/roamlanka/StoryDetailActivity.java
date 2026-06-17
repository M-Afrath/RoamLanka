package com.afrath.roamlanka;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StoryDetailActivity extends AppCompatActivity {

    TextView title, desc, location;

    RecyclerView commentRecycler;
    EditText commentInput;
    Button sendBtn;

    FirebaseFirestore db; // Firestore database instance
    String storyId;

    CommentAdapter commentAdapter;  // Adapter for displaying comments
    ArrayList<CommentModel> commentList = new ArrayList<>();    // List that stores all comments

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);

        db = FirebaseFirestore.getInstance();  // Initialize Firestore

        title = findViewById(R.id.storyTitle);
        desc = findViewById(R.id.storyDescription);
        location = findViewById(R.id.storyLocation);

        commentRecycler = findViewById(R.id.commentRecycler);
        commentInput = findViewById(R.id.commentInput);
        sendBtn = findViewById(R.id.sendBtn);

        storyId = getIntent().getStringExtra("storyId");   // Get story ID passed from previous activity

        // Set RecyclerView layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        commentRecycler.setLayoutManager(layoutManager);

        commentAdapter = new CommentAdapter(this, commentList, storyId);   // Create comment adapter

        commentRecycler.setAdapter(commentAdapter);   // Attach adapter to RecyclerView

        loadStory();  // Load story information
        loadComments();  // Load comments from Firestore

        sendBtn.setOnClickListener(v -> {

            String text = commentInput.getText().toString().trim();    // Get comment text entered by user

            if (text.isEmpty()) {
                Toast.makeText(this, "Enter comment", Toast.LENGTH_SHORT).show();
                return;
            }

            addComment(text);  // Add comment to Firestore
            commentInput.setText("");   // Clear input field after sending
        });
    }


    // Load selected story details from Firestore
    private void loadStory() {

        if (storyId == null) return;  // Stop if story ID is missing

        db.collection("stories")
                .document(storyId)
                .get()
                .addOnSuccessListener(doc -> {

                    // Check if story exists
                    if (doc.exists()) {
                        title.setText(doc.getString("title"));  // Display story title
                        desc.setText(doc.getString("description"));
                        location.setText("📍 " + doc.getString("location"));
                    }
                });
    }

    // Add a new comment to Firestore
    private void addComment(String text) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;

        String uid = user.getUid(); // Get user ID

        // Retrieve user's nickname
        db.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener(doc -> {

                    // Use default name if nickname is missing
                    String nickname = doc.getString("nickname");
                    if (nickname == null) nickname = "Anonymous";

                    // Generate unique comment ID
                    String commentId = db.collection("stories")
                            .document(storyId)
                            .collection("comments")
                            .document()
                            .getId();

                    // Create comment data map
                    Map<String, Object> comment = new HashMap<>();
                    comment.put("id", commentId);
                    comment.put("text", text);
                    comment.put("user", nickname);
                    comment.put("userId", uid);
                    comment.put("time", System.currentTimeMillis());

                    // Save comment in Firestore
                    db.collection("stories")
                            .document(storyId)
                            .collection("comments")
                            .document(commentId)
                            .set(comment);
                });
    }

    // Load comments in real time
    private void loadComments() {

        db.collection("stories")
                .document(storyId)
                .collection("comments")
                .orderBy("time", Query.Direction.DESCENDING)  // Show newest comments first
                .addSnapshotListener((snapshots, error) -> {

                    if (error != null) return;  // Stop if error occurs

                    commentList.clear();  // Clear old comment list

                    if (snapshots != null) {

                        // Loop through all comment documents
                        for (DocumentSnapshot doc : snapshots) {

                            // Create CommentModel object and add it to the list
                            commentList.add(new CommentModel(
                                    doc.getString("id"),
                                    doc.getString("text"),
                                    doc.getString("user"),
                                    doc.getString("userId"),
                                    doc.getLong("time")
                            ));
                        }
                    }

                    commentAdapter.notifyDataSetChanged();   // Refresh RecyclerView
                });
    }
}