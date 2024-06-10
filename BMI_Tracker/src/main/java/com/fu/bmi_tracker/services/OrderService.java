/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Order;

/**
 *
 * @author Duc Huy
 */
public interface OrderService extends GeneralService<Order> {

    public Iterable<Order> getOrderByMemberAccountID(Integer id);

    public Iterable<Order> getOrderByMemberID(Integer memberID);

    public Iterable<Order> getOrderByAdvisorIDAndMonth(Integer advisorID, String month);
    
}
