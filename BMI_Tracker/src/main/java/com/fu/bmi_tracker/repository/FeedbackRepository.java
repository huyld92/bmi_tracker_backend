/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author BaoLG
 */
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    public Iterable<Feedback> findByMember_MemberID(int memberID);
}
