/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.util;

/**
 *
 * @author BaoLG
 */
public class PriceUtils {
    
    private static final int BASIC_SALARY_PER_MONTH = 18000000;
    
    public float priceUpperLimitCalculator() {
        //Implement price logic here...
        return BASIC_SALARY_PER_MONTH*10;
    }
}
