package com.comp1011.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.comp1011.assignment3.controller.CompanyController;

public class MainActivity extends AppCompatActivity {

    private Button viewAllBtn, viewAvailableBtn, viewSoldBtn, addBtn, exitBtn, editBtn;
    CompanyController companyController;

    @Override
    public void onRestart() {
        super.onRestart();

        companyController = new CompanyController(this);

        TextView companyNameTextView = findViewById(R.id.companyName);
        TextView companyAddressTextView = findViewById(R.id.addressText);
        String companyName = "";
        String companyAdd = "";
        try {
            companyName = companyController.getCompanyName(this);
            companyAdd = companyController.getCompanyAddress(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        companyNameTextView.setText(companyName);
        companyAddressTextView.setText(companyAdd);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        companyController = new CompanyController(this);

        TextView companyNameTextView = findViewById(R.id.companyName);
        TextView companyAddressTextView = findViewById(R.id.addressText);
        String companyName = "";
        String companyAdd = "";
        try {
            companyName = companyController.getCompanyName(this);
            companyAdd = companyController.getCompanyAddress(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        companyNameTextView.setText(companyName);
        companyAddressTextView.setText(companyAdd);

        // Get reference to viewAllBtn button
        viewAllBtn = findViewById(R.id.viewAllBtn);
        viewAvailableBtn = findViewById(R.id.viewAvailableBtn);
        viewSoldBtn = findViewById(R.id.viewSoldBtn);
        exitBtn = findViewById(R.id.exitBtn);
        addBtn = findViewById(R.id.addBtn);
        editBtn = findViewById(R.id.editCompanyBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch ViewAllVehicles activity
                Intent intent = new Intent(MainActivity.this, AddNewVehicle.class);
                startActivity(intent);
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch ViewAllVehicles activity
                Intent intent = new Intent(MainActivity.this, ModifyCompanyDetails.class);
                startActivity(intent);
            }
        });

        // Set OnClickListener for viewAllBtn button
        viewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch ViewAllVehicles activity
                Intent intent = new Intent(MainActivity.this, ViewAllVehicles.class);
                startActivity(intent);
            }
        });

        viewAvailableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch ViewAllVehicles activity
                Intent intent = new Intent(MainActivity.this, ViewAvailableVehicles.class);
                startActivity(intent);
            }
        });

        viewSoldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch ViewAllVehicles activity
                Intent intent = new Intent(MainActivity.this, ViewSoldVehicles.class);
                startActivity(intent);
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }
}
