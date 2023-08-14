package com.comp1011.assignment3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.comp1011.assignment3.controller.CompanyController;

import java.io.File;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_DURATION = 5000; // 5 seconds
    CompanyController companyController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        companyController = new CompanyController(SplashScreen.this);
        // Load company name from company_info.txt
        TextView companyNameTextView = findViewById(R.id.company_name_textview);
        String companyName = "";
        try {
            companyName = companyController.getCompanyName(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        companyNameTextView.setText(companyName);

        // Load logo image from logo.png on device storage
        ImageView logoImageView = findViewById(R.id.logo_imageview);
        String logoImagePath = companyController.getLogoImagePath(this);
        if (logoImagePath != null) {
            File logoImageFile = new File(logoImagePath);
            if (logoImageFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(logoImageFile.getAbsolutePath());
                logoImageView.setImageBitmap(bitmap);
            }
        }

        // Go to main activity after splash duration
        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }, SPLASH_DURATION);
    }
}
