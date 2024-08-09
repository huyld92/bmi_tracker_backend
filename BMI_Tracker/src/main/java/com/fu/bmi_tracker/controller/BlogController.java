/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Blog;
import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.payload.request.CreateBlogRequest;
import com.fu.bmi_tracker.payload.response.BlogGetAllResponse;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.services.BlogService;
import com.fu.bmi_tracker.payload.request.UpdateBlogRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    BlogService blogService;

    @Operation(
            summary = "Create new Blog with form (ADVISOR)",
            description = "Create new blog with form")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = MessageResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> createNewBlog(@RequestBody CreateBlogRequest blogRequest) {
        // tìm accountID từ context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //Gọi blogService để tạo mới blog
        Blog saveBlog = blogService.createBlog(blogRequest, principal.getId());

        //If fail to save new blog
        if (saveBlog == null) {
            return new ResponseEntity<>(new MessageResponse("Failed to create new Blog"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //If success to save new plan
        return new ResponseEntity<>(new MessageResponse("Blog is successfully created!"), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get All Blog",
            description = "Get All Blog")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = BlogGetAllResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getAll")
    //@PreAuthorize("hasRole('ADMIN')") //MEMBER, ADVISOR, STAFF
    public ResponseEntity<?> getAllBlog() {

        Iterable<Blog> blogList = blogService.findAllWithActive();

        if (!blogList.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo BlogGetAllResponse
        List<BlogGetAllResponse> blogResponses = new ArrayList<>();
        blogList.forEach(blog -> {
            blogResponses.add(new BlogGetAllResponse(blog));
        });

        return new ResponseEntity<>(blogResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Blog of advisor (ADVISOR)",
            description = "Get All Blog of advisor personally")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = BlogGetAllResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/advisor/getAll")
    @PreAuthorize("hasRole('ADVISOR')") //MEMBER, ADVISOR, STAFF
    public ResponseEntity<?> getAllOfAdvisor() {
        // Lấy accountID từ context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // gọi blogService tim tất cả blog của advisor
        Iterable<Blog> blogList = blogService.findAllOfAdvisor(principal.getId());

        if (!blogList.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo BlogGetAllResponse
        List<BlogGetAllResponse> blogResponses = new ArrayList<>();
        blogList.forEach(blog -> {
            blogResponses.add(new BlogGetAllResponse(blog));
        });

        return new ResponseEntity<>(blogResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Advisor's Blog",
            description = "Get all Blog by AdvisorID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = BlogGetAllResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getAllByAdvisorID/{id}")
    //@PreAuthorize("hasRole('ADVISOR')") MEMBER, ADVISOR, STAFF
    public ResponseEntity<?> getAllBlogByAdvisorID(@PathVariable("id") int id) {

        Iterable<Blog> blogList = blogService.findByAdvisorID(id);

        if (!blogList.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo BlogGetAllResponse
        List<BlogGetAllResponse> blogResponses = new ArrayList<>();
        blogList.forEach(blog -> {
            blogResponses.add(new BlogGetAllResponse(blog));
        });

        return new ResponseEntity<>(blogResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Get Blog details",
            description = "Get blog detials by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = BlogGetAllResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getByID/{id}")
    //@PreAuthorize("hasRole('ADVISOR')") MEMBER, ADVISOR, STAFF
    public ResponseEntity<?> getByID(@PathVariable("id") int id) {
        // GỌi blogService tìm blog by id 
        Optional<Blog> blog = blogService.findById(id);

        // kiểm tra kết quả
        if (!blog.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // tạo BlogGetAllResponse
        BlogGetAllResponse blogResponse = new BlogGetAllResponse(blog.get());

        return new ResponseEntity<>(blogResponse, HttpStatus.OK);
    }

    @Operation(summary = "Update a Blog")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = MessageResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())})})
    @PutMapping("/update")
    //@PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> updateBlog(@RequestBody UpdateBlogRequest blogDetails) {
        // Tìm blog bằng ID
        Optional<Blog> blog = blogService.findById(blogDetails.getBlogID());
        // kiểm tra kết quả
        if (blog.isPresent()) {
            blog.get().setBlogContent(blogDetails.getBlogContent());
            blog.get().setBlogName(blogDetails.getBlogName());
            blog.get().setBlogPhoto(blogDetails.getBlogPhoto());
            blog.get().setLink(blogDetails.getLink());

            return new ResponseEntity<>(blogService.save(blog.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResponse(("Cannot find blog with id{" + blogDetails.getBlogID() + "}")), HttpStatus.NOT_FOUND);
        }
    }

//    @Operation(summary = "Delete a Blog")
//    @ApiResponses({
//        @ApiResponse(responseCode = "204", content = {
//            @Content(schema = @Schema())}),
//        @ApiResponse(responseCode = "500", content = {
//            @Content(schema = @Schema())})})
//    @DeleteMapping("/delete/{id}")
//    //@PreAuthorize("hasRole('ADVISOR')")
//    public ResponseEntity<?> deleteBlog(@PathVariable("id") int id) {
//        // tìm blog bằng ID
//        Optional<Blog> blog = blogService.findById(id);
//
//        // kiểm tra kết quả
//        if (blog.isPresent()) {
//            // Delete blog
//            blogService.deleteBlog(blog.get());
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else {
//            return new ResponseEntity<>(new MessageResponse("Cannot find plan with id{" + id + "}"), HttpStatus.NOT_FOUND);
//        }
//    }
    @Operation(summary = "Deactivate a Blog")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/deactivate/{id}")
    //@PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> deactivateBlog(@PathVariable("id") int id) {
        // tìm blog bằng ID
        blogService.deactivateBlog(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
