/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Exercise;
import com.fu.bmi_tracker.model.entities.Member;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fu.bmi_tracker.repository.MemberRepository;
import com.fu.bmi_tracker.repository.WorkoutExerciseRepository;
import com.fu.bmi_tracker.services.MemberService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;
    
    @Autowired
    WorkoutExerciseRepository workoutExerciseRepository;
    
    @Override
    public Iterable<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> findById(Integer id) {
        return memberRepository.findById(id);
    }

    @Override
    public Member save(Member t) {
        return memberRepository.save(t);
    }

    @Override
    public boolean existsByAccountID(int accountID) {
        return memberRepository.existsByAccountID(accountID);
    }

    @Override
    public Optional<Member> findByAccountID(int accountID) {
        return memberRepository.findByAccountID(accountID);
    }

    @Override
    public List<Exercise> getllExerciseResponseInWorkout(Integer accountID) {
        // Find member by accountID
        Member member = this.findByAccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find member!"));
        
        return workoutExerciseRepository.findExercisesByWorkoutID(member.getWorkoutID());

    }

}
