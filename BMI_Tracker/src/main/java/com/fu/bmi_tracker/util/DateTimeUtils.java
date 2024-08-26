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

        // Xác định ngày dự kiến thanh toán
        int dayOfMonth;

        // Kiểm tra nếu ngày kết thúc trước ngày 10 thì ngày thanh toán là ngày 10
        if (endDate.getDayOfMonth() < 10) {
            dayOfMonth = 10;
        } else {
            // Nếu ngày kết thúc là ngày 25, thanh toán vào ngày 10 tháng sau
            if (endDate.getDayOfMonth() == 25) {
                month += 1; // Tăng tháng

                // Nếu tháng vượt quá 12, tăng năm và đặt tháng về 1
                if (month > 12) {
                    month = 1;
                    year += 1;
                }

                return LocalDate.of(year, month, 10);
            } else {
                // Ngày thanh toán mặc định là ngày 25
                dayOfMonth = 25;
            }
        }

        // Tạo ExpectedPaymentDate và kiểm tra xem ngày có hợp lệ không 
        return LocalDate.of(year, month, dayOfMonth);
    }

}
