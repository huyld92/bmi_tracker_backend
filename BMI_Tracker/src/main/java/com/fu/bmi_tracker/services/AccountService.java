/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Account;
import com.fu.bmi_tracker.payload.request.CreateAccountRequest;
import com.fu.bmi_tracker.payload.request.UpdateProfileRequest;

/**
 *
 * @author Duc Huy
 */
public interface AccountService extends GeneralService<Account> {

    public Account createAccount(CreateAccountRequest CreateAccountRequest, String password);

    public void updateDeviceToken(Integer accountID, String deviceToken);

    public void updateProfile(Integer accountID, UpdateProfileRequest updateProfileRequest);

    public void addMoreRole(Integer accountID, Integer roleID);

    public boolean existsByEmail(String email);

    public boolean existsByPhoneNumber(String phoneNumber);

}
