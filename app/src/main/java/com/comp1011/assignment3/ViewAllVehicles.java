package com.comp1011.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.comp1011.assignment3.controller.VehicleController;
import com.comp1011.assignment3.model.Vehicle;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewAllVehicles extends AppCompatActivity {

    private ArrayList<Vehicle> vehicleList;
    private LinearLayout cardListLayout;
    private Spinner sortSpinner;

    @Override
    protected void onRestart() {
        super.onRestart();

        // Get references to layout elements
        cardListLayout = findViewById(R.id.card_list);
        try {
            VehicleController vehicleController = new VehicleController(ViewAllVehicles.this);
            vehicleList = vehicleController.getVehicles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        // Get list of vehicles from VehicleController


        // Create and add CardViews for each vehicle to the LinearLayout
        for (Vehicle vehicle : vehicleList) {
            addCardView(vehicle);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_vehicles);

        // Get references to layout elements
        cardListLayout = findViewById(R.id.card_list);
        sortSpinner = findViewById(R.id.sort_spinner);

        // Get list of attribute names from Vehicle class
        List<String> attributeNames = new ArrayList<>(
                Arrays.asList("Make", "Model", "Condition", "Engine Cylinders", "Year", "Number of Doors", "Price"));

        // Create ArrayAdapter with attribute names and set it as the spinner adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, attributeNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);

        try {
            VehicleController vehicleController = new VehicleController(ViewAllVehicles.this);
            vehicleList = vehicleController.getVehicles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        // Get list of vehicles from VehicleController


        // Create and add CardViews for each vehicle to the LinearLayout
        for (Vehicle vehicle : vehicleList) {
            addCardView(vehicle);
        }

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            VehicleController vehicleController;

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Get the selected sort criteria from the Spinner
                String sortCriteria = (String) adapterView.getItemAtPosition(i);

                // Sort the list of vehicles using the VehicleController
                try {
                    vehicleController = new VehicleController(ViewAllVehicles.this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                ArrayList<Vehicle> sortedVehicles = vehicleController.sort(sortCriteria);

                // Clear the current CardView list and add the sorted vehicles
                cardListLayout.removeAllViews();
                for (Vehicle vehicle : sortedVehicles) {
                    addCardView(vehicle);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });
    }

    private void addCardView(Vehicle vehicle) {
        // Inflate CardView layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View cardViewLayout = inflater.inflate(R.layout.card_vehicle, cardListLayout, false);

        // Set vehicle data in CardView elements
        MaterialTextView makeModelView = cardViewLayout.findViewById(R.id.make_model_text_view);
        MaterialTextView yearView = cardViewLayout.findViewById(R.id.year_text_view);
        MaterialTextView priceView = cardViewLayout.findViewById(R.id.price_text_view);
        MaterialTextView conditionView = cardViewLayout.findViewById(R.id.condition_text_view);
        MaterialTextView cylindersView = cardViewLayout.findViewById(R.id.cylinders_text_view);
        MaterialTextView doorsView = cardViewLayout.findViewById(R.id.doors_text_view);
        MaterialTextView colorView = cardViewLayout.findViewById(R.id.color_text_view);
        MaterialTextView soldView = cardViewLayout.findViewById(R.id.sold_text_view);

        // Load thumbnail image
        ImageView thumbnailImageView = cardViewLayout.findViewById(R.id.thumbnail_image_view);
        String thumbnailPath = vehicle.getThumbnailImage();
        if (thumbnailPath == null || thumbnailPath.equals("none")) {
            thumbnailImageView.setImageResource(R.drawable.ic_placeholder);
        } else {
            try {
                InputStream stream = getAssets().open(thumbnailPath);
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                thumbnailImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                thumbnailImageView.setImageResource(R.drawable.ic_placeholder);
            }
        }

        makeModelView.setText("Make/Model: " + vehicle.getMake() + " " + vehicle.getModel());
        yearView.setText(String.valueOf("Year: " +vehicle.getYear()));
        priceView.setText(String.valueOf("Price: " +vehicle.getPrice()));
        conditionView.setText("Condition: " +vehicle.getCondition());
        cylindersView.setText("Cylinder: " +vehicle.getEngineCylinders());
        doorsView.setText("Doors: " +String.valueOf(vehicle.getNumOfDoors()));
        colorView.setText("Color: " +vehicle.getColor());
        if (vehicle.getDateSold() != null) {
            soldView.setText("Sold on: " + vehicle.getDateSold().toString());
        } else {
            soldView.setVisibility(View.GONE);
            soldView.setText(""); // Set text to empty string instead of null
        }

        cardViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When card view is clicked, start ViewSingleVehicle activity with vehicle ID
                Intent intent = new Intent(ViewAllVehicles.this, ViewSingleVehicle.class);
                intent.putExtra("vehicle_id", vehicle.getID());
                startActivity(intent);
            }
        });

        // Add CardView to LinearLayout
        MaterialCardView cardView = (MaterialCardView) cardViewLayout;
        cardListLayout.addView(cardView);
    }
}

