package com.example.nutrishield_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AccountSetupActivity extends AppCompatActivity {

    LinearLayout customerLayout, shopkeeperLayout;
    Button btnNext;

    String selectedRole = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup);

        // 🔹 TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        // 🔹 BIND VIEWS
        customerLayout = findViewById(R.id.layoutCustomer);
        shopkeeperLayout = findViewById(R.id.layoutShopkeeper);
        btnNext = findViewById(R.id.btnNext);

        // 🔹 CUSTOMER SELECT
        customerLayout.setOnClickListener(v -> {
            selectedRole = "customer";
            customerLayout.setBackgroundResource(R.drawable.bg_selected);
            shopkeeperLayout.setBackgroundResource(R.drawable.bg_unselected);
        });

        // 🔹 SHOPKEEPER SELECT
        shopkeeperLayout.setOnClickListener(v -> {
            selectedRole = "shopkeeper";
            shopkeeperLayout.setBackgroundResource(R.drawable.bg_selected);
            customerLayout.setBackgroundResource(R.drawable.bg_unselected);
        });

        btnNext.setOnClickListener(v -> {

            if (selectedRole.isEmpty()) {
                Toast.makeText(
                        this,
                        "Select account type",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            Intent intent;

            if ("customer".equals(selectedRole)) {
                // 👤 CUSTOMER FLOW
                intent = new Intent(
                        AccountSetupActivity.this,
                        SignUpActivity.class   // or CustomerSignupActivity
                );

            } else {
                // 🏪 SHOPKEEPER FLOW
                intent = new Intent(
                        AccountSetupActivity.this,
                        ShopkeeperSignupStep1Activity.class
                );
            }

            intent.putExtra("role", selectedRole);
            startActivity(intent);
        });

    }
}
