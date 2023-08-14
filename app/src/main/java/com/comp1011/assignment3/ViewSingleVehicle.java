package com.comp1011.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.comp1011.assignment3.controller.VehicleController;
import com.comp1011.assignment3.model.Vehicle;

import java.io.IOException;
import java.text.ParseException;

public class ViewSingleVehicle extends AppCompatActivity {

    private TextView makeModelView, yearView, priceView, conditionView, cylindersView, doorsView, colorView, soldView;
    private Button editBtn, deleteBtn;
    private Vehicle vehicle;

    @Override
    protected void onRestart() {
        super.onRestart();
        // Get vehicle ID from intent
        int vehicleId = getIntent().getIntExtra("vehicle_id", -1);

        // Get vehicle from VehicleController
        try {
            VehicleController vehicleController = new VehicleController(this);
            vehicle = vehicleController.getVehicleById(vehicleId);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading vehicle details", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Populate views with vehicle details
        makeModelView.setText("Make Model: " + vehicle.getMake() + " " + vehicle.getModel());
        yearView.setText("Year: " + String.valueOf(vehicle.getYear()));
        priceView.setText("Price: " + String.valueOf(vehicle.getPrice()));
        conditionView.setText("Condition: " + vehicle.getCondition());
        cylindersView.setText("Cylinders: " + vehicle.getEngineCylinders());
        doorsView.setText("Doors: " + String.valueOf(vehicle.getNumOfDoors()));
        colorView.setText("Colour: " + vehicle.getColor());
        if (vehicle.getDateSold() != null) {
            soldView.setText("Sold on: " + vehicle.getDateSold().toString());
        } else {
            soldView.setVisibility(View.GONE);
            soldView.setText("");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_vehicle);

        // Get references to layout elements
        makeModelView = findViewById(R.id.make_model_text_view);
        yearView = findViewById(R.id.year_text_view);
        priceView = findViewById(R.id.price_text_view);
        conditionView = findViewById(R.id.condition_text_view);
        cylindersView = findViewById(R.id.cylinders_text_view);
        doorsView = findViewById(R.id.doors_text_view);
        colorView = findViewById(R.id.color_text_view);
        soldView = findViewById(R.id.sold_text_view);
        editBtn = findViewById(R.id.edit_button);
        deleteBtn = findViewById(R.id.delete_button);

        // Get vehicle ID from intent
        int vehicleId = getIntent().getIntExtra("vehicle_id", -1);

        // Get vehicle from VehicleController
        try {
            VehicleController vehicleController = new VehicleController(this);
            vehicle = vehicleController.getVehicleById(vehicleId);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading vehicle details", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Populate views with vehicle details
        makeModelView.setText("Make Model: " + vehicle.getMake() + " " + vehicle.getModel());
        yearView.setText("Year: " + String.valueOf(vehicle.getYear()));
        priceView.setText("Price: " + String.valueOf(vehicle.getPrice()));
        conditionView.setText("Condition: " + vehicle.getCondition());
        cylindersView.setText("Cylinders: " + vehicle.getEngineCylinders());
        doorsView.setText("Doors: " + String.valueOf(vehicle.getNumOfDoors()));
        colorView.setText("Colour: " + vehicle.getColor());
        if (vehicle.getDateSold() != null) {
            soldView.setText("Sold on: " + vehicle.getDateSold().toString());
        } else {
            soldView.setVisibility(View.GONE);
            soldView.setText("");
        }

        // Set up edit button click listener
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewSingleVehicle.this, ModifyVehicle.class);
                intent.putExtra("vehicleId", vehicle.getID());
                startActivity(intent);
            }
        });

        // Set up delete button click listener
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VehicleController vehicleController;
                try {
                    vehicleController = new VehicleController(ViewSingleVehicle.this);
                    vehicleController.removeVehicle(vehicle.getID());
                    vehicleController.saveChanges(ViewSingleVehicle.this);
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(ViewSingleVehicle.this, "Error deleting vehicle", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(ViewSingleVehicle.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
