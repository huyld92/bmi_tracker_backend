/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Advisor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface AdvisorRepository extends JpaRepository<Advisor, Integer> {

    public Optional<Advisor> findByAccount_AccountID(Integer accountID);

    public List<Advisor> findAllByIsActiveTrue();

    @Query("SELECT COUNT(m) FROM Menu m WHERE m.advisor.advisorID = :advisorID")
    public Integer countMenusByAdvisorID(Integer advisorID);

    @Query("SELECT COUNT(w) FROM Workout w WHERE w.advisorID = :advisorID")
    public Integer countWorkoutsByAdvisorID(Integer advisorID);
}
