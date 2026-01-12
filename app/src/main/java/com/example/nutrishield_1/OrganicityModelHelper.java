package com.example.nutrishield_1;

import android.content.Context;
import android.graphics.Bitmap;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class OrganicityModelHelper {

    private Interpreter interpreter;
    private static final int IMAGE_SIZE = 224;

    public OrganicityModelHelper(Context context) throws IOException {
        interpreter = new Interpreter(loadModelFile(context));
    }

    private MappedByteBuffer loadModelFile(Context context) throws IOException {
        FileInputStream fis =
                new FileInputStream(context.getAssets()
                        .openFd("apple_organicity_model_optimized.tflite")
                        .getFileDescriptor());

        FileChannel channel = fis.getChannel();
        long startOffset = context.getAssets()
                .openFd("apple_organicity_model_optimized.tflite")
                .getStartOffset();
        long declaredLength = context.getAssets()
                .openFd("apple_organicity_model_optimized.tflite")
                .getDeclaredLength();

        return channel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    public float predict(Bitmap bitmap) {

        Bitmap resized = Bitmap.createScaledBitmap(
                bitmap, IMAGE_SIZE, IMAGE_SIZE, true);

        ByteBuffer inputBuffer =
                ByteBuffer.allocateDirect(4 * IMAGE_SIZE * IMAGE_SIZE * 3);
        inputBuffer.order(ByteOrder.nativeOrder());

        for (int y = 0; y < IMAGE_SIZE; y++) {
            for (int x = 0; x < IMAGE_SIZE; x++) {
                int px = resized.getPixel(x, y);

                inputBuffer.putFloat(((px >> 16) & 0xFF) / 255f);
                inputBuffer.putFloat(((px >> 8) & 0xFF) / 255f);
                inputBuffer.putFloat((px & 0xFF) / 255f);
            }
        }

        float[][] output = new float[1][1];
        interpreter.run(inputBuffer, output);

        return output[0][0] * 100f; // % confidence
    }

    public void close() {
        interpreter.close();
    }
}
