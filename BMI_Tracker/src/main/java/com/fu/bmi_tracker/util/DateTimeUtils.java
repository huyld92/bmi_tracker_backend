/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.util;

import java.time.LocalDate;
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

    
    //Tính ngày dự kiến thanh toán cho advisor
    public LocalDate calculateExpectedPaymentDate(LocalDate endDate) {
        // lấy month và year từ endDate
        int month = endDate.getMonthValue();
        int year = endDate.getYear();

        // Mặc định là ngày 30
        int dayOfMonth = 30;

        // kiểm tra nếu ngày kế thúc trước ngày 15 thì Ngày kết toán là 15
        if (endDate.getDayOfMonth() < 15) {
            dayOfMonth = 15; // Set to 15th day of the month
        } else if (dayOfMonth > endDate.lengthOfMonth()) {
            // kiểm tra nếu tháng không có ngày 30
            dayOfMonth = endDate.lengthOfMonth();
        }

        // tạo ExpectedPaymentDate ngày dự kiến thanh toán 
        return LocalDate.of(year, month, dayOfMonth);
    }
}
