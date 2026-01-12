package com.example.nutrishield_1;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ShopkeeperSignInActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnNext;
    TextView tvSignUp;

    boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_signin);

        // TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // VIEWS
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnNext = findViewById(R.id.btnNext);
        tvSignUp = findViewById(R.id.tvSignUp);

        // ✅ SET COLORED TEXT SAFELY
        tvSignUp.setText(
                Html.fromHtml(
                        getString(R.string.shopkeeper_sign_up_html),
                        Html.FROM_HTML_MODE_LEGACY
                )
        );

        // 👁 PASSWORD TOGGLE
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

        // SIGN IN
        btnNext.setOnClickListener(v -> {
            if (etUsername.getText().toString().isEmpty()) {
                etUsername.setError("Enter email");
                return;
            }
            if (etPassword.getText().toString().isEmpty()) {
                etPassword.setError("Enter password");
                return;
            }

            Toast.makeText(this, "Shopkeeper signed in", Toast.LENGTH_SHORT).show();
        });

        // SIGN UP CLICK
        tvSignUp.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                ShopkeeperSignupStep1Activity.class
                        )
                )
        );
    }
}
