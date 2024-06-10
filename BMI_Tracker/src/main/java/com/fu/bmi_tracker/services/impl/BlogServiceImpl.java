/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Blog;
import com.fu.bmi_tracker.repository.BlogRepository;
import com.fu.bmi_tracker.services.BlogService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author BaoLG
 */
@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    BlogRepository repository;
    
    @Override
    public Blog findByBlogName(String blogName) {
        return repository.findByBlogName(blogName);
    }

    @Override
    public Iterable<Blog> findByAdvisorID(int advisorID) {
        return repository.findByAdvisorID(advisorID);
    }

    @Override
    public Iterable<Blog> findByIsActiveTrue() {
        return repository.findByIsActiveTrue();
    }

    @Override
    public Iterable<Blog> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Blog> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Blog save(Blog t) {
        return repository.save(t);
    }

    @Override
    public void deleteBlog(Blog blog) {
        repository.delete(blog);
    }
    
}
