/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.CustomerMenu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface CustomerMenuRepository extends JpaRepository<CustomerMenu, Integer> {

    public List<CustomerMenu> findAllByCustomerCustomerID(Integer customerID);

    public void deleteByCustomerCustomerIDAndFoodFoodID(int customerID, int foodID);

}
