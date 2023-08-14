package com.comp1011.assignment3.controller;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class CompanyController {
    private final String COMPANY_INFO_FILE_NAME = "company_info.txt";
    private final String LOGO_IMAGE_FILE_NAME = "logo.png";

    public CompanyController(Context context) {
        // Copy the company info file from assets to device storage if needed
        copyCompanyInfoFileToDataDirectory(context);

        // Copy the logo image from assets to device storage if needed
        copyLogoImageToDataDirectory(context);
    }


    public void copyCompanyInfoFileToDataDirectory(Context context) {
        // Check if company info file exists in data directory
        File file = new File(context.getFilesDir(), COMPANY_INFO_FILE_NAME);
        if (file.exists()) {
            return;
        }

        // Copy company info file from assets to data directory
        try {
            InputStream inputStream = context.getAssets().open(COMPANY_INFO_FILE_NAME);
            OutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copyLogoImageToDataDirectory(Context context) {
        // Check if logo image file exists in data directory
        File file = new File(context.getFilesDir(), LOGO_IMAGE_FILE_NAME);
        if (file.exists()) {
            return;
        }

        // Copy logo image file from assets to data directory
        try {
            InputStream inputStream = context.getAssets().open(LOGO_IMAGE_FILE_NAME);
            OutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readCompanyInfoFile(Context context) {
        // Open the company info file input stream
        InputStream inputStream = null;
        try {
            inputStream = context.openFileInput(COMPANY_INFO_FILE_NAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Read the company info file contents
        String contents = "";
        if (inputStream != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                reader.close();
                inputStream.close();
                contents = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return contents;
    }

    public String getLogoImagePath(Context context) {
        // Check if logo image file exists in data directory
        File file = new File(context.getFilesDir(), LOGO_IMAGE_FILE_NAME);
        if (file.exists()) {
            return file.getAbsolutePath();
        }

        return null;
    }

    public String getCompanyName(Context context) throws IOException {
        InputStream inputStream = context.openFileInput("company_info.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String companyName = reader.readLine();
        reader.close();
        return companyName;
    }

    public String getCompanyAddress(Context context) throws IOException {
        InputStream inputStream = context.openFileInput("company_info.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        reader.readLine(); // Skip the first line (company name)
        String companyAddress = reader.readLine();
        reader.close();
        return companyAddress;
    }

    public void updateCompanyName(Context context, String companyName) {
        try {
            String companyAddress = getCompanyAddress(context);
            // Open the company info file for writing
            FileOutputStream outputStream = context.openFileOutput(COMPANY_INFO_FILE_NAME, Context.MODE_PRIVATE);
            // Write the company name to the file
            outputStream.write(companyName.getBytes());
            outputStream.write("\n".getBytes());

            // Write the existing company address to the file
            outputStream.write(companyAddress.getBytes());

            // Close the output stream
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateCompanyAddress(Context context, String companyAddress) {
        try {
            String companyName = getCompanyName(context);
            // Open the company info file for writing
            FileOutputStream outputStream = context.openFileOutput(COMPANY_INFO_FILE_NAME, Context.MODE_PRIVATE);

            // Write the existing company name to the file

            outputStream.write(companyName.getBytes());
            outputStream.write("\n".getBytes());

            // Write the company address to the file
            outputStream.write(companyAddress.getBytes());

            // Close the output stream
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

