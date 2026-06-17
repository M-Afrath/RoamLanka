package com.afrath.roamlanka;

 import android.content.Intent;
 import android.os.Bundle;
 import android.widget.*;

 import androidx.appcompat.app.AppCompatActivity;

 import com.google.firebase.auth.AuthCredential;
 import com.google.firebase.auth.EmailAuthProvider;
 import com.google.firebase.auth.FirebaseAuth;
 import com.google.firebase.auth.FirebaseUser;
 import com.google.firebase.firestore.FirebaseFirestore;
 import com.google.firebase.firestore.SetOptions;

 import java.util.HashMap;
 import java.util.Map;

 public class SettingsActivity extends AppCompatActivity {

     EditText etNickname;
     Switch switchNotifications;
     Button btnLogout, btnSaveUsername,  btnDeleteAccount, btnSendFeedback;

     FirebaseAuth mAuth;
     FirebaseFirestore db;

     String userId;
     boolean isLoading = false; // used to prevent unwanted switch triggers while loading data

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_settings);

         etNickname = findViewById(R.id.etNickname);
         switchNotifications = findViewById(R.id.switchNotifications);
         btnLogout = findViewById(R.id.btnLogout);
         btnSaveUsername = findViewById(R.id.btnSaveUsername);
         btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
         btnSendFeedback = findViewById(R.id.btnSendFeedback);

         // Initialize Firebase
         mAuth = FirebaseAuth.getInstance();
         db = FirebaseFirestore.getInstance();

         FirebaseUser user = mAuth.getCurrentUser(); // Get current logged-in user

         // If user is not logged in, redirect to Login screen
         if (user == null) {
             startActivity(new Intent(this, LoginActivity.class));
             finish();
             return;
         }

         userId = user.getUid();  // store user ID

         loadUserData(); // Load user data from Firestore

         // SAVE SETTINGS
         btnSaveUsername.setOnClickListener(v -> {

             String nickname = etNickname.getText().toString().trim();

             // Validation check
             if (nickname.isEmpty()) {
                 Toast.makeText(this, "Nickname cannot be empty", Toast.LENGTH_SHORT).show();
                 return;
             }

             // Data to update in Firestore
             Map<String, Object> updates = new HashMap<>();
             updates.put("nickname", nickname);
             updates.put("notifications", switchNotifications.isChecked());

             // Update Firestore document (merge = keep old + update new)
             db.collection("users")
                     .document(userId)
                     .set(updates, SetOptions.merge())
                     .addOnSuccessListener(unused ->
                             Toast.makeText(this, "Nickname updated", Toast.LENGTH_SHORT).show()
                     )
                     .addOnFailureListener(e ->
                             Toast.makeText(this, "Update failed: " + e.getMessage(), Toast.LENGTH_LONG).show()
                     );
         });

         // NOTIFICATION SWITCH
         switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {

             if (isLoading) return; // Prevent trigger during initial loading

             Map<String, Object> map = new HashMap<>();
             map.put("notifications", isChecked);

             // Update only notification setting
             db.collection("users")
                     .document(userId)
                     .set(map, SetOptions.merge());
         });


         // FEEDBACK EMAIL
         btnSendFeedback.setOnClickListener(v -> {
             // Open email app
             Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
             emailIntent.setData(android.net.Uri.parse("mailto:"));

             // Receiver email
             emailIntent.putExtra(Intent.EXTRA_EMAIL,
                     new String[]{"roamlanka.feedback@gmail.com"});

             emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                     "RoamLanka App Feedback");

             emailIntent.putExtra(Intent.EXTRA_TEXT,
                     "Hello,\n\nI would like to share feedback:\n\n");

             try {
                 startActivity(Intent.createChooser(emailIntent, "Send Feedback"));
             } catch (Exception e) {
                 Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show();
             }
         });

         // LOGOUT
         btnLogout.setOnClickListener(v -> {

             new androidx.appcompat.app.AlertDialog.Builder(this)
                     .setTitle("Logout")
                     .setMessage("Are you sure you want to logout?")
                     .setPositiveButton("Yes", (dialog, which) -> {

                         mAuth.signOut(); // Sign out user

                         // Go to login screen and clear back stack
                         Intent intent = new Intent(this, LoginActivity.class);
                         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                         startActivity(intent);

                     })
                     .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                     .show();
         });

         // DELETE ACCOUNT
         btnDeleteAccount.setOnClickListener(v -> {

             new androidx.appcompat.app.AlertDialog.Builder(this)
                     .setTitle("Delete Account")
                     .setMessage("This action is permanent. Do you really want to delete your account?")
                     .setPositiveButton("Delete", (dialog, which) -> {

                         deleteAccount();  // call delete process

                     })
                     .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                     .show();
         });

     }

     // LOAD USER DATA
     private void loadUserData() {

         isLoading = true;  // prevent switch listener trigger

         db.collection("users").document(userId)
                 .get()
                 .addOnSuccessListener(doc -> {

                     if (doc.exists()) {

                         etNickname.setText(doc.getString("nickname")); // Load nickname from Firestore

                         // Load notification setting
                         Boolean notif = doc.getBoolean("notifications");
                         switchNotifications.setChecked(Boolean.TRUE.equals(notif));

                     } else {
                         // If user doc doesn't exist, create default values
                         Map<String, Object> defaultData = new HashMap<>();
                         defaultData.put("nickname", "");
                         defaultData.put("notifications", true);

                         db.collection("users")
                                 .document(userId)
                                 .set(defaultData);

                         switchNotifications.setChecked(true);
                     }

                     isLoading = false;
                 })
                 .addOnFailureListener(e -> {
                     isLoading = false;
                     Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show();
                 });
     }

     // DELETE ACCOUNT FLOW
     private void deleteAccount() {

         FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

         if(user == null) return;

         // Ask user password for security
         EditText passwordInput = new EditText(this);
         passwordInput.setHint("Enter your password");

         new androidx.appcompat.app.AlertDialog.Builder(this)
                 .setTitle("Confirm Password")
                 .setMessage("Please enter your password to delete account")
                 .setView(passwordInput)
                 .setPositiveButton("Confirm",
                         (dialog, which) -> {

                             String password = passwordInput.getText().toString().trim();

                             reAuthenticateAndDelete(user, password);
                         })

                 .setNegativeButton("Cancel", null)
                 .show();
     }

     // RE-AUTHENTICATE USER BEFORE DELETE
     private void reAuthenticateAndDelete(FirebaseUser user, String password) {

         String email = user.getEmail();

         // Create login credential again (security step)
         AuthCredential credential = EmailAuthProvider.getCredential(email, password);

         // Verify password first
         user.reauthenticate(credential).addOnSuccessListener(unused -> {

                     String uid = user.getUid();

                     // Delete Firestore user data
                     db.collection("users")
                             .document(uid)
                             .delete()
                             .addOnSuccessListener(aVoid -> {

                                 // Delete Firebase Authentication account
                                 user.delete()
                                         .addOnSuccessListener(v -> {

                                             Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT).show();

                                             // Redirect to login screen
                                             Intent intent = new Intent(this, LoginActivity.class);
                                             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                             startActivity(intent);
                                             finish();

                                         });

                             });

                 })
                 .addOnFailureListener(e -> {

                     Toast.makeText(
                             this,
                             "Incorrect password",
                             Toast.LENGTH_LONG
                     ).show();

                 });
     }
 }