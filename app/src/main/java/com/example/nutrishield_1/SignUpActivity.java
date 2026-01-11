package com.example.nutrishield_1;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    EditText etUsername, etEmail, etPassword, etConfirmPassword;
    Button btnNext;
    TextView tvSignIn;
    ImageView btnBack;

    boolean isPasswordVisible = false;
    boolean isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnNext = findViewById(R.id.btnNext);
        tvSignIn = findViewById(R.id.tvSignIn);
        btnBack = findViewById(R.id.btnBack);

        // BACK
        btnBack.setOnClickListener(v ->
                startActivity(new Intent(this, AccountSetupActivity.class))
        );

        // PASSWORD TOGGLE
        etPassword.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP &&
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


        // CONFIRM PASSWORD TOGGLE
        etConfirmPassword.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP &&
                    event.getRawX() >=
                            (etConfirmPassword.getRight()
                                    - etConfirmPassword.getCompoundDrawables()[2].getBounds().width())) {

                if (isConfirmPasswordVisible) {
                    etConfirmPassword.setInputType(
                            InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD
                    );
                    etConfirmPassword.setTransformationMethod(
                            PasswordTransformationMethod.getInstance()
                    );
                    etConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_eye, 0
                    );
                } else {
                    etConfirmPassword.setInputType(
                            InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    );
                    etConfirmPassword.setTransformationMethod(null);
                    etConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_eye_off, 0
                    );
                }

                isConfirmPasswordVisible = !isConfirmPasswordVisible;
                etConfirmPassword.setSelection(
                        etConfirmPassword.getText().length()
                );
                return true;
            }
            return false;
        });

        // NEXT
        btnNext.setOnClickListener(v -> {
            if (!etPassword.getText().toString()
                    .equals(etConfirmPassword.getText().toString())) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
        });

        // SIGN IN
        tvSignIn.setOnClickListener(v ->
                startActivity(new Intent(this, CustomerSignInActivity.class))
        );
    }
}
