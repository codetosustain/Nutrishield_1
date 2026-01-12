package com.example.nutrishield_1;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrganicityResultActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView txtPercent, txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organicity_result);

        progressBar = findViewById(R.id.progressBar);
        txtPercent = findViewById(R.id.txtPercent);
        txtResult = findViewById(R.id.txtResult);

        // Get data from previous screen
        float confidence = getIntent().getFloatExtra("confidence", 0f);
        String result = getIntent().getStringExtra("result");

        // Clamp confidence (safety)
        confidence = Math.max(0f, Math.min(confidence, 100f));

        // Update UI
        progressBar.setProgress((int) confidence);
        txtPercent.setText((int) confidence + "%");
        txtResult.setText(result);
    }
}
