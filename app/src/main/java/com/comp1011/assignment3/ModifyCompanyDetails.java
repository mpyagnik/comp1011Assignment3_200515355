package com.comp1011.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.comp1011.assignment3.controller.CompanyController;
import com.comp1011.assignment3.model.Company;

import java.io.File;
import java.io.IOException;

public class ModifyCompanyDetails extends AppCompatActivity {

    private EditText companyNameEditText;
    private EditText companyAddressEditText;
    private Button saveNameButton;
    private Button saveAddressButton;

    CompanyController companyController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_company_details);

        // Get references to UI elements
        companyNameEditText = findViewById(R.id.company_name_edittext);
        companyAddressEditText = findViewById(R.id.company_address_edittext);
        saveNameButton = findViewById(R.id.save_name_button);
        saveAddressButton = findViewById(R.id.save_address_button);

        companyController = new CompanyController(this);

        ImageView logoImageView = findViewById(R.id.logo_imageview);
        String logoImagePath = companyController.getLogoImagePath(this);
        if (logoImagePath != null) {
            File logoImageFile = new File(logoImagePath);
            if (logoImageFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(logoImageFile.getAbsolutePath());
                logoImageView.setImageBitmap(bitmap);
            }
        }

        // Set the current company name and address as text in the corresponding EditTexts
        try {
            companyNameEditText.setText(companyController.getCompanyName(this));
            companyAddressEditText.setText(companyController.getCompanyAddress(this));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set click listener for save name button
        saveNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = companyNameEditText.getText().toString().trim();
                if (!newName.isEmpty()) {
                    companyController.updateCompanyName(ModifyCompanyDetails.this, newName);
                    Toast.makeText(ModifyCompanyDetails.this, "Company name saved.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ModifyCompanyDetails.this, "Please enter a valid company name.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set click listener for save address button
        saveAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newAddress = companyAddressEditText.getText().toString().trim();
                if (!newAddress.isEmpty()) {
                    companyController.updateCompanyAddress(ModifyCompanyDetails.this, newAddress);
                    Toast.makeText(ModifyCompanyDetails.this, "Company address saved.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ModifyCompanyDetails.this, "Please enter a valid company address.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
