package com.afrath.roamlanka;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class FullScreenImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        ImageView fullImageView = findViewById(R.id.fullImageView);


        // Get image resource ID sent from previous activity (Intent)
        // -1 means "no value received"
        int imageResId = getIntent().getIntExtra("image_res_id", -1);
        if (imageResId != -1) {
            fullImageView.setImageResource(imageResId);
        }
    }
}
