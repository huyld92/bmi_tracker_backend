/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.MemberBodyMass;
import java.util.Optional;

/**
 *
 * @author Duc Huy
 */
public interface MemberBodyMassService extends GeneralService<MemberBodyMass> {

    public Optional<MemberBodyMass> findTopByMemberMemberIDOrderByDateInputDesc(Integer memberID);

    public Iterable<MemberBodyMass> findAllByAccountID(Integer accountID);
}
