package com.example.nutrishield_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;

public class ShopkeeperHomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView btnMenu;
    TextView btnProfile;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_home);

        drawerLayout = findViewById(R.id.drawerLayout);
        btnMenu = findViewById(R.id.btnMenu);
        btnLogout = findViewById(R.id.btnLogout);

        // ✅ FIND PROFILE BUTTON (INSIDE DRAWER)
        btnProfile = drawerLayout.findViewById(R.id.btnProfile);

        // OPEN DRAWER
        btnMenu.setOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START)
        );

        // ✅ PROFILE CLICK
        if (btnProfile != null) {
            btnProfile.setOnClickListener(v -> {
                startActivity(new Intent(
                        ShopkeeperHomeActivity.this,
                        ShopkeeperProfileActivity.class
                ));
                drawerLayout.closeDrawer(GravityCompat.START);
            });
        }

        // LOGOUT
        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(
                    ShopkeeperHomeActivity.this,
                    WelcomeActivity.class
            ));
            finish();
        });
    }
}
