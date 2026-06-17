package com.afrath.roamlanka;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnswersActivity extends AppCompatActivity {

    RecyclerView recyclerView;  // RecyclerView used to display answers
    EditText answerInput;
    Button btnAnswer;

    ArrayList<AnswerModel> answerList;  // List that stores all answers
    AnswerAdapter adapter;  // Adapter for RecyclerView

    FirebaseFirestore db;   // Firestore database instance

    String questionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);

        questionId = getIntent().getStringExtra("questionId"); // Get question ID from previous activity

        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.answerRecycler);

        answerInput = findViewById(R.id.answerInput);

        btnAnswer = findViewById(R.id.btnAnswer);

        answerList = new ArrayList<>(); // Create empty answer list

        adapter = new AnswerAdapter(this, answerList, questionId);  // Create adapter

        // Set RecyclerView layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);  // Attach adapter to RecyclerView

        loadAnswers(); // Load existing answers

        btnAnswer.setOnClickListener(v -> {

            // Get answer text entered by user
            String text = answerInput.getText().toString().trim();

            // Check if answer is not empty
            if (!text.isEmpty()) {
                addAnswer(text);   // Save answer
                answerInput.setText("");  // Clear input field
            }
        });
    }

  /*  public String getQuestionId() {
        return questionId;
    } */  // Returns selected question ID

    // Add a new answer to Firestore
    private void addAnswer(String text) {

        // Get current user's ID
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Retrieve user's nickname from Firestore
        db.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener(doc -> {

                    String nickname = doc.getString("nickname");

                    // Use default name if nickname is empty
                    if (nickname == null || nickname.isEmpty()) {
                        nickname = "Anonymous";
                    }

                    // Generate unique answer ID
                    String answerId =
                            db.collection("community")
                                    .document(questionId)
                                    .collection("answers")
                                    .document()
                                    .getId();

                    // Create answer data map
                    Map<String, Object> map = new HashMap<>();

                    map.put("id", answerId);
                    map.put("userId", uid);
                    map.put("username", nickname);
                    map.put("answer", text);
                    map.put("time", System.currentTimeMillis()); // Save current time

                    // Save answer to Firestore
                    db.collection("community")
                            .document(questionId)
                            .collection("answers")
                            .document(answerId)
                            .set(map);
                });
    }

    // Load answers from Firestore
    private void loadAnswers() {

        db.collection("community")
                .document(questionId)
                .collection("answers")
                .orderBy("time", Query.Direction.DESCENDING) // Show newest answers first
                // Real-time listener
                .addSnapshotListener((snapshots, error) -> {

                    answerList.clear(); // Clear old data

                    if (snapshots != null) {

                        // Loop through all answer documents
                        for (DocumentSnapshot doc : snapshots.getDocuments()) {
                            // Convert Firestore document into AnswerModel object
                            answerList.add(
                                    new AnswerModel(
                                            doc.getString("id"),
                                            doc.getString("userId"),
                                            doc.getString("username"),
                                            doc.getString("answer"),
                                            doc.getLong("time")
                                    )
                            );
                        }
                    }

                    adapter.notifyDataSetChanged();  // Refresh RecyclerView

                    // Scroll to latest answer
                    if (!answerList.isEmpty()) {
                        recyclerView.scrollToPosition(0);
                    }
                });
    }
}