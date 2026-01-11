package com.example.nutrishield_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

    public class AccountSetupActivity extends AppCompatActivity {

        EditText etUsername;
        LinearLayout customerLayout, shopkeeperLayout;
        Button btnNext;

        String selectedRole = "";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_account_setup);

            etUsername = findViewById(R.id.etUsername);
            customerLayout = findViewById(R.id.layoutCustomer);
            shopkeeperLayout = findViewById(R.id.layoutShopkeeper);
            btnNext = findViewById(R.id.btnNext);

            customerLayout.setOnClickListener(v -> {
                selectedRole = "customer";
                customerLayout.setBackgroundResource(R.drawable.bg_selected);
                shopkeeperLayout.setBackgroundResource(R.drawable.bg_unselected);
            });

            shopkeeperLayout.setOnClickListener(v -> {
                selectedRole = "shopkeeper";
                shopkeeperLayout.setBackgroundResource(R.drawable.bg_selected);
                customerLayout.setBackgroundResource(R.drawable.bg_unselected);
            });

            btnNext.setOnClickListener(v -> {

                String username = etUsername.getText().toString().trim();

                if (username.isEmpty()) {
                    Toast.makeText(this, "Enter username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedRole.isEmpty()) {
                    Toast.makeText(this, "Select account type", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent;

                if (selectedRole.equals("customer")) {
                    intent = new Intent(this, CustomerAuthActivity.class);
                } else {
                    intent = new Intent(this, LoginActivity.class);
                }

                intent.putExtra("username", username);
                intent.putExtra("role", selectedRole);

                startActivity(intent);
            });

        }
    }


