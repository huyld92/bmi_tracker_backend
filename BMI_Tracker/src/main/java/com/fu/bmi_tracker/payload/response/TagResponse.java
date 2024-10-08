/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Tag;
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
public class TagResponse {

    private Integer tagID;
    private String tagName;
    private String tagDescription;

    public TagResponse(Tag tag) {
        this.tagID = tag.getTagID();
        this.tagName = tag.getTagName();
        this.tagDescription = tag.getTagDescription();
    }

}
