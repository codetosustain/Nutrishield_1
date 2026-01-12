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

public class SignUpActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword, etConfirmPassword;
    private Button btnCreateAccount;
    private TextView tvSignIn;
    private ImageView btnBack;

    private boolean isPasswordVisible = false;
    private boolean isConfirmVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // 🔗 Bind Views
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        tvSignIn = findViewById(R.id.tvSignIn);
        btnBack = findViewById(R.id.btnBack);

        // ⬅ Back Button
        btnBack.setOnClickListener(v -> {
            finish(); // return to AccountSetupActivity
        });

        // 👁 Password visibility toggle
        setupPasswordToggle(etPassword, true);
        setupPasswordToggle(etConfirmPassword, false);

        // 🎨 "Already have an account? Sign in" styling
        String text = "Already have an account? Sign in";
        SpannableString span = new SpannableString(text);

        span.setSpan(
                new ForegroundColorSpan(Color.BLACK),
                0,
                25,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        span.setSpan(
                new ForegroundColorSpan(Color.parseColor("#1E88E5")),
                26,
                text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        tvSignIn.setText(span);

        // 👉 Open Sign In page
        tvSignIn.setOnClickListener(v ->
                startActivity(new Intent(SignUpActivity.this, CustomerSignInActivity.class))
        );

        // ✅ Create Account Button
        btnCreateAccount.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            // 🔎 Validation
            if (username.isEmpty()) {
                etUsername.setError("Username required");
                return;
            }

            if (email.isEmpty()) {
                etEmail.setError("Email required");
                return;
            }

            if (password.isEmpty()) {
                etPassword.setError("Password required");
                return;
            }

            if (!password.equals(confirmPassword)) {
                etConfirmPassword.setError("Passwords do not match");
                return;
            }

            // ✅ SUCCESS (Later replace with Firebase Auth)
            Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();

            // 🚀 Go to Scanner Page
            Intent intent = new Intent(SignUpActivity.this, ScannerActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // 👁 Password Toggle Logic
    private void setupPasswordToggle(EditText editText, boolean isMainPassword) {
        editText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP &&
                    editText.getCompoundDrawables()[2] != null &&
                    event.getRawX() >=
                            (editText.getRight()
                                    - editText.getCompoundDrawables()[2].getBounds().width())) {

                boolean isVisible = isMainPassword ? isPasswordVisible : isConfirmVisible;

                if (isVisible) {
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

                if (isMainPassword) {
                    isPasswordVisible = !isPasswordVisible;
                } else {
                    isConfirmVisible = !isConfirmVisible;
                }

                editText.setSelection(editText.getText().length());
                return true;
            }
            return false;
        });
    }
}
