package com.comp1011.assignment3.controller;

import android.content.Context;
import android.util.Log;

import com.comp1011.assignment3.model.Vehicle;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class VehicleController {
    private ArrayList<Vehicle> vehicles;
    Context context;
    public VehicleController(Context context) throws IOException, ParseException {
        this.context = context;
        vehicles = DataController.readCsv(context);
    }

    public ArrayList<Vehicle> getVehicles() {
        try {
            vehicles = DataController.readCsv(context);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return vehicles;
    }

    public void addVehicle(String make, String model, String condition, String engineCylinders, int year,
                           int numOfDoors, double price, String color, String thumbnailImage, String fullSizeImage,
                           Date dateSold) {
        int id = generateId();
        Vehicle vehicle = new Vehicle(make, model, condition, engineCylinders, year, numOfDoors, price, color,
                thumbnailImage, fullSizeImage, dateSold);
        vehicle.setID(id);
        vehicles.add(vehicle);
    }

    public void updateVehicle(int id, String make, String model, String condition, String engineCylinders, int year,
                              int numOfDoors, double price, String color, String thumbnailImage, String fullSizeImage,
                              Date dateSold) {
        Vehicle vehicle = getVehicleById(id);
        if (vehicle != null) {
            vehicle.setMake(make);
            vehicle.setModel(model);
            vehicle.setCondition(condition);
            vehicle.setEngineCylinders(engineCylinders);
            vehicle.setYear(year);
            vehicle.setNumOfDoors(numOfDoors);
            vehicle.setPrice(price);
            vehicle.setColor(color);
            vehicle.setThumbnailImage(thumbnailImage);
            vehicle.setFullSizeImage(fullSizeImage);
            vehicle.setDateSold(dateSold);
        }
    }

    public void removeVehicle(int id) {
        Vehicle vehicle = getVehicleById(id);
        if (vehicle != null) {
            vehicles.remove(vehicle);
        }
    }

    public ArrayList<Vehicle> getSoldVehicles() {
        ArrayList<Vehicle> soldVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getDateSold() != null) {
                Log.d("Sold Vehicle Date: ", String.valueOf(vehicle.getDateSold()));
                soldVehicles.add(vehicle);
            }
        }

        Log.d("Total Sold Vehicle: ", String.valueOf(soldVehicles.size()));
        return soldVehicles;
    }

    public ArrayList<Vehicle> getAvailableVehicles() {
        ArrayList<Vehicle> availableVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getDateSold() == null) {
                Log.d("Available Vehicle: ", String.valueOf(vehicle.getID()));
                availableVehicles.add(vehicle);
            }
        }
        return availableVehicles;
    }

    public Vehicle getVehicleById(int id) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getID() == id) {
                return vehicle;
            }
        }
        return null;
    }

    private int generateId() {
        int maxId = 0;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getID() > maxId) {
                maxId = vehicle.getID();
            }
        }
        return maxId + 1;
    }

    public ArrayList<Vehicle> sort(String sortCriteria) {
        switch (sortCriteria) {
            case "Make":
                vehicles.sort(Comparator.comparing(Vehicle::getMake));
                break;
            case "Model":
                vehicles.sort(Comparator.comparing(Vehicle::getModel));
                break;
            case "Condition":
                vehicles.sort(Comparator.comparing(Vehicle::getCondition));
                break;
            case "Engine Cylinders":
                vehicles.sort(Comparator.comparing(Vehicle::getEngineCylinders));
                break;
            case "Year":
                vehicles.sort(Comparator.comparing(Vehicle::getYear));
                break;
            case "Number of Doors":
                vehicles.sort(Comparator.comparing(Vehicle::getNumOfDoors));
                break;
            case "Price":
                vehicles.sort(Comparator.comparing(Vehicle::getPrice));
                break;
            default:
                break;
        }

        return vehicles;
    }


    public void saveChanges(Context context) throws IOException {
        DataController.writeCsv(context, vehicles);
    }
}

