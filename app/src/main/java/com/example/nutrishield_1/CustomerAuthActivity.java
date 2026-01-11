package com.example.nutrishield_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerAuthActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnNext;
    TextView tvSignIn, tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_auth);

        // Bind views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnNext = findViewById(R.id.btnNext);
        tvSignIn = findViewById(R.id.tvSignIn);
        tvBack = findViewById(R.id.tvBack);

        // BACK ARROW
        tvBack.setOnClickListener(v -> finish());

        // SIGN UP FLOW (Create account)
        btnNext.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty()) {
                etEmail.setError("Enter email");
                return;
            }

            if (password.isEmpty()) {
                etPassword.setError("Enter password");
                return;
            }

            Toast.makeText(this, "Customer account created", Toast.LENGTH_SHORT).show();

            // TODO: Navigate to Customer Dashboard
        });

        // SIGN IN FLOW (Already have account)
        tvSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(CustomerAuthActivity.this, CustomerSignInActivity.class);
            startActivity(intent);
        });
    }
}

