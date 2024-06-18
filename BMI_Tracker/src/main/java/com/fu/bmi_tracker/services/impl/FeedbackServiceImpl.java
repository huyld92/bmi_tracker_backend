/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Feedback;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fu.bmi_tracker.repository.FeedbackRepository;
import com.fu.bmi_tracker.services.FeedbackService;

/**
 *
 * @author BaoLG
 */
@Service
public class FeedbackServiceImpl implements FeedbackService{
    
    @Autowired
    FeedbackRepository feedbackRepository;
    
    

    @Override
    public Iterable<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public Optional<Feedback> findById(Integer id) {
        
        return feedbackRepository.findById(id);
    }

    @Override
    public Feedback save(Feedback t) {
        return feedbackRepository.save(t);
    }

    @Override
    public Iterable<Feedback> findFeedbackByMemberID(int memberID) {
        return feedbackRepository.findByMember_MemberID(memberID);
    }

    
    
    
}
