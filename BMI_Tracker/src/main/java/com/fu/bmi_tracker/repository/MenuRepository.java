/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    public Iterable<Menu> findByAdvisor_AdvisorID(Integer advisorID);

    public int countByAdvisor_AdvisorIDAndIsActiveTrue(Integer advisorID);

}
