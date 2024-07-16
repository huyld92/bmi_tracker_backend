/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Workout;
import com.fu.bmi_tracker.payload.response.CountWorkoutResponse;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Integer> {

    public Iterable<Workout> findByAdvisor_AdvisorID(Integer advisorID);

    public int countByAdvisor_AdvisorIDAndIsActiveTrue(Integer advisorID);

    @Query("SELECT new com.fu.bmi_tracker.payload.response.CountWorkoutResponse(YEAR(w.creationDate), MONTH(w.creationDate), COUNT(w)) "
            + "FROM Workout w "
            + "WHERE w.creationDate  BETWEEN :startDate AND :endDate "
            + "GROUP BY YEAR(w.creationDate), MONTH(w.creationDate) "
            + "ORDER BY YEAR(w.creationDate) DESC, MONTH(w.creationDate) DESC")
    public List<CountWorkoutResponse> countTotalWorkoutPerMonthInBetween(LocalDate startDate, LocalDate endDate);

}
