/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Blog;

/**
 *
 * @author BaoLG
 */
public interface BlogService extends GeneralService<Blog>{
    
    public Blog findByBlogName(String blogName);
    
    public Iterable<Blog> findByAdvisorID(int advisorID);
    
    public Iterable<Blog> findByIsActiveTrue();
    
    public void deleteBlog(Blog blog);
    
}
