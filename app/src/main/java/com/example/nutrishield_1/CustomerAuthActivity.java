package com.example.nutrishield_1;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerAuthActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnNext;
    TextView tvSignIn, tvBack;
    ImageView btnBack;

    boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_auth);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnNext = findViewById(R.id.btnNext);
        tvSignIn = findViewById(R.id.tvSignIn);
        tvBack = findViewById(R.id.btnBack);
        btnBack = findViewById(R.id.btnBack);

        // 🔙 BACK
        btnBack.setOnClickListener(v -> finish());
        tvBack.setOnClickListener(v -> finish());

        // 👁 PASSWORD TOGGLE (ACCESSIBILITY SAFE)
        etPassword.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP &&
                    etPassword.getCompoundDrawables()[2] != null &&
                    event.getRawX() >= (etPassword.getRight()
                            - etPassword.getCompoundDrawables()[2].getBounds().width())) {

                v.performClick();

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

        btnNext.setOnClickListener(v -> {
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

            Toast.makeText(this, "Customer account created", Toast.LENGTH_SHORT).show();
        });

        tvSignIn.setOnClickListener(v ->
                startActivity(new Intent(this, SignUpActivity.class))
        );
    }
}
