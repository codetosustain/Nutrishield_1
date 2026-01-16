package com.example.nutrishield_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ShopkeeperSignupStep1Activity extends AppCompatActivity {

    EditText etShopName, etShopAddress;
    Button btnNext;
    TextView tvSignIn;

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
        tvSignIn = findViewById(R.id.tvSignIn);
        String text = "Already logged in? Sign in";
        SpannableString spannable = new SpannableString(text);

// "Already logged in?" → BLACK
        spannable.setSpan(
                new ForegroundColorSpan(Color.BLACK),
                0,
                19,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

// "Sign in" → BLUE
        spannable.setSpan(
                new ForegroundColorSpan(Color.parseColor("#1E88E5")),
                20,
                text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        tvSignIn.setText(spannable);

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

        // 🔹 ALREADY LOGGED IN → SIGN IN
        tvSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(
                    ShopkeeperSignupStep1Activity.this,
                    ShopkeeperSignInActivity.class
            );
            startActivity(intent);
            finish();
        });
    }
}
