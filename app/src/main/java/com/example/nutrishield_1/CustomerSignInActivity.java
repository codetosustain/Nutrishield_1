package com.example.nutrishield_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerSignInActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnNext;
    TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_signin);

        // Bind views
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnNext = findViewById(R.id.btnNext);
        tvSignUp = findViewById(R.id.tvSignUp);

        // SIGN IN LOGIC
        btnNext.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty()) {
                etUsername.setError("Enter username");
                return;
            }

            if (password.isEmpty()) {
                etPassword.setError("Enter password");
                return;
            }

            Toast.makeText(this, "Customer signed in successfully", Toast.LENGTH_SHORT).show();

            // TODO: Open Customer Dashboard
        });

        // GO TO SIGN UP
        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(CustomerSignInActivity.this, CustomerAuthActivity.class);
            startActivity(intent);
            finish();
        });
    }
}

