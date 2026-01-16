package com.example.nutrishield_1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite









        .Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class OrganicityActivity extends AppCompatActivity {

    // ===== CONSTANTS =====
    private static final int IMAGE_SIZE = 224;
    private static final int PIXEL_SIZE = 3;

    // ===== UI =====
    ProgressBar progressBar;
    TextView txtPercent;

    // ===== LAUNCHERS =====
    ActivityResultLauncher<Intent> cameraLauncher;
    ActivityResultLauncher<Intent> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organicity);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        Button btnCamera = findViewById(R.id.btnCamera);
        Button btnGallery = findViewById(R.id.btnGallery);

        progressBar = findViewById(R.id.progressBar);
        txtPercent = findViewById(R.id.txtPercent);

        // ===== CAMERA =====
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK &&
                            result.getData() != null &&
                            result.getData().getExtras() != null) {

                        Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                        startAnalysis(bitmap);
                    }
                });

        // ===== GALLERY =====
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media
                                    .getBitmap(getContentResolver(), uri);
                            startAnalysis(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

        btnCamera.setOnClickListener(v ->
                cameraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)));

        btnGallery.setOnClickListener(v ->
                galleryLauncher.launch(
                        new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)));
    }

    // ===== PROGRESS + INFERENCE =====
    private void startAnalysis(Bitmap bitmap) {

        progressBar.setVisibility(View.VISIBLE);
        txtPercent.setVisibility(View.VISIBLE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            int progress = 0;

            @Override
            public void run() {
                progress++;
                progressBar.setProgress(progress);
                txtPercent.setText(progress + "%");

                if (progress < 100) {
                    handler.postDelayed(this, 15);
                } else {
                    float confidence = runOrganicityModel(bitmap);

                    Intent intent = new Intent(
                            OrganicityActivity.this,
                            OrganicityResultActivity.class);

                    intent.putExtra("confidence", confidence);
                    intent.putExtra(
                            "result",
                            confidence > 50 ? "Organic ✅" : "Not Organic ❌");

                    startActivity(intent);
                }
            }
        }, 15);
    }

    // ===== TFLITE INFERENCE (NO METADATA) =====
    private float runOrganicityModel(Bitmap bitmap) {
        try {
            Interpreter interpreter = new Interpreter(loadModelFile());

            ByteBuffer input = preprocess(bitmap);
            float[][] output = new float[1][1];

            interpreter.run(input, output);
            interpreter.close();

            return output[0][0] * 100f;

        } catch (Exception e) {
            e.printStackTrace();
            return 0f;
        }
    }

    // ===== IMAGE PREPROCESSING =====
    private ByteBuffer preprocess(Bitmap bitmap) {

        Bitmap resized = Bitmap.createScaledBitmap(
                bitmap, IMAGE_SIZE, IMAGE_SIZE, true);

        ByteBuffer buffer = ByteBuffer.allocateDirect(
                4 * IMAGE_SIZE * IMAGE_SIZE * PIXEL_SIZE);

        buffer.order(ByteOrder.nativeOrder());

        int[] pixels = new int[IMAGE_SIZE * IMAGE_SIZE];
        resized.getPixels(pixels, 0, IMAGE_SIZE, 0, 0, IMAGE_SIZE, IMAGE_SIZE);

        int index = 0;
        for (int i = 0; i < IMAGE_SIZE; i++) {
            for (int j = 0; j < IMAGE_SIZE; j++) {

                int px = pixels[index++];

                buffer.putFloat((px >> 16) & 0xFF); // R
                buffer.putFloat((px >> 8) & 0xFF);  // G
                buffer.putFloat(px & 0xFF);         // B
            }
        }
        return buffer;
    }

    // ===== LOAD MODEL =====
    private MappedByteBuffer loadModelFile() throws IOException {

        FileInputStream fis = new FileInputStream(
                getAssets().openFd("apple_organicity_model_optimized.tflite")
                        .getFileDescriptor());

        FileChannel channel = fis.getChannel();
        long start = getAssets()
                .openFd("apple_organicity_model_optimized.tflite")
                .getStartOffset();
        long length = getAssets()
                .openFd("apple_organicity_model_optimized.tflite")
                .getDeclaredLength();

        return channel.map(FileChannel.MapMode.READ_ONLY, start, length);
    }
}
