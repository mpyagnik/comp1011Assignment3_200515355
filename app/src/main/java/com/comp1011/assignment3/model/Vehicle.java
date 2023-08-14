package com.comp1011.assignment3.model;

import java.util.Date;

public class Vehicle {
    private int id;
    private String make;
    private String model;
    private String condition;
    private String engineCylinders;
    private int year;
    private int numOfDoors;
    private double price;
    private String color;
    private String thumbnailImage;
    private String fullSizeImage;
    private Date dateSold;


    public Vehicle(String make, String model, String condition, String engineCylinders, int year, int numOfDoors,
                   double price, String color, String thumbnailImage, String fullSizeImage) {
        this.make = make;
        this.model = model;
        this.condition = condition;
        this.engineCylinders = engineCylinders;
        this.year = year;
        this.numOfDoors = numOfDoors;
        this.price = price;
        this.color = color;
        this.thumbnailImage = thumbnailImage;
        this.fullSizeImage = fullSizeImage;
    }

    public Vehicle(String make, String model, String condition, String engineCylinders, int year, int numOfDoors,
                   double price, String color, String thumbnailImage, String fullSizeImage, Date dateSold) {
        this.make = make;
        this.model = model;
        this.condition = condition;
        this.engineCylinders = engineCylinders;
        this.year = year;
        this.numOfDoors = numOfDoors;
        this.price = price;
        this.color = color;
        this.thumbnailImage = thumbnailImage;
        this.fullSizeImage = fullSizeImage;
        this.dateSold = dateSold;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    // Getter methods
    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getCondition() {
        return condition;
    }

    public String getEngineCylinders() {
        return engineCylinders;
    }

    public int getYear() {
        return year;
    }

    public int getNumOfDoors() {
        return numOfDoors;
    }

    public double getPrice() {
        return price;
    }

    public String getColor() {
        return color;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public String getFullSizeImage() {
        return fullSizeImage;
    }

    public Date getDateSold() {
        return dateSold;
    }

    // Setter methods
    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setEngineCylinders(String engineCylinders) {
        this.engineCylinders = engineCylinders;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setNumOfDoors(int numOfDoors) {
        this.numOfDoors = numOfDoors;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public void setFullSizeImage(String fullSizeImage) {
        this.fullSizeImage = fullSizeImage;
    }

    public void setDateSold(Date dateSold) {
        this.dateSold = dateSold;
    }
}

