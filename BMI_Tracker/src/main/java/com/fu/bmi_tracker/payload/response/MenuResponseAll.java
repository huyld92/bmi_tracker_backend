/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Menu;
import java.util.List;
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
public class MenuResponseAll {

    private Integer menuID;
    private String menuName;
    private String menuDescription;
    private Integer totalCalories;
    private Boolean isActive;
    private Integer advisorID;
    private String advisorName;
    private List<String> membersUsing;

    public MenuResponseAll(Menu menu, List<String> membersUsing) {
        this.menuID = menu.getMenuID();
        this.menuName = menu.getMenuName();
        this.menuDescription = menu.getMenuDescription();
        this.totalCalories = menu.getTotalCalories();
        this.isActive = menu.getIsActive();
        this.advisorID = menu.getAdvisor().getAdvisorID();
        this.membersUsing = membersUsing;
        this.advisorName = menu.getAdvisor().getAccount().getFullName();
    }
}
