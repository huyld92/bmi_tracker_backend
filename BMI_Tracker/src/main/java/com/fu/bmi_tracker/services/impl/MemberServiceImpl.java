/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Member;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fu.bmi_tracker.repository.MemberRepository;
import com.fu.bmi_tracker.services.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository repository;

    @Override
    public Iterable<Member> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Member> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Member save(Member t) {
        return repository.save(t);
    }

    @Override
    public boolean existsByAccountID(int accountID) {
        return repository.existsByAccountID(accountID);
    }

    @Override
    public Optional<Member> findByAccountID(int accountID) {
        return repository.findByAccountID(accountID);
    }

}
