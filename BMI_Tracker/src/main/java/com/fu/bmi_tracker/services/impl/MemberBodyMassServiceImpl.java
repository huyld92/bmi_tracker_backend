/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.MemberBodyMass;
import com.fu.bmi_tracker.repository.MemberBodyMassRepository;
import com.fu.bmi_tracker.services.MemberBodyMassService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberBodyMassServiceImpl implements MemberBodyMassService {

    @Autowired
    MemberBodyMassRepository repository;

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
        System.out.println("aaaaaaaaa : "+ memberID);
        return repository.findFirstByMemberMemberIDOrderByDateInputDesc(memberID).get();

    }

    @Override
    public Iterable<MemberBodyMass> findAllByAccountID(Integer accountID) {
        return repository.findAllByAccountID(accountID);
    }

}
