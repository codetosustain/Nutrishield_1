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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CustomerSignInActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnSignIn;
    private TextView tvSignUp;

    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_signin);

        // 🔧 TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // 🔗 BIND VIEWS
        etEmail = findViewById(R.id.etUsername); // email field
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnNext);
        tvSignUp = findViewById(R.id.tvSignUp);

        // 🎨 "Don't have an account? Sign Up"
        String text = "Don't have an account? Sign Up";
        SpannableString span = new SpannableString(text);

        span.setSpan(
                new ForegroundColorSpan(Color.BLACK),
                0, 22,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        span.setSpan(
                new ForegroundColorSpan(Color.parseColor("#1E88E5")),
                23, text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        tvSignUp.setText(span);

        // 👁 PASSWORD VISIBILITY TOGGLE
        etPassword.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP &&
                    etPassword.getCompoundDrawables()[2] != null &&
                    event.getRawX() >=
                            (etPassword.getRight()
                                    - etPassword.getCompoundDrawables()[2].getBounds().width())) {

                if (isPasswordVisible) {
                    etPassword.setInputType(
                            InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD
                    );
                    etPassword.setTransformationMethod(
                            PasswordTransformationMethod.getInstance()
                    );
                } else {
                    etPassword.setInputType(
                            InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    );
                    etPassword.setTransformationMethod(null);
                }

                isPasswordVisible = !isPasswordVisible;
                etPassword.setSelection(etPassword.getText().length());
                return true;
            }
            return false;
        });

        // 🔐 SIGN IN BUTTON
        btnSignIn.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty()) {
                etEmail.setError("Enter email");
                return;
            }

            if (password.isEmpty()) {
                etPassword.setError("Enter password");
                return;
            }

            // ✅ SUCCESS (replace with Firebase later)
            Toast.makeText(this, "Signed in successfully", Toast.LENGTH_SHORT).show();

            // 🚀 OPEN SCANNER PAGE
            startActivity(new Intent(this, ScannerActivity.class));
            finish();
        });

        // ➕ GO TO SIGN UP
        tvSignUp.setOnClickListener(v ->
                startActivity(new Intent(this, SignUpActivity.class))
        );
    }
}
