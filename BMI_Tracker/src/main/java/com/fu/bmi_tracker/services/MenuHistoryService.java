/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.MenuHistory;

/**
 *
 * @author Duc Huy
 */
public interface MenuHistoryService extends GeneralService<MenuHistory> {

    public Iterable<MenuHistory> getMenuHistoryOfMember(Integer memberID);

    public MenuHistory assignMenuToMember(Integer menuID, Integer memberID);

}
