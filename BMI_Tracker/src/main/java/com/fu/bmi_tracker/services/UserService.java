/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.User;
import java.util.Optional;

/**
 *
 * @author Duc Huy
 */
public interface UserService extends GeneralService<User> {

    public boolean existsByAccountID(int accountID);

    public Optional<User> findByAccountID(int accountID);

}
