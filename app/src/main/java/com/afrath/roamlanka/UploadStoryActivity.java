package com.afrath.roamlanka;

 import android.os.Bundle;
 import android.widget.*;
 import androidx.appcompat.app.AppCompatActivity;

 import com.google.firebase.auth.*;
 import com.google.firebase.firestore.*;

 public class UploadStoryActivity extends AppCompatActivity {

     EditText etTitle, etDescription, etLocation;
     FirebaseFirestore db = FirebaseFirestore.getInstance();  // Create Firestore database instance

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_upload_story);

         etTitle = findViewById(R.id.etTitle);
         etDescription = findViewById(R.id.etDescription);
         etLocation = findViewById(R.id.etLocation);

         findViewById(R.id.btnUploadStory).setOnClickListener(v -> uploadStory());
     }

     // Method to upload a story to Firestore
     private void uploadStory() {
         // Get user input and remove extra spaces
         String title = etTitle.getText().toString().trim();
         String desc = etDescription.getText().toString().trim();
         String location = etLocation.getText().toString().trim();

         // Check whether all fields are filled
         if (title.isEmpty() || desc.isEmpty() || location.isEmpty()) {
             Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
             return;
         }

         FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();   // Get currently logged-in Firebase user
         if (user == null) return;  // Stop if no user is logged in

         String uid = user.getUid();  // Get user ID

         // Retrieve user details from Firestore
         db.collection("users").document(uid).get()
                 .addOnSuccessListener(doc -> {

                     String nickname = doc.getString("nickname");  // Get user's nickname

                     // Use default name if nickname is missing
                     if (nickname == null || nickname.isEmpty()) {
                         nickname = "Anonymous";
                     }
                     // Generate a unique document ID for the story
                     String id = db.collection("stories").document().getId();

                     // Create TravelStory object
                     TravelStory story = new TravelStory(id, title, desc, location, uid, nickname, System.currentTimeMillis());

                     // Save story object to Firestore
                     db.collection("stories")
                             .document(id)
                             .set(story)
                             .addOnSuccessListener(a ->
                                     Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show()
                             );

                     finish();  // Close this activity and return to the previous screen
                 });
     }
 }