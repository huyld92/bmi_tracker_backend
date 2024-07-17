/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Duc Huy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountMenuResponse {

    private String yearMonth;
    private Long totalMenu;

    public CountMenuResponse(Integer year, Integer month, Long totalMenu) {
        this.yearMonth = Integer.toString(year) + "-" + Integer.toString(month);
        this.totalMenu = totalMenu;
    }
}
