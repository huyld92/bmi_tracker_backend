/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Duc Huy
 */
@Component
public class DateTimeUtils {

    public LocalDateTime convertStrToLocalDateTime(String dateString) {
        // Define the format of the input String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        // Parse the String to LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);

        return localDateTime;
    }
}
