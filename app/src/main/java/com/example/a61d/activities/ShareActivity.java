package com.example.a61d.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a61d.R;
import com.example.a61d.utils.PreferenceManager;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;

public class ShareActivity extends AppCompatActivity {

    private ImageView qrImageView;
    private TextView linkText;
    private Button backBtn, shareNowBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        qrImageView = findViewById(R.id.qrImageView);
        linkText = findViewById(R.id.shareLinkText);
        backBtn = findViewById(R.id.backBtn);
        shareNowBtn = findViewById(R.id.shareNowBtn);

        // 构造分享链接
        String username = PreferenceManager.getCurrentUser(this);
        String shareLink = "https://myapp.com/user/" + username;

        linkText.setText(shareLink);

        // 生成二维码
        generateQRCode(shareLink);

        // 返回按钮
        backBtn.setOnClickListener(v -> finish());

        // 分享按钮
        shareNowBtn.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out my profile: " + shareLink);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, "Share via");
            startActivity(shareIntent);
        });
    }

    private void generateQRCode(String text) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            int size = 512;
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565);
            var bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, size, size);

            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

            qrImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}

