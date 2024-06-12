/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.fu.bmi_tracker.model.enums;

/**
 *
 * @author Duc Huy
 */
public enum EBMI {
    LOW("BMI Low"),
    NORMAL("BMI Normal"),
    OVERWEIGHT("BMI Overweight"),
    OBESE("BMI Obese"),
    OBESE2("BMI Severely Obese");

    private final String label;

    EBMI(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
