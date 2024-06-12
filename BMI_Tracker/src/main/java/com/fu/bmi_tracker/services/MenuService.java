/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Menu;
import java.util.List;

/**
 *
 * @author Duc Huy
 */
public interface MenuService extends GeneralService<Menu> {

    public Iterable<Menu> getAllByAdvisorID(Integer advisorID);

    public List<Menu> getMenuByTagName(String tagName);

}
