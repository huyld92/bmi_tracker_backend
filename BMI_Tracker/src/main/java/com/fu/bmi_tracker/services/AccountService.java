/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Account;
import com.fu.bmi_tracker.payload.request.CreateAccountRequest;

/**
 *
 * @author Duc Huy
 */
public interface AccountService extends GeneralService<Account> {

    public Account createAccount(CreateAccountRequest CreateAccountRequest, String password);

}
