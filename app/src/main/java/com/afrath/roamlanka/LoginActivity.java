package com.afrath.roamlanka;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;  // Declare login button
    TextView tvRegister, tvForgotPassword;
    CheckBox cbShowPassword;
    String nickname = "";  // To store user's nickname
    FirebaseFirestore db;
    SharedPreferences preferences;

    private FirebaseAuth mAuth;  // Declare Firebase Authentication object
    //private static final String TAG = "LoginActivity"; // Tag for Logcat messages


    // onCreate() runs when activity starts
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);   // Call parent activity's onCreate method
        mAuth = FirebaseAuth.getInstance();  // Get Firebase Authentication instance
        setContentView(R.layout.activity_login);

            etEmail = findViewById(R.id.etEmail);
            etPassword = findViewById(R.id.etPassword);
            cbShowPassword = findViewById(R.id.cbShowPassword);
            btnLogin = findViewById(R.id.btnLogin);
            tvForgotPassword = findViewById(R.id.tvForgotPassword);
            tvRegister = findViewById(R.id.tvRegister);

            preferences = getSharedPreferences("AppSettings", MODE_PRIVATE);  // Open SharedPreferences file named "AppSettings"


            // Get the nickname
            nickname = getIntent().getStringExtra("nickname");
            if (nickname == null) {
                   nickname = ""; // avoid null errors
            }


            // Login button click
            btnLogin.setOnClickListener(v -> {
                String email = etEmail.getText().toString().trim();   // Get email text and remove spaces
                String password = etPassword.getText().toString().trim();  // Get password text and remove spaces

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return;  // Stop execution
                }
                // --- Firebase Login Logic process ---
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                                    // Create Intent to open HomeActivity
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Clear previous activities
                                    startActivity(intent);
                                    finish(); // Close LoginActivity
                                } else {
                                    Toast.makeText(LoginActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            });

            // When checkbox clicked
            cbShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    // Show password
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    // Hide password
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                    etPassword.setSelection(etPassword.length()); // move cursor to end
                });


            // Set styled text using HTML
            tvRegister.setText(Html.fromHtml("Don't have an account? <font color='#1E88E5'>Sign up</font>"));

            tvRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                    startActivity(intent);
                }
            });

            tvForgotPassword.setOnClickListener(v -> {
                Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            });
    }
}

