package com.example.nutrishield_1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class FreshnessActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> cameraLauncher;
    ActivityResultLauncher<Intent> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freshness);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        Button btnCamera = findViewById(R.id.btnCamera);
        Button btnGallery = findViewById(R.id.btnGallery);

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        showLoading();

                        new android.os.Handler().postDelayed(() -> {
                            hideLoading();
                            Toast.makeText(this, "Freshness result ready ✔", Toast.LENGTH_SHORT).show();
                        }, 2000);
                    }
                });

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        showLoading();

                        new android.os.Handler().postDelayed(() -> {
                            hideLoading();
                            Toast.makeText(this, "Freshness result ready ✔", Toast.LENGTH_SHORT).show();
                        }, 2000);
                    }
                });

        btnCamera.setOnClickListener(v ->
                cameraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)));

        btnGallery.setOnClickListener(v ->
                galleryLauncher.launch(
                        new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)));
    }

    private void showLoading() {
        findViewById(R.id.loadingOverlay).setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        findViewById(R.id.loadingOverlay).setVisibility(View.GONE);
    }
}
