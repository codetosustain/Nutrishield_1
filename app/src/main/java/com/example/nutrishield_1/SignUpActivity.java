package com.example.nutrishield_1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText etUsername, etEmail, etPassword, etConfirmPassword;
    Button btnCreateAccount;
    TextView tvSignIn;
    ImageView btnBack;

    boolean isPasswordVisible = false;
    boolean isConfirmVisible = false;

    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // 🔹 FIREBASE
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // 🔹 VIEWS
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        tvSignIn = findViewById(R.id.tvSignIn);
        btnBack = findViewById(R.id.btnBack);

        // 🔹 BACK
        btnBack.setOnClickListener(v ->
                startActivity(new Intent(this, AccountSetupActivity.class))
        );

        // 👁 PASSWORD TOGGLES
        setupPasswordToggle(etPassword, true);
        setupPasswordToggle(etConfirmPassword, false);

        // 🔹 COLORED TEXT
        String text = "Already have an account? Sign in";
        SpannableString span = new SpannableString(text);

        span.setSpan(
                new ForegroundColorSpan(Color.BLACK),
                0,
                24,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        span.setSpan(
                new ForegroundColorSpan(Color.parseColor("#1E88E5")),
                25,
                text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        tvSignIn.setText(span);

        tvSignIn.setOnClickListener(v ->
                startActivity(new Intent(this, CustomerSignInActivity.class))
        );

        // 🔥 SIGN UP
        btnCreateAccount.setOnClickListener(v -> createAccount());
    }

    private void createAccount() {

        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirm = etConfirmPassword.getText().toString().trim();

        if (username.isEmpty()) {
            etUsername.setError("Enter username");
            return;
        }

        if (email.isEmpty()) {
            etEmail.setError("Enter email");
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Enter password");
            return;
        }

        if (!password.equals(confirm)) {
            etConfirmPassword.setError("Passwords do not match");
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {

                    String uid = auth.getCurrentUser().getUid();

                    // 🔥 CREATE USER DOCUMENT IN FIRESTORE
                    Map<String, Object> user = new HashMap<>();
                    user.put("username", username);
                    user.put("email", email);
                    user.put("role", "customer");
                    user.put("createdAt", System.currentTimeMillis());

                    db.collection("users")
                            .document(uid)
                            .set(user)
                            .addOnSuccessListener(unused -> {

                                Toast.makeText(
                                        this,
                                        "Account created successfully",
                                        Toast.LENGTH_SHORT
                                ).show();

                                startActivity(
                                        new Intent(this, CustomerSignInActivity.class)
                                );
                                finish();
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(
                                            this,
                                            e.getMessage(),
                                            Toast.LENGTH_SHORT
                                    ).show()
                            );
                })
                .addOnFailureListener(e ->
                        Toast.makeText(
                                this,
                                e.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show()
                );
    }

    private void setupPasswordToggle(EditText editText, boolean main) {
        editText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP &&
                    editText.getCompoundDrawables()[2] != null &&
                    event.getRawX() >=
                            (editText.getRight()
                                    - editText.getCompoundDrawables()[2]
                                    .getBounds().width())) {

                boolean visible = main ? isPasswordVisible : isConfirmVisible;

                if (visible) {
                    editText.setInputType(
                            InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD
                    );
                    editText.setTransformationMethod(
                            PasswordTransformationMethod.getInstance()
                    );
                } else {
                    editText.setInputType(
                            InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    );
                    editText.setTransformationMethod(null);
                }

                if (main) isPasswordVisible = !isPasswordVisible;
                else isConfirmVisible = !isConfirmVisible;

                editText.setSelection(editText.getText().length());
                return true;
            }
            return false;
        });
    }
}
