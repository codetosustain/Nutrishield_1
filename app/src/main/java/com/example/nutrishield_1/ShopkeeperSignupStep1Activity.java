package com.example.nutrishield_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ShopkeeperSignupStep1Activity extends AppCompatActivity {

    EditText etShopName, etShopAddress;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_signup_step1);

        // TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Shopkeeper");
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        // BIND VIEWS
        etShopName = findViewById(R.id.etShopName);
        etShopAddress = findViewById(R.id.etShopAddress);
        btnNext = findViewById(R.id.btnNext);

        // NEXT BUTTON
        btnNext.setOnClickListener(v -> {
            String shopName = etShopName.getText().toString().trim();
            String address = etShopAddress.getText().toString().trim();

            if (shopName.isEmpty()) {
                etShopName.setError("Enter shop name");
                return;
            }

            if (address.isEmpty()) {
                etShopAddress.setError("Enter shop address");
                return;
            }

            Intent intent = new Intent(
                    ShopkeeperSignupStep1Activity.this,
                    ShopkeeperSignupStep2Activity.class
            );
            intent.putExtra("shopName", shopName);
            intent.putExtra("address", address);
            startActivity(intent);
        });
    }
}
