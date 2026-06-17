package com.afrath.roamlanka;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1; // Request code for selecting an image

    ImageView imageProfile;
    Button btnSelectPhoto, btnRemovePhoto;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageProfile = findViewById(R.id.imageProfile);
        btnSelectPhoto = findViewById(R.id.btnSelectPhoto);
        btnRemovePhoto = findViewById(R.id.btnRemovePhoto);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);  // Open SharedPreferences

        // Load saved image if exists
        String imageBase64 = sharedPreferences.getString("profile_image", "");
        if (!imageBase64.isEmpty()) {
            byte[] imageBytes = Base64.decode(imageBase64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imageProfile.setImageBitmap(bitmap);
        }

        btnSelectPhoto.setOnClickListener(view -> openGallery()); // Open gallery when button is clicked

        btnRemovePhoto.setOnClickListener(view -> {
            // Set default icon
            imageProfile.setImageResource(R.drawable.ic_profile); // Show default profile icon

            // Remove saved image from SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("profile_image");
            editor.apply();

            // Send result back to HomeActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("image_removed", true);
            setResult(RESULT_OK, resultIntent);

            finish(); // close activity and return
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check if user selected an image
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();

            try {
                // Get selected image
                InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close(); // Close the stream!

                if (bitmap != null) {
                    imageProfile.setImageBitmap(bitmap);  // Show image in ImageView
                    imageProfile.invalidate();  // Refresh image view

                    // Convert image to text format
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    // Save to SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("profile_image", encodedImage);
                    editor.apply();

                    // Send result back to HomeActivity
                    Intent resultIntent = new Intent();
                    resultIntent.setData(selectedImageUri); // optional
                    setResult(RESULT_OK, resultIntent);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}


