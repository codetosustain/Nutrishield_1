package com.example.nutrishield_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    EditText etUsername, etEmail, etPassword, etConfirmPassword;
    Button btnNext;
    TextView tvSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnNext = findViewById(R.id.btnNext);
        tvSignIn = findViewById(R.id.tvSignIn);

        // SIGN UP
        btnNext.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            if (username.isEmpty()) {
                etUsername.setError("Enter username");
                return;
            }
            if (email.isEmpty()) {
                etEmail.setError("Enter email");
                return;
            }
            if (password.isEmpty()) {
                etPassword.setError("Enter password");
                return;
            }
            if (!password.equals(confirmPassword)) {
                etConfirmPassword.setError("Passwords do not match");
                return;
            }

            Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();

            // TODO: Go to dashboard or next step
        });

        // SIGN IN
        tvSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, CustomerSignInActivity.class);
            startActivity(intent);
        });
    }
}

