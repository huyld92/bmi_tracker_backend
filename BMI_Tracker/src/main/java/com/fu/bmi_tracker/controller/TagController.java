/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Tag;
import com.fu.bmi_tracker.model.entities.TagType;
import com.fu.bmi_tracker.model.enums.ETagType;
import com.fu.bmi_tracker.payload.request.CreateTagRequest;
import com.fu.bmi_tracker.payload.response.TagResponse;
import com.fu.bmi_tracker.payload.response.TagTypeResponse;
import com.fu.bmi_tracker.services.TagService;
import com.fu.bmi_tracker.util.TagConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Duc Huy
 */
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tag", description = "Tag management APIs")
@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    TagService tagService;

    @Operation(
            summary = "Create new tag with form",
            description = "Create new tag with form: include MultiplePath for tag photo",
            tags = {"Tag"})
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Tag.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    public ResponseEntity<?> createNewTag(@Valid @RequestBody CreateTagRequest createTagRequest) {

        Tag tag = new Tag(createTagRequest);

        Tag tagSave = tagService.save(tag);

        if (tagSave == null) {
            return new ResponseEntity<>("Failed to create new tag", HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(tagSave, HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve all Tags", tags = {"Tag"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Tag.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no Tags", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllTags() {

        Iterable<Tag> tags = tagService.findAll();

        if (!tags.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tags, HttpStatus.OK);

    }

    @Operation(
            summary = "Retrieve a Tag by Id",
            description = "Get a Tag object by specifying its id. The response is Tag object",
            tags = {"Tag"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Tag.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getByID/{id}")
    public ResponseEntity<?> getTagById(@PathVariable("id") int id) {
        Optional<Tag> tag = tagService.findById(id);

        if (tag.isPresent()) {
            return new ResponseEntity<>(tag, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find tag with id{" + id + "}", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Retrieve a Tag by Id",
            description = "Get a Tag object by specifying its id. The response is Tag object",
            tags = {"Tag"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Tag.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getByTagType")
    public ResponseEntity<?> getByTagType(@RequestParam ETagType tagType) {
        List<Tag> tags = tagService.findByTagType(tagType);

        return new ResponseEntity<>(tags, HttpStatus.OK);

    }

    @Operation(summary = "Retrieve all Tags to create food", tags = {"Tag"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = TagResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no Tags", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("food/getAll")
    public ResponseEntity<?> getAllToCreateFood() {

        // gọi tagService lấy danh sách tag phù hợp create food
        Iterable<Tag> tags = tagService.getTagCreateFood();

        if (!tags.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo tag Response
        List<TagResponse> tagResponses = new ArrayList<>();

        tags.forEach(tag -> tagResponses.add(new TagResponse(tag)));
        return new ResponseEntity<>(tagResponses, HttpStatus.OK);

    }

    @Operation(summary = "Retrieve all Tags to create exercise", tags = {"Tag"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = TagResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no Tags", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("exercise/getAll")
    public ResponseEntity<?> getAllToCreateExercise() {

        // gọi tagService lấy danh sách tag phù hợp create exercise
        Iterable<Tag> tags = tagService.getTagCreateExercise();

        if (!tags.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo tag Response
        List<TagResponse> tagResponses = new ArrayList<>();

        tags.forEach(tag -> tagResponses.add(new TagResponse(tag)));
        return new ResponseEntity<>(tagResponses, HttpStatus.OK);

    }

    @Operation(summary = "Retrieve all Tags to create ingredient", tags = {"Tag"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = TagResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no Tags", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("ingredient/getAll")
    public ResponseEntity<?> getAllToCreateIngredient() {

        // gọi tagService lấy danh sách tag phù hợp create ingredient
        Iterable<Tag> tags = tagService.getTagCreateIngredient();

        if (!tags.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo tag Response
        List<TagResponse> tagResponses = new ArrayList<>();

        tags.forEach(tag -> tagResponses.add(new TagResponse(tag)));
        return new ResponseEntity<>(tagResponses, HttpStatus.OK);

    }

    @Operation(summary = "Retrieve all tag food group by tagType", tags = {"Tag"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = TagResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no Tags", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("food/group-by-tag-type")
    public ResponseEntity<?> getAllGroupByTagType() {

        // gọi tagService lấy danh sách tag phù hợp create food
        Iterable<TagType> tagTypes = tagService.getTagsGroupByTagType();

        if (!tagTypes.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo tag type Response
        List<TagTypeResponse> tagResponses = new ArrayList<>();
        System.out.println("tagTypes: " + tagTypes.toString());
        tagTypes.forEach(tagType -> tagResponses.add(
                new TagTypeResponse(
                        tagType.getTagTypeID(),
                        tagType.getTagTypeName(),
                        TagConverter.convertToTagResponseList(tagType.getTags()))));
        return new ResponseEntity<>(tagResponses, HttpStatus.OK);

    }

        @Operation(summary = "Deactivate Tag", tags = {"Tag"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content( mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no Tags", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("deactivate")
    public ResponseEntity<?> getAllGroupByTagType(@RequestParam Integer tagID){
        tagService.deactivateTag(tagID);
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
