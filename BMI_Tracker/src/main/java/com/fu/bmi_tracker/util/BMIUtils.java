/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.util;

import com.fu.bmi_tracker.model.enums.EBMI;
import com.fu.bmi_tracker.model.enums.EGender;
import org.springframework.stereotype.Component;

/**
 *
 * @author Duc Huy
 */
@Component
public class BMIUtils {

    // Method to calculate BMI
    public double calculateBMI(int weight, int height) {
        // BMI formula: weight (kg) / (height (m) * height (m))

        // convert height cm to m
        double heightInMeter = height / 100.0;

        return (weight / (heightInMeter * heightInMeter));
    }

    // Method to calculate BMR Mifflin St Jeor
    public double calculateBMR(double weight, double height, int age, EGender gender) {
        double bMR;
        if (EGender.Male.equals(gender)) {
            //( 10 x weight (kg) + 6.25 x height (cm) – 5 x age (y) + 5
            bMR = (10 * weight + 6.25 * height) - 5 * age + 5;
        } else {
            // 10 x weight (kg) + 6.25 x height (cm) – 5 x age (y) – 161
            bMR = (10 * weight + 6.25 * height) - 5 * age - 161;
        }

        return bMR;
    }

    // Method to calculate TDEE
    public double calculateTDEE(double bMR, double activityLevel) {
        return bMR * activityLevel;
    }

    // Phương pháp tính lượng calo mặc định
    public int calculateDefaultCalories(double tdee, double targetWeight) {
        // Calculation based on TDEE and weight goal
        // Adjust this based on your specific requirements
        return (int) (tdee + targetWeight);
    }

    // Phương thức phân loại BMI
    public String classifyBMI(double bmi) {
        if (bmi < 18.5) {
            return EBMI.LOW.toString();
        } else if (bmi < 25) {
            return EBMI.NORMAL.toString();
        } else if (bmi < 30) {
            return EBMI.OVERWEIGHT.toString();
        } else if (bmi < 35) {
            return EBMI.OBESE.toString();
        } else if (bmi < 40) {
            return EBMI.OBESE2.toString();
        } else {
            return "Morbidly Obese (Class 3)";
        }
    }
}
