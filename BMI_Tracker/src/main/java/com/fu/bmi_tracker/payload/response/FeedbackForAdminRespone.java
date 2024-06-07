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
 * @author BaoLG
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackForAdminRespone {
    
    private int feedbackID;
    
    private String title;
    
    private String type;
    
    private String description;
    
    private boolean status;
    
    private int memberID;
    
    private String memberName;
}
