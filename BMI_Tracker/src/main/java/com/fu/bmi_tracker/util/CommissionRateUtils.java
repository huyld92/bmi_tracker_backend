/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.stereotype.Component;

/**
 *
 * @author BaoLG
 */
@Component
public class CommissionRateUtils {

    //@Value("${commissionRate.configFile}")
    private final String fileName = "commission-rate.txt";

    private void createDefaultCommissionRateFile() {

        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("creating default");
            try {
                Files.createDirectories(Paths.get("src/main/resources/"));
                file.createNewFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write("0.05");
                    writer.close();
                    System.out.println("New file created and default commisionrate is 0.05 (5%).");
                }
            } catch (IOException ex) {
                System.out.println("Can't create the file");
            }
        }
    }

    public float getCommissionRate() {
        //createDefaultCommissionRateFile();
        System.out.println("Get commission");
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            float commisionRate = 0;
            while ((line = reader.readLine()) != null) {
                commisionRate = Float.parseFloat(line.trim());
                System.out.println(commisionRate);
            }
            if (reader != null) {
                reader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }

            return commisionRate;
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Can't convert");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //Can't read or something error when read the file.
        return 0;
    }

    public void updateCommissionRate(float inputeRate) {
        String line = inputeRate + "";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/" + fileName));
            writer.write(line);
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

}
