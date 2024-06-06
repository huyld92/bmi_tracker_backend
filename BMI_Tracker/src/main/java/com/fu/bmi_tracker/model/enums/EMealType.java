/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.fu.bmi_tracker.model.enums;

/**
 *
 * @author Duc Huy
 */
public enum EMealType {
    Breakfast,
    Lunch,
    Dinner,
    Snack;

    @Override
    public String toString() {
        switch (this) {
            case Breakfast -> {
                return "Breakfast";
            }
            case Lunch -> {
                return "Lunch";
            }
            case Dinner -> {
                return "Dinner";
            }
            case Snack -> {
                return "Snack";
            }
            default ->
                throw new IllegalArgumentException();
        }
    }
}
