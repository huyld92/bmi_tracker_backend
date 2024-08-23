/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.MemberBodyMass;
import com.fu.bmi_tracker.repository.MemberBodyMassRepository;
import com.fu.bmi_tracker.services.MemberBodyMassService;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberBodyMassServiceImpl implements MemberBodyMassService {

    @Autowired
    private MemberBodyMassRepository repository;

    @Override
    public Iterable<MemberBodyMass> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<MemberBodyMass> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public MemberBodyMass save(MemberBodyMass t) {
        return repository.save(t);
    }

    @Override
    public MemberBodyMass getLatestBodyMass(Integer memberID) {
        return repository.findFirstByMemberMemberIDOrderByDateInputDesc(memberID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find body mass of member!"));
    }

    @Override
    public Iterable<MemberBodyMass> findAllByAccountID(Integer accountID) {
        return repository.findAllByAccountID(accountID);
    }

    @Override
    public Iterable<MemberBodyMass> findAllWithMonth(Integer accountID, LocalDate endDate) {

        // Trừ đi 30 ngày
        LocalDate startDate = endDate.minusDays(30);

        // gọi repository tìm tất cả bodymass trong 30 ngày
        return repository.findRecent30Days(accountID, startDate, endDate);

    }

    @Override
    public Iterable<MemberBodyMass> getBodyMassInMonthByMemberID(Integer memberID, LocalDate endDate) {

        // Trừ đi 30 ngày
        LocalDate startDate = endDate.minusDays(30);

        // gọi repository tìm tất cả bodymass trong 30 ngày
        return repository.findRecentIn30DaysByMemberID(memberID, startDate, endDate);
    }

}
