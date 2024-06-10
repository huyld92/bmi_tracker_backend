/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.TagFood;
import java.util.List;

/**
 *
 * @author Duc Huy
 */
public interface FoodTagService extends GeneralService<TagFood> {

    public List<TagFood> saveAll(List<TagFood> foodTags);
}
