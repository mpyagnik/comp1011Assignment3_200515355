package com.comp1011.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.comp1011.assignment3.controller.VehicleController;
import com.comp1011.assignment3.model.Vehicle;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModifyVehicle extends AppCompatActivity {

    private VehicleController vehicleController;
    private Vehicle vehicle;
    private EditText priceEditText;
    private EditText soldDateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_vehicle);

        // Get the vehicle ID from the intent
        int vehicleId = getIntent().getIntExtra("vehicleId", 0);

        try {
            vehicleController = new VehicleController(this);
            vehicle = vehicleController.getVehicleById(vehicleId);
            if (vehicle == null) {
                Toast.makeText(this, "Vehicle not found", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "An error occurred while loading the vehicle", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Set up UI elements
        priceEditText = findViewById(R.id.edit_vehicle_price_edit_text);
        soldDateEditText = findViewById(R.id.edit_vehicle_sold_edit_text);

        priceEditText.setText(String.valueOf(vehicle.getPrice()));

        if (vehicle.getDateSold() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            soldDateEditText.setText(dateFormat.format(vehicle.getDateSold()));
        }

        Button saveButton = findViewById(R.id.edit_vehicle_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double price = null;
                if (!priceEditText.getText().toString().isEmpty()) {
                    price = Double.parseDouble(priceEditText.getText().toString());
                }

                Date soldDate = null;
                if (!soldDateEditText.getText().toString().isEmpty()) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        soldDate = dateFormat.parse(soldDateEditText.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                if (vehicle.getDateSold() != null && soldDate != null && soldDate.before(vehicle.getDateSold())) {
                    Toast.makeText(ModifyVehicle.this, "Sold date cannot be before previous sold date", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (price != null) {
                    vehicle.setPrice(price);
                }
                vehicle.setDateSold(soldDate);

                try {
                    vehicleController.saveChanges(ModifyVehicle.this);
                    Toast.makeText(ModifyVehicle.this, "Vehicle updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ModifyVehicle.this, "An error occurred while saving the changes", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button exitButton = findViewById(R.id.edit_vehicle_exit_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
