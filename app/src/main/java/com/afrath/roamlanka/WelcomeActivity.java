package com.afrath.roamlanka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;


// This is the Welcome Screen Activity (first screen of the app)
public class WelcomeActivity extends AppCompatActivity {

    private Button btnContinue;

    // This method runs when the activity is first opened
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      // Call parent activity's onCreate method
        setContentView(R.layout.activity_welcom);

        btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(v -> {
            // Mark first run as false
            SharedPreferences prefs = getSharedPreferences("AppSettings", MODE_PRIVATE);
            prefs.edit().putBoolean("isFirstRun", false).apply();

            // Go to LoginActivity
            startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            finish();  // Close WelcomeActivity So user cannot return to this screen using back button
        });

    }
}