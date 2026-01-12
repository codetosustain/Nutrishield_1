package com.example.nutrishield_1;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShopkeeperProfileActivity extends AppCompatActivity {

    // TextViews
    TextView tvName, tvEmail, tvPhone, tvShopName, tvAddress;

    // Badge Images
    ImageView imgGold, imgSilver, imgBronze;

    // Badge cards + container
    LinearLayout badgeContainer;
    LinearLayout cardGold, cardSilver, cardBronze;

    FirebaseFirestore db;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_profile);

        // Bind TextViews
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvShopName = findViewById(R.id.tvShopName);
        tvAddress = findViewById(R.id.tvShopAddress);

        // Bind Badge Images
        imgGold = findViewById(R.id.imgGold);
        imgSilver = findViewById(R.id.imgSilver);
        imgBronze = findViewById(R.id.imgBronze);

        // Bind Badge Cards + Container
        badgeContainer = findViewById(R.id.badgeContainer);
        cardGold = findViewById(R.id.cardGold);
        cardSilver = findViewById(R.id.cardSilver);
        cardBronze = findViewById(R.id.cardBronze);

        // Firebase
        db = FirebaseFirestore.getInstance();
        uid = FirebaseAuth.getInstance().getUid();

        if (uid == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        loadShopkeeperProfile();
    }

    private void loadShopkeeperProfile() {
        db.collection("shops")
                .whereEqualTo("ownerUid", uid)
                .get()
                .addOnSuccessListener(query -> {
                    if (query.isEmpty()) {
                        Toast.makeText(this, "Shop not found", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    DocumentSnapshot doc = query.getDocuments().get(0);

                    // Set profile data
                    tvName.setText(doc.getString("ownerName"));
                    tvEmail.setText(doc.getString("email"));
                    tvPhone.setText(doc.getString("phone"));
                    tvShopName.setText(doc.getString("shopName"));
                    tvAddress.setText(doc.getString("address"));

                    // Points
                    Long pointsLong = doc.getLong("totalPoints");
                    long points = pointsLong != null ? pointsLong : 0;

                    // Show badges
                    showBadge(points);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to load profile", Toast.LENGTH_SHORT).show()
                );
    }

    private void showBadge(long points) {
        /* Crucial Rule: View.GONE removes the view from the layout entirely.
           The LinearLayout weight logic will automatically divide the width
           among the views that remain View.VISIBLE.
        */

        if (points >= 45) {
            // GOLD → show all 3. They will each take 33% width.
            cardGold.setVisibility(View.VISIBLE);
            cardSilver.setVisibility(View.VISIBLE);
            cardBronze.setVisibility(View.VISIBLE);
        } else if (points >= 35) {
            // SILVER → show 2. They will each take 50% width automatically.
            cardGold.setVisibility(View.GONE);
            cardSilver.setVisibility(View.VISIBLE);
            cardBronze.setVisibility(View.VISIBLE);
        } else if (points >= 20) {
            // BRONZE → show 1. It will take 100% width automatically.
            cardGold.setVisibility(View.GONE);
            cardSilver.setVisibility(View.GONE);
            cardBronze.setVisibility(View.VISIBLE);
        } else {
            // No badges earned
            cardGold.setVisibility(View.GONE);
            cardSilver.setVisibility(View.GONE);
            cardBronze.setVisibility(View.GONE);
        }
    }
}