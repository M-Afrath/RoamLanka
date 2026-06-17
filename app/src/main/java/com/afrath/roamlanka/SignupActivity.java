package com.afrath.roamlanka;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.*;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etConfirmPassword, etNickname;
    Button btnSignup;
    TextView tvBackToLogin;
    CheckBox cbShowPassword, cbShowConfirmPassword;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  // Call parent onCreate method
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();  // Initialize Firebase Authentication
        db = FirebaseFirestore.getInstance();   // Initialize Firestore database

        // Connect Java variables with XML views
        etName = findViewById(R.id.etName);
        etNickname = findViewById(R.id.etNickname);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignup = findViewById(R.id.btnSignup);
        tvBackToLogin = findViewById(R.id.tvBackToLogin);
        cbShowPassword = findViewById(R.id.cbShowPassword);
        cbShowConfirmPassword = findViewById(R.id.cbShowConfirmPassword);

        // Set styled text using HTML
        tvBackToLogin.setText(Html.fromHtml("Already have an account? <font color='#1E88E5'>Login</font>"));

        tvBackToLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });

        btnSignup.setOnClickListener(v -> {
            // Get text from all fields
            String name = etName.getText().toString().trim();
            String nickname = etNickname.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            // Check if any field is empty
            if (name.isEmpty() || nickname.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if passwords match
            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create Firebase account using email and password
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();  // Get current Firebase user
                            String userId = user.getUid();   // Get unique user ID

                            // SAVE USER DATA TO FIRESTORE
                            Map<String, Object> data = new HashMap<>();  // Create HashMap to store user data
                            data.put("name", name);
                            data.put("nickname", nickname);
                            data.put("email", email);
                            data.put("notifications", true);  // Enable notifications by default
                            db.collection("users").document(userId).set(data);  // Save user data in Firestore

                            // UPDATE FIREBASE AUTH PROFILE
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()  // Create profile update request
                                            .setDisplayName(nickname)  // Save nickname as displayName
                                            .build();

                            user.updateProfile(profileUpdates)   // Update Firebase user profile
                                    .addOnCompleteListener(task1 -> {

                                        if (task1.isSuccessful()) {   // If profile updated successfully

                                            Intent intent = new Intent(SignupActivity.this, HomeActivity.class);  // open HomeActivity
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);   // Remove previous activities
                                            startActivity(intent); // Start HomeActivity
                                            finish();  // Close SignupActivity

                                        } else {
                                            Toast.makeText(this, "Profile update failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        } else {
                            Toast.makeText(this, "Signup failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        // Password checkbox listener
        cbShowPassword.setOnCheckedChangeListener((b, isChecked) -> {
            if (isChecked) {  //show password
                etPassword.setInputType(
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                );
            } else {
                etPassword.setInputType(  //hide password
                        InputType.TYPE_CLASS_TEXT |
                                InputType.TYPE_TEXT_VARIATION_PASSWORD
                );
            }
            etPassword.setSelection(etPassword.length());  // Keep cursor at end
        });


        // Confirm password checkbox listener
        cbShowConfirmPassword.setOnCheckedChangeListener((b, isChecked) -> {
            if (isChecked) {
                etConfirmPassword.setInputType(
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                );
            } else {
                etConfirmPassword.setInputType(
                        InputType.TYPE_CLASS_TEXT |
                                InputType.TYPE_TEXT_VARIATION_PASSWORD
                );
            }
            etConfirmPassword.setSelection(etConfirmPassword.length());  // Keep cursor at end
        });
    }
}
