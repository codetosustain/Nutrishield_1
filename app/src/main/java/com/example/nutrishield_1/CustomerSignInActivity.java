package com.example.nutrishield_1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerSignInActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_signin);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(v -> {

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

            Toast.makeText(this, "Customer Signed In", Toast.LENGTH_SHORT).show();

            // Next: open Customer Dashboard
        });
    }
}

