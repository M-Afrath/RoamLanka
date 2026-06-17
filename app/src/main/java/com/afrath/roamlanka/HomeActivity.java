package com.afrath.roamlanka;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    TextView greetingText;
    ImageView profileIcon;
    MaterialCardView cardDiscover, cardPlan, cardMap, cardStories, cardCommunity, cardWeather, cardSettings;

    private static final int REQUEST_CODE_SETTINGS = 1;  // Used when opening Settings page
    private ActivityResultLauncher<Intent> profileLauncher;  // Used to get data back from Profile page

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        greetingText = findViewById(R.id.greetingText);
        profileIcon = findViewById(R.id.profileIcon);

        cardDiscover = findViewById(R.id.cardDiscover);
        cardPlan = findViewById(R.id.cardPlan);
        cardMap = findViewById(R.id.cardMap);
        cardStories = findViewById(R.id.cardStories);
        cardCommunity = findViewById(R.id.cardCommunity);
        cardWeather = findViewById(R.id.cardWeather);
        cardSettings = findViewById(R.id.cardSettings);

        // Load saved profile image
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String imageBase64 = sharedPreferences.getString("profile_image", "");
        // If image exists, show it
        if (!imageBase64.isEmpty()) {
            try {
                byte[] imageBytes = Base64.decode(imageBase64, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                profileIcon.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace(); // Optional: log error
            }
        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser(); // Get current logged in user

        // Show default greeting if no user found
        if (currentUser == null) {
            greetingText.setText("Hello 👋");
            return;
        }

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(currentUser.getUid())
                .get()
                .addOnSuccessListener(doc -> {

                    String nickname = doc.getString("nickname");

                    if (nickname != null && !nickname.isEmpty()) {
                        greetingText.setText("Hello " + nickname + " 👋");
                    } else {
                        greetingText.setText("Hello 👋");
                    }

                });


        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            profileLauncher.launch(intent);
        });

        cardDiscover.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, DiscoverActivity.class));
        });

        cardPlan.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, PlanYourTripActivity.class));
        });

        cardMap.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, MapLocationActivity.class));
        });

        cardStories.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, TravelStoriesActivity.class));
        });

        cardCommunity.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, TravelCommunityActivity.class));
        });

        cardWeather.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, WeatherActivity.class));
        });

        cardSettings.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SETTINGS);
        });

        // Get updates from profile page
        profileLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // If profile image was removed
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        boolean imageRemoved = data != null && data.getBooleanExtra("image_removed", false);

                        if (imageRemoved) {
                            profileIcon.setImageResource(R.drawable.ic_profile);
                            return;
                        }

                        // Reload profile image from SharedPreferences
                        String updatedImageBase64 = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                                .getString("profile_image", "");
                        if (!updatedImageBase64.isEmpty()) {
                            try {
                                byte[] imageBytes = Base64.decode(updatedImageBase64, Base64.DEFAULT);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                                profileIcon.setImageBitmap(bitmap);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            profileIcon.setImageResource(R.drawable.ic_profile);
                        }

                    }

                });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Update greeting after settings change
        if (requestCode == REQUEST_CODE_SETTINGS && resultCode == RESULT_OK) {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null && currentUser.getDisplayName() != null && !currentUser.getDisplayName().isEmpty()) {
                greetingText.setText("Hello " + currentUser.getDisplayName() + " 👋");
            } else {
                greetingText.setText("Hello 👋");
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume(); // Reload nickname when returning to this page
        loadNickname();
    }

    // Get nickname from database
    private void loadNickname() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) return;

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(currentUser.getUid())
                .get()
                .addOnSuccessListener(doc -> {

                    String nickname = doc.getString("nickname");

                    if (nickname != null && !nickname.isEmpty()) {
                        greetingText.setText("Hello " + nickname + " 👋");
                    } else {
                        greetingText.setText("Hello 👋");
                    }
                });
    }
}

