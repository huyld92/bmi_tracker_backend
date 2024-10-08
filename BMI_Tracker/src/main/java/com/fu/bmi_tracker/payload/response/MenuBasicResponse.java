/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Duc Huy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuBasicResponse {

    private Integer menuID;
    private String menuName;
    private String menuDescription;
    private Integer totalCalories;
    private Boolean isActive;
    private Integer advisorID;
    private String advisorName;

    public MenuBasicResponse(Menu menu) {
        this.menuID = menu.getMenuID();
        this.menuName = menu.getMenuName();
        this.menuDescription = menu.getMenuDescription();
        this.totalCalories = menu.getTotalCalories();
        this.isActive = menu.getIsActive();
        this.advisorID = menu.getAdvisor().getAdvisorID();
    }
}
