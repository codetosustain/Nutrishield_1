package com.example.nutrishield_1;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class OrganicityResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organicity_result);

        // 🔗 Bind views (MATCHES YOUR XML)
        TextView txtResult = findViewById(R.id.txtResult);
        TextView txtPercent = findViewById(R.id.txtPercent);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        // 📦 Get data from previous activity
        float confidence = getIntent().getFloatExtra("confidence", 0f);
        String result = getIntent().getStringExtra("result");

        // 🛡 Safety fallback
        if (result == null) {
            result = "Result unavailable";
        }

        // 📊 Update UI
        progressBar.setProgress((int) confidence);
        txtPercent.setText(String.format(Locale.US, "%.1f%%", confidence));
        txtResult.setText(result);
    }
}
