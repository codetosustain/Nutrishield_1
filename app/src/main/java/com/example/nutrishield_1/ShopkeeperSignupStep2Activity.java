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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ShopkeeperSignupStep2Activity extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etConfirmPassword;
    Button btnCreateAccount;
    TextView tvSignIn;

    boolean isPasswordVisible = false;
    boolean isConfirmVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_signup_step2);

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
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        tvSignIn = findViewById(R.id.tvSignIn);

        // COLORED TEXT
        tvSignIn.setText(
                Html.fromHtml(
                        "Already logged in? <font color='#1E88E5'>Sign in</font>",
                        Html.FROM_HTML_MODE_LEGACY
                )
        );

        // PASSWORD TOGGLE
        setupPasswordToggle(etPassword, true);
        setupPasswordToggle(etConfirmPassword, false);

        // CREATE ACCOUNT
        btnCreateAccount.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();
            String confirm = etConfirmPassword.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (email.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!pass.equals(confirm)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ShopkeeperSignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        // SIGN IN CLICK
        tvSignIn.setOnClickListener(v ->
                startActivity(new Intent(this, ShopkeeperSignInActivity.class))
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
