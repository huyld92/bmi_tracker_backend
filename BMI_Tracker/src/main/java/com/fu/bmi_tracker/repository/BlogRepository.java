/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author BaoLG
 */
@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer>{
    
    public Blog findByBlogName(String blogName);
    
    public Iterable<Blog> findByAdvisor_AdvisorID(int advisorID);
    
    public Iterable<Blog> findByIsActiveTrue();
}
