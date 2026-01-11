package com.example.nutrishield_1;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ShopkeeperSignupStep2Activity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_signup_step2);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        // Receive Step-1 data
        String shopNo = getIntent().getStringExtra("shopNo");
        String shopName = getIntent().getStringExtra("shopName");
        String address = getIntent().getStringExtra("address");

        btnCreateAccount.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // 👉 Here you can save to Firebase / Database
            Toast.makeText(this,
                    "Signup Complete\nShop: " + shopName,
                    Toast.LENGTH_LONG).show();

            // startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
