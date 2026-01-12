package com.example.nutrishield_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ScannerActivity extends AppCompatActivity {

    Button btnFreshness, btnPurity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        // Toolbar back
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Buttons
        btnFreshness = findViewById(R.id.btnFreshness);
        btnPurity = findViewById(R.id.btnPurity);

        // Freshness
        btnFreshness.setOnClickListener(v -> {
            startActivity(new Intent(
                    ScannerActivity.this,
                    FreshnessActivity.class
            ));
        });

        // 🔥 ORGANICITY (THIS WAS FAILING)
        btnPurity.setOnClickListener(v -> {
            startActivity(new Intent(
                    ScannerActivity.this,
                    OrganicityActivity.class
            ));
        });
    }
}
