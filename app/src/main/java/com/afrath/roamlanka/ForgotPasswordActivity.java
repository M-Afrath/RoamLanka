package com.afrath.roamlanka;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etEmailForgot;
    private Button btnResetPassword;
    private TextView tvBackToLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();  // Initialize Firebase Authentication

        // Initialize Views
        etEmailForgot = findViewById(R.id.etEmailForgot);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        tvBackToLogin = findViewById(R.id.tvBackToLogin);


        btnResetPassword.setOnClickListener(v -> {
            String email = etEmailForgot.getText().toString().trim();  // Get email from EditText

            // Empty check
            if (email.isEmpty()) {
                Toast.makeText(ForgotPasswordActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                return;   // Stop execution
            }

            // Email validation
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(ForgotPasswordActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                return;
            }

            // Disable button while request is processing
            btnResetPassword.setEnabled(false);

            // Send password reset email using Firebase
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    btnResetPassword.setEnabled(true);   // Re-enable button after task finishes

                                    if (task.isSuccessful()) {
                                        Toast.makeText(ForgotPasswordActivity.this, "Password reset email sent. Check Inbox or Spam folder.", Toast.LENGTH_LONG).show();

                                    } else {  // Get Firebase error message
                                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Something went wrong";

                                        Toast.makeText(ForgotPasswordActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
        });

        // Back to Login
        tvBackToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            startActivity(intent);  // Open LoginActivity
            finish();  // Close ForgotPasswordActivity
        });
    }
}