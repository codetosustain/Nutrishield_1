package com.example.nutrishield_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ShopkeeperSignupStep1Activity extends AppCompatActivity {

    EditText etShopNumber, etShopName, etShopAddress;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_signup_step1);

        // TOOLBAR (ADDED ONLY)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Shopkeeper");
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        // EXISTING CODE (UNCHANGED)
        etShopNumber = findViewById(R.id.etShopNumber);
        etShopName = findViewById(R.id.etShopName);
        etShopAddress = findViewById(R.id.etShopAddress);
        btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(v -> {
            String shopNo = etShopNumber.getText().toString().trim();
            String shopName = etShopName.getText().toString().trim();
            String address = etShopAddress.getText().toString().trim();

            if (shopNo.isEmpty() || shopName.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, ShopkeeperSignupStep2Activity.class);
            intent.putExtra("shopNo", shopNo);
            intent.putExtra("shopName", shopName);
            intent.putExtra("address", address);
            startActivity(intent);
        });
    }
}
