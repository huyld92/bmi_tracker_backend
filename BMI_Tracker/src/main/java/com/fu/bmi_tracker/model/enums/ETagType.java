/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.enums;

/**
 *
 * @author Duc Huy
 */
public enum ETagType {
    MEAL_TYPE(1, "Meal Type"),
    SPECIAL_DIET(2, "Special Diet"),
    ALLERGY(3, "Allergy"),
    DISH_TYPE(4, "Dish Type"),
    EXERCISE_TYPE(5, "Exercise Type"),
    INGREDIENT_TYPE(6, "Ingredient Type"),
    BMI_CATEGORY(7, "BMI Category");

    private final int id;
    private final String typeName;

    ETagType(int id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public int getId() {
        return id;
    }

    public String getTypeName() {
        return typeName;
    }

    public static ETagType fromId(int id) {
        for (ETagType type : ETagType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("No TagType with id " + id);
    }
}
