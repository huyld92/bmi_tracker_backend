/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.CustomerMenu;
import java.util.List;

/**
 *
 * @author Duc Huy
 */
public interface CustomerMenuService extends GeneralService<CustomerMenu> {

    public List<CustomerMenu> saveAll(Iterable<CustomerMenu> customerMenus);

    public List<CustomerMenu> findAllByCustomerID(Integer customerID);

    public void deleteByCustomerCustomerIdAndFoodFoodID(int customerID, int foodID);

}
