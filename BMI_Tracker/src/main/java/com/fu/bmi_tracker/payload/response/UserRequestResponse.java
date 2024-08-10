/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.UserRequest;
import java.time.LocalDate;
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
public class UserRequestResponse {

    private Integer userRequestID;
    private String type;
    private String purpose;
    private String processNote;
    private String status;
    private LocalDate creationDate;
    private LocalDate processingDate;

    public UserRequestResponse(UserRequest userRequest) {
        this.userRequestID = userRequest.getUserRequestID();
        this.type = userRequest.getType();
        this.purpose = userRequest.getPurpose();
        this.processNote = userRequest.getProcessNote();
        this.status = userRequest.getStatus();
        this.creationDate = userRequest.getCreationDate();
        this.processingDate = userRequest.getProcessingDate();
    }

    
}
