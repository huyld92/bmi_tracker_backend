/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.MemberBodyMass;
import java.time.LocalDate;

/**
 *
 * @author Duc Huy
 */
public interface MemberBodyMassService extends GeneralService<MemberBodyMass> {

    public MemberBodyMass getLatestBodyMass(Integer memberID);

    public Iterable<MemberBodyMass> findAllByAccountID(Integer accountID);

    public Iterable<MemberBodyMass> findAllWithMonth(Integer accountID, LocalDate localDate);

    public Iterable<MemberBodyMass> getBodyMassInMonthByMemberID(Integer memberID, LocalDate localDate);
}
