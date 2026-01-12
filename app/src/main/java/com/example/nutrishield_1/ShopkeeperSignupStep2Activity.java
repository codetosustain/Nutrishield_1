

package com.example.nutrishield_1;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ShopkeeperSignupStep2Activity extends AppCompatActivity {

    EditText etName, etEmail, etPhone, etPassword, etConfirmPassword;
    Button btnCreateAccount;
    TextView tvSignIn;

    boolean isPasswordVisible = false;
    boolean isConfirmVisible = false;

    FirebaseAuth auth;
    FirebaseFirestore db;

    String shopName;   // ✅ address REMOVED

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_signup_step2);

        // FIREBASE
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // GET DATA FROM STEP 1
        shopName = getIntent().getStringExtra("shopName");
        // ❌ address REMOVED

        // TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Shopkeeper");
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // VIEWS
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        tvSignIn = findViewById(R.id.tvSignIn);

        // SIGN IN TEXT
        String text = "Already logged in? Sign in";
        SpannableString spannable = new SpannableString(text);

// "Already logged in?" → BLACK
        spannable.setSpan(
                new android.text.style.ForegroundColorSpan(Color.BLACK),
                0,
                20,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

// "Sign in" → BLUE
        spannable.setSpan(
                new android.text.style.ForegroundColorSpan(Color.parseColor("#1E88E5")),
                21,
                text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        tvSignIn.setText(spannable);


        // PASSWORD TOGGLE
        setupPasswordToggle(etPassword, true);
        setupPasswordToggle(etConfirmPassword, false);

        // CREATE ACCOUNT
        btnCreateAccount.setOnClickListener(v -> createShopkeeper());

        // SIGN IN
        tvSignIn.setOnClickListener(v ->
                startActivity(new Intent(this, ShopkeeperSignInActivity.class))
        );
    }

    private void createShopkeeper() {

        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();
        String confirm = etConfirmPassword.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()
                || pass.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pass.equals(confirm)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener(authResult -> {

                    String uid = auth.getCurrentUser().getUid();

                    Map<String, Object> shopData = new HashMap<>();
                    shopData.put("ownerUid", uid);
                    shopData.put("ownerName", name);
                    shopData.put("email", email);
                    shopData.put("phone", phone);
                    shopData.put("shopName", shopName);
                    shopData.put("totalPoints", 0);
                    shopData.put("averageRating", 0);
                    shopData.put("reviewCount", 0);
                    shopData.put("createdAt", System.currentTimeMillis());

                    db.collection("shops")
                            .add(shopData)
                            .addOnSuccessListener(doc -> {

                                Toast.makeText(
                                        this,
                                        "Shop registered successfully",
                                        Toast.LENGTH_LONG
                                ).show();

                                Intent intent = new Intent(
                                        this,
                                        ShopkeeperHomeActivity.class
                                );
                                intent.setFlags(
                                        Intent.FLAG_ACTIVITY_NEW_TASK |
                                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                                );
                                startActivity(intent);
                                finish();
                            });
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    private void setupPasswordToggle(EditText editText, boolean isMain) {
        editText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP &&
                    editText.getCompoundDrawables()[2] != null &&
                    event.getRawX() >=
                            (editText.getRight()
                                    - editText.getCompoundDrawables()[2].getBounds().width())) {

                boolean visible = isMain ? isPasswordVisible : isConfirmVisible;

                if (visible) {
                    editText.setInputType(
                            InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD
                    );
                    editText.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_eye, 0
                    );
                } else {
                    editText.setInputType(
                            InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    );
                    editText.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_eye_off, 0
                    );
                }

                if (isMain) isPasswordVisible = !isPasswordVisible;
                else isConfirmVisible = !isConfirmVisible;

                editText.setSelection(editText.getText().length());
                return true;
            }
            return false;
        });
    }
}
