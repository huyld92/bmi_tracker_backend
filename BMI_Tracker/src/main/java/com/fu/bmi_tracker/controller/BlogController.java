/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Blog;
import com.fu.bmi_tracker.services.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author BaoLG
 */
@Tag(name = "Blog", description = "Blog management APIs")
@RestController
@RequestMapping("/api/test/blogs")
public class BlogController {
    
    @Autowired
    BlogService service;
    
    @Operation(
            summary = "Create new Blog with form",
            description = "Create new blog with form")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Blog.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    //@PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> createNewBlog(@RequestBody Blog blogDetails) {
        
        //Convert createRequest to Plan before save
        Blog newBlog = new Blog();
        newBlog.setBlogName(blogDetails.getBlogName());
        newBlog.setBlogContent(blogDetails.getBlogContent());
        newBlog.setBlogPhoto(blogDetails.getBlogPhoto());
        newBlog.setLink(blogDetails.getLink());
        newBlog.setActive(Boolean.TRUE);
        
        Blog saveBlog = service.save(newBlog);
        //Call service to save plan
        
        
        //If fail to save new plan
        if (saveBlog == null) {
            return new ResponseEntity<>("Failed to create new Blog", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        //If success to save new plan
        return new ResponseEntity<>("Blog is successfully created", HttpStatus.CREATED);
    }
    
     @Operation(
            summary = "Get All Blog",
            description = "Get All Blog")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Blog.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getAll")
    //@PreAuthorize("hasRole('ADMIN')") //MEMBER, ADVISOR, STAFF
    public ResponseEntity<?> getAllBlog() {
        
        Iterable<Blog> blogList = service.findAll();
        
        if (!blogList.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
       
        return new ResponseEntity<>(blogList, HttpStatus.OK);
    }
    
    @Operation(
            summary = "Get All Advisor's Blog",
            description = "Get all Blog by AdvisorID")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Blog.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getAllByAdvisorID/{id}")
    //@PreAuthorize("hasRole('ADVISOR')") MEMBER, ADVISOR, STAFF
    public ResponseEntity<?> getAllBlogByAdvisorID(@PathVariable("id") int id) {
        
        Iterable<Blog> blogList = service.findByAdvisorID(id);
        
        if (!blogList.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(blogList, HttpStatus.OK);
    }
   
    @Operation(
            summary = "Get Blog details",
            description = "Get blog detials by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Blog.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getByID/{id}")
    //@PreAuthorize("hasRole('ADVISOR')") MEMBER, ADVISOR, STAFF
    public ResponseEntity<?> getByID(@PathVariable("id") int id) {
        
        Optional<Blog> blog = service.findById(id);
        
        if (blog.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(blog, HttpStatus.OK);
    }
    
    @Operation(summary = "Update a Blog")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Blog.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())})})
    @PutMapping("/update")
    //@PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> updateBlog(@RequestBody Blog blogDetails) {
        Optional<Blog> blog = service.findById(blogDetails.getBlogID());
        
        if (blog.isPresent()) {
            blog.get().setBlogContent(blogDetails.getBlogContent());
            blog.get().setBlogName(blogDetails.getBlogName());
            blog.get().setBlogPhoto(blogDetails.getBlogPhoto());
            blog.get().setLink(blogDetails.getLink());
            
            return new ResponseEntity<>(service.save(blog.get()), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Cannot find blog with id{" + blogDetails.getBlogID() + "}", HttpStatus.NOT_FOUND);
        }
    }
    
    @Operation(summary = "Delete a Blog")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/delete/{id}")
    //@PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> deleteBlog(@PathVariable("id") int id) {
        Optional<Blog> blog =  service.findById(id);

        if (blog.isPresent()) {
            service.deleteBlog(blog.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Cannot find plan with id{" + id + "}", HttpStatus.NOT_FOUND);
        }
    }
}
