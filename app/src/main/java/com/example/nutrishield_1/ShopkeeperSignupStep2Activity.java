package com.example.nutrishield_1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ShopkeeperSignupStep2Activity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_signup_step2);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        // Receive data from Step 1
        String shopNo = getIntent().getStringExtra("shopNo");
        String shopName = getIntent().getStringExtra("shopName");
        String address = getIntent().getStringExtra("address");

        btnCreateAccount.setOnClickListener(v -> {

            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // TODO: Firebase signup here
            Toast.makeText(
                    this,
                    "Signup Completed\nShop: " + shopName,
                    Toast.LENGTH_LONG
            ).show();

            finish(); // or go to LoginActivity
        });
    }
}
