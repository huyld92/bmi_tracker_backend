/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Blog;
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
public class BlogGetAllResponse {

    private Integer blogID;
    private String blogName;
    private String blogContent;
    private String blogPhoto;
    private String link;
    private Boolean isActive;
    private Integer advisorID;
    private String advisorName;

    public BlogGetAllResponse(Blog blog) {
        this.blogID = blog.getBlogID();
        this.blogName = blog.getBlogName();
        this.blogContent = blog.getBlogContent();
        this.blogPhoto = blog.getBlogPhoto();
        this.link = blog.getLink();
        this.isActive = blog.getIsActive();
        this.advisorID = blog.getAdvisor().getAdvisorID();
        this.advisorName = blog.getAdvisor().getAccount().getFullName();
    }

}
