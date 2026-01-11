package com.example.nutrishield_1;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;   // ✅ THIS LINE
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class SignUpActivity extends AppCompatActivity {

    EditText etUsername;
    RadioGroup rgAccount;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // VERY IMPORTANT
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.etUsername);
        rgAccount = findViewById(R.id.rgAccount);
        btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(v -> {

            String username = etUsername.getText().toString().trim();
            int selectedId = rgAccount.getCheckedRadioButtonId();

            if (username.isEmpty()) {
                etUsername.setError("Enter username");
                return;
            }

            if (selectedId == -1) {
                Toast.makeText(this, "Select account type", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selected = findViewById(selectedId);
            String role = selected.getText().toString();

            if (role.equalsIgnoreCase("Customer")) {
                Intent intent = new Intent(SignUpActivity.this, CustomerAuthActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Shopkeeper screen coming next", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

