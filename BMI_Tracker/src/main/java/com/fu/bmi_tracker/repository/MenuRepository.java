/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Menu;
import com.fu.bmi_tracker.payload.response.CountMenuResponse;
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
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    public Iterable<Menu> findByAdvisor_AdvisorID(Integer advisorID);

    public int countByAdvisor_AdvisorIDAndIsActiveTrue(Integer advisorID);

    @Query("SELECT new com.fu.bmi_tracker.payload.response.CountMenuResponse(YEAR(m.creationDate), MONTH(m.creationDate), COUNT(m)) "
            + "FROM Menu m "
            + "WHERE m.creationDate  BETWEEN :startDate AND :endDate "
            + "GROUP BY YEAR(m.creationDate), MONTH(m.creationDate) "
            + "ORDER BY YEAR(m.creationDate) DESC, MONTH(m.creationDate) DESC")
    public List<CountMenuResponse> countTotalMenuPerMonthInBetween(LocalDate startDate, LocalDate endDate);

}
