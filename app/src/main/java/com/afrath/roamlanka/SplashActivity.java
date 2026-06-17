package com.afrath.roamlanka;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;   // Declare FirebaseAuth object



    // onCreate() runs when activity starts
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Firebase initialize
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();    // Get Firebase Authentication instance

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            SharedPreferences preferences = getSharedPreferences("AppSettings", MODE_PRIVATE);  // Open SharedPreferences file called "AppSettings"
            boolean isFirstRun = preferences.getBoolean("isFirstRun", true);  // If value does not exist, default value will be true

            // Firebase user check
            if (mAuth.getCurrentUser() != null) {

                // Reload to verify user still exists
                mAuth.getCurrentUser().reload().addOnCompleteListener(task -> {
                    if (task.isSuccessful() && mAuth.getCurrentUser() != null) {
                        // User valid → go Home
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    } else {
                        // User account deleted/invalid → sign out
                        mAuth.signOut();
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    finish();   // Close SplashActivity
                });

            } else {
                // No user logged in
                Intent intent;  // Declare Intent variable

                if (isFirstRun) {  // Check whether app is opening first time
                    intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                } else {   // Not first time → open LoginActivity
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }

                startActivity(intent);  // Start selected activity
                finish();  // Close SplashActivity
            }

        }, 2000); // splash delay 2000 milliseconds

    }
}