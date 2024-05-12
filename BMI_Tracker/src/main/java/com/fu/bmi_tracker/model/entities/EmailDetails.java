/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

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
public class EmailDetails {
        // Class data members
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
