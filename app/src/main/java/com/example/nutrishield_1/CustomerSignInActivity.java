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

    EditText etUsername, etPassword;
    Button btnNext;
    TextView tvSignUp;

    boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_signin);

        // TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // VIEWS
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnNext = findViewById(R.id.btnNext);
        tvSignUp = findViewById(R.id.tvSignUp);

        // COLORED TEXT (NO XML HTML)
        String text = "Don't have an account? Sign Up";
        SpannableString spannable = new SpannableString(text);
        spannable.setSpan(
                new ForegroundColorSpan(Color.BLACK),
                0, 22,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        spannable.setSpan(
                new ForegroundColorSpan(Color.parseColor("#1E88E5")),
                23, text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        tvSignUp.setText(spannable);

        // 👁 PASSWORD TOGGLE
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
                    etPassword.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_eye, 0
                    );
                } else {
                    etPassword.setInputType(
                            InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    );
                    etPassword.setTransformationMethod(null);
                    etPassword.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_eye_off, 0
                    );
                }

                isPasswordVisible = !isPasswordVisible;
                etPassword.setSelection(etPassword.getText().length());
                return true;
            }
            return false;
        });

        // SIGN IN
        btnNext.setOnClickListener(v -> {
            if (etUsername.getText().toString().trim().isEmpty()) {
                etUsername.setError("Enter email");
                return;
            }
            if (etPassword.getText().toString().trim().isEmpty()) {
                etPassword.setError("Enter password");
                return;
            }

            Toast.makeText(this, "Customer signed in successfully", Toast.LENGTH_SHORT).show();
        });

        // GO TO SIGN UP
        tvSignUp.setOnClickListener(v ->
                startActivity(new Intent(this, SignUpActivity.class))
        );
    }
}
