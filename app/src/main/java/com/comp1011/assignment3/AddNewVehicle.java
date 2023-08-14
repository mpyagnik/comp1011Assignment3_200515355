package com.comp1011.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.comp1011.assignment3.controller.VehicleController;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNewVehicle extends AppCompatActivity {

    private EditText makeEditText;
    private EditText modelEditText;
    private EditText conditionEditText;
    private EditText engineCylindersEditText;
    private EditText yearEditText;
    private EditText numOfDoorsEditText;
    private EditText priceEditText;
    private EditText colorEditText;
    private EditText thumbnailImageEditText;
    private EditText fullSizeImageEditText;
    private EditText dateSoldEditText;

    private VehicleController vehicleController;
    private Button saveBtn, exitBtn;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_vehicle);

        try {
            vehicleController = new VehicleController(this);
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing VehicleController", Toast.LENGTH_SHORT).show();
        }

        makeEditText = findViewById(R.id.make_edit_text);
        modelEditText = findViewById(R.id.model_edit_text);
        conditionEditText = findViewById(R.id.condition_edit_text);
        engineCylindersEditText = findViewById(R.id.cylinders_edit_text);
        yearEditText = findViewById(R.id.year_edit_text);
        numOfDoorsEditText = findViewById(R.id.doors_edit_text);
        priceEditText = findViewById(R.id.price_edit_text);
        colorEditText = findViewById(R.id.color_edit_text);
        dateSoldEditText = findViewById(R.id.date_sold_edit_text);

        saveBtn = findViewById(R.id.save_button);
        exitBtn = findViewById(R.id.exit_button);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveClicked(v);
            }
        });


    }

    public void onSaveClicked(View view) {
        String make = makeEditText.getText().toString().trim();
        String model = modelEditText.getText().toString().trim();
        String condition = conditionEditText.getText().toString().trim();
        String engineCylinders = engineCylindersEditText.getText().toString().trim();
        String yearStr = yearEditText.getText().toString().trim();
        String numOfDoorsStr = numOfDoorsEditText.getText().toString().trim();
        String priceStr = priceEditText.getText().toString().trim();
        String color = colorEditText.getText().toString().trim();
        String thumbnailImage = "none";
        String fullSizeImage = "none";
        String dateSoldStr = dateSoldEditText.getText().toString().trim();

        // Validate inputs
        if (make.isEmpty() || model.isEmpty() || condition.isEmpty() || engineCylinders.isEmpty() || yearStr.isEmpty()
                || numOfDoorsStr.isEmpty() || priceStr.isEmpty() || color.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int year = Integer.parseInt(yearStr);
        int numOfDoors = Integer.parseInt(numOfDoorsStr);
        double price = Double.parseDouble(priceStr);
        Date dateSold = null;
        if (!dateSoldStr.isEmpty()) {
            try {
                dateSold = DATE_FORMAT.parse(dateSoldStr);
            } catch (ParseException e) {
                Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Save vehicle details
        // add new vehicle to list
        vehicleController.addVehicle(make, model, condition, engineCylinders, year, numOfDoors, price, color,
                thumbnailImage, fullSizeImage, null);

        // save changes
                try {
                    vehicleController.saveChanges(getApplicationContext());
                    Toast.makeText(getApplicationContext(), "Vehicle added successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Error saving changes", Toast.LENGTH_SHORT).show();
                }

        // return to previous activity
                finish();
    }
}