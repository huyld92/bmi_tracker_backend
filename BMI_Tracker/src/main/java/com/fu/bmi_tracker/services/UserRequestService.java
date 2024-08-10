/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.UserRequest;
import com.fu.bmi_tracker.payload.request.CreateUserRequest;
import com.fu.bmi_tracker.payload.request.UpdateUserRequestProcessing;

/**
 *
 * @author Duc Huy
 */
public interface UserRequestService extends GeneralService<UserRequest> {

    public Iterable<UserRequest> findByAccountID(Integer accountID);

    public UserRequest createNewUserRequest(CreateUserRequest createUserRequest, Integer id);

    public UserRequest updateProcessing(UpdateUserRequestProcessing userRequestProcessing);

}
