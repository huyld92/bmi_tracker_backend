/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.UserMenu;
import java.util.List;

/**
 *
 * @author Duc Huy
 */
public interface UserMenuService extends GeneralService<UserMenu> {

    public List<UserMenu> saveAll(Iterable<UserMenu> userMenus);

    public List<UserMenu> findAllByUserID(Integer userID);

    public void deleteByUserUserIDAndFoodFoodID(int userID, int foodID);
    
    public void deleteAllByUserUserID(int userID);

}
