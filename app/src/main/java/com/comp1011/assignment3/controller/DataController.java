package com.comp1011.assignment3.controller;

import android.content.Context;

import com.comp1011.assignment3.model.Vehicle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
public class DataController {
    private static final String CSV_FILE_NAME = "vehicles.csv";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy", Locale.getDefault());

    public static ArrayList<Vehicle> readCsv(Context context) throws IOException, ParseException {
        ArrayList<Vehicle> vehicles = new ArrayList<>();

        // Open the CSV file input stream
        InputStream inputStream;
        try {
            // Try to open the file in the app's data directory
            inputStream = context.openFileInput(CSV_FILE_NAME);
        } catch (FileNotFoundException e) {
            // If the file does not exist, copy the initial CSV file from the assets folder
            copyInitialCsvToDataDirectory(context);
            inputStream = context.openFileInput(CSV_FILE_NAME);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        // Skip the header row
        reader.readLine();

        // Parse each line of the CSV file into a Vehicle object and add it to the list
        String line;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            int id = Integer.parseInt(fields[0]);
            String make = fields[1];
            String model = fields[2];
            String condition = fields[3];
            String engineCylinders = fields[4];
            int year = Integer.parseInt(fields[5]);
            int numOfDoors = Integer.parseInt(fields[6]);
            double price = Double.parseDouble(fields[7]);
            String color = fields[8];
            String thumbnailImage = fields[9];
            String fullSizeImage = fields[10];
            Date dateSold = null;

            if (fields.length == 12 && !fields[11].isEmpty()) {
                dateSold = DATE_FORMAT.parse(fields[11]);
            } else {
                dateSold = null;
            }

            Vehicle vehicle = new Vehicle(make, model, condition, engineCylinders, year, numOfDoors, price, color,
                    thumbnailImage, fullSizeImage, dateSold);
            vehicle.setID(id);
            vehicles.add(vehicle);
        }

        // Close the input stream
        reader.close();

        return vehicles;
    }

    public static void writeCsv(Context context, ArrayList<Vehicle> vehicles) throws IOException {
        // Check if the CSV file exists in the app's data directory
        File csvFile = new File(context.getFilesDir(), CSV_FILE_NAME);
        boolean isNewFile = !csvFile.exists();

        // Open the CSV file output stream
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(CSV_FILE_NAME, Context.MODE_PRIVATE));

        // Write the CSV header row
        outputStreamWriter.write("id,Make,Model,Condition,Engine Cylinders,Year,Number of Doors,Price,Color,Thumbnail Image,Full Size Image,Date Sold\n");

        // Write each vehicle to the CSV file
        for (Vehicle vehicle : vehicles) {
            outputStreamWriter.write(String.format(Locale.getDefault(), "%d,%s,%s,%s,%s,%d,%d,%.2f,%s,%s,%s,%s\n", vehicle.getID(),
                    vehicle.getMake(), vehicle.getModel(), vehicle.getCondition(), vehicle.getEngineCylinders(),
                    vehicle.getYear(), vehicle.getNumOfDoors(), vehicle.getPrice(), vehicle.getColor(),
                    vehicle.getThumbnailImage(), vehicle.getFullSizeImage(), vehicle.getDateSold() != null ? DATE_FORMAT.format(vehicle.getDateSold()) : ""));
        }

        // Close the output stream
        outputStreamWriter.close();

        // If the CSV file didn't exist before, copy the initial CSV file to the app's data directory
        if (isNewFile) {
            copyInitialCsvToDataDirectory(context);
        }
    }

    private static void copyInitialCsvToDataDirectory(Context context) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = context.getAssets().open(CSV_FILE_NAME);
            outputStream = new FileOutputStream(new File(context.getFilesDir(), CSV_FILE_NAME));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

