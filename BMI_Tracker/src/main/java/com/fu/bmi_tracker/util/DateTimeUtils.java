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

    // Tính ngày dự kiến thanh toán cho advisor
    public LocalDate calculateExpectedPaymentDate(LocalDate endDate) {
        // Lấy month và year từ endDate
        int month = endDate.getMonthValue();
        int year = endDate.getYear();

        // Mặc định là ngày 25
        int dayOfMonth = 25;

        // Kiểm tra nếu ngày kết thúc trước ngày 10 thì Ngày kết toán là 10
        if (endDate.getDayOfMonth() < 10) {
            dayOfMonth = 10; // Set to 10th day of the month
        } else if (dayOfMonth > endDate.lengthOfMonth()) {
            // Kiểm tra nếu tháng không có ngày 25 (ví dụ tháng 2)
            dayOfMonth = endDate.lengthOfMonth();
        }

        // Tạo ExpectedPaymentDate ngày dự kiến thanh toán 
        return LocalDate.of(year, month, dayOfMonth);
    }

}
