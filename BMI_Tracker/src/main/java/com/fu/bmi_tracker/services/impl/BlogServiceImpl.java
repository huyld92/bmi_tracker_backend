/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.model.entities.Blog;
import com.fu.bmi_tracker.payload.request.CreateBlogRequest;
import com.fu.bmi_tracker.repository.AdvisorRepository;
import com.fu.bmi_tracker.repository.BlogRepository;
import com.fu.bmi_tracker.services.BlogService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author BaoLG
 */
@Service
public class BlogServiceImpl implements BlogService {
    
    @Autowired
    BlogRepository blogRepository;
    
    @Autowired
    AdvisorRepository advisorRepository;
    
    @Override
    public Blog findByBlogName(String blogName) {
        return blogRepository.findByBlogName(blogName);
    }
    
    @Override
    public Iterable<Blog> findByAdvisorID(int advisorID) {
        return blogRepository.findByAdvisorID(advisorID);
    }
    
    @Override
    public Iterable<Blog> findAll() {
        return blogRepository.findAll();
    }
    
    @Override
    public Optional<Blog> findById(Integer id) {
        return blogRepository.findById(id);
    }
    
    @Override
    public Blog save(Blog t) {
        return blogRepository.save(t);
    }
    
    @Override
    public void deleteBlog(Blog blog) {
        blogRepository.delete(blog);
    }
    
    @Override
    public Blog createBlog(CreateBlogRequest newBlog, Integer accountID) {
        // tìm Advisor 
        Advisor advisor = advisorRepository.findByAccount_AccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Advisor not found"));
        // tạo mới object blog để save
        Blog blog = new Blog();
        blog.setAdvisorID(advisor.getAdvisorID());
        blog.setBlogContent(newBlog.getBlogContent());
        blog.setBlogName(newBlog.getBlogName());
        blog.setBlogPhoto(newBlog.getBlogPhoto());
        blog.setLink(newBlog.getLink());
        blog.setActive(true);

        //gọi repository để save
        return blogRepository.save(blog);
    }
    
    @Override
    public Iterable<Blog> findAllOfAdvisor(Integer accountID) {
        // Tim advisor từ accountID
        Advisor advisor = advisorRepository.findByAccount_AccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find advisor!"));

        // gọi repository tìm tất cả blog
        return blogRepository.findByAdvisorID(advisor.getAdvisorID());
    }
    
    @Override
    public void deactivateBlog(int blogID) {
        // tìm blog bằng blogID
        Blog blog = blogRepository.findById(blogID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find blog!"));

        // deactivate blog bằng set IsActive = false
        blog.setActive(false);

        // cập nhaatj BLog vào DB
        save(blog);
    }
    
}
