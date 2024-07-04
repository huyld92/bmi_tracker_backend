/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Role;
import com.fu.bmi_tracker.model.enums.ERole;
import java.util.Optional;

/**
 *
 * @author Duc Huy
 */
public interface RoleService extends GeneralService<Role> {

    public Optional<Role> findByRoleName(ERole eRole);

}
