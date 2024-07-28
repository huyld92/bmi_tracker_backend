/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Menu;
import com.fu.bmi_tracker.model.entities.MenuFood;
import com.fu.bmi_tracker.payload.request.CreateMenuFoodRequest;
import com.fu.bmi_tracker.payload.response.CountMenuResponse;
import java.util.List;

/**
 *
 * @author Duc Huy
 */
public interface MenuService extends GeneralService<Menu> {

    public Iterable<Menu> getAllByAdvisorID(Integer advisorID);

    public Menu createNewMenu(Menu menu);

    public List<CountMenuResponse> countTotalMenuIn6Months();

    public MenuFood createNewMenuFood(CreateMenuFoodRequest menuFoodRequest);

    public List<MenuFood> createNewMenuFoods(List<CreateMenuFoodRequest> menuFoodRequests);

}
