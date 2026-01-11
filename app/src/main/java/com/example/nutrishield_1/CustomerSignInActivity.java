package com.example.nutrishield_1;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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

        // 🔙 BACK BUTTON (GOES TO AccountSetupActivity)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        // Bind views
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnNext = findViewById(R.id.btnNext);
        tvSignUp = findViewById(R.id.tvSignUp);

        // 👁 PASSWORD VISIBILITY
        etPassword.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP &&
                    etPassword.getCompoundDrawables()[2] != null &&
                    event.getRawX() >= (etPassword.getRight()
                            - etPassword.getCompoundDrawables()[2].getBounds().width())) {

                if (isPasswordVisible) {
                    etPassword.setInputType(
                            InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD
                    );
                    isPasswordVisible = false;
                } else {
                    etPassword.setInputType(
                            InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    );
                    isPasswordVisible = true;
                }

                etPassword.setSelection(etPassword.getText().length());
                return true;
            }
            return false;
        });

        // SIGN IN LOGIC
        btnNext.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty()) {
                etUsername.setError("Enter username");
                return;
            }

            if (password.isEmpty()) {
                etPassword.setError("Enter password");
                return;
            }

            Toast.makeText(this, "Customer signed in successfully", Toast.LENGTH_SHORT).show();
        });

        // GO TO SIGN UP
        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(CustomerSignInActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
