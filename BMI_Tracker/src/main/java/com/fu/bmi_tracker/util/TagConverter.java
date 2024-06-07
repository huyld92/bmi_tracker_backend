/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.util;

import com.fu.bmi_tracker.model.entities.Tag;
import com.fu.bmi_tracker.payload.response.TagResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Duc Huy
 */

public class TagConverter {

    public static TagResponse convertToTagResponse(Tag tag) {
        TagResponse tagResponse = new TagResponse();
        tagResponse.setTagID(tag.getTagID());
        tagResponse.setTagName(tag.getTagName());
        return tagResponse;
    }

    public static List<TagResponse> convertToTagResponseList(List<Tag> tags) {
        return tags.stream()
                .map(TagConverter::convertToTagResponse)
                .collect(Collectors.toList());
    }
}
