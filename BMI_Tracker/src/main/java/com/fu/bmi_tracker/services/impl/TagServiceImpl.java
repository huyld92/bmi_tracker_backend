/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Tag;
import com.fu.bmi_tracker.model.enums.ETagType;
import com.fu.bmi_tracker.repository.TagRepository;
import com.fu.bmi_tracker.services.TagService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository repository;

    @Override
    public Iterable<Tag> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Tag> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Tag save(Tag t) {
        return repository.save(t);
    }

    @Override
    public List<Tag> findByTagIDIn(List<Integer> tagIds) {
        return repository.findByTagIDIn(tagIds);
    }

    @Override
    public List<Tag> findByTagType(ETagType tagType) {
        return repository.findByTagTypeID(tagType.getId());
    }

    @Override
    public Iterable<Tag> getTagCreateFood() {
        // tạo danh sách tag type không phù hợp với food MealType, 
        List<Integer> tagTypeIDs = new ArrayList<>();
        tagTypeIDs.add(5); //Exercise Type
        tagTypeIDs.add(6);//Ingredient Type
        tagTypeIDs.add(7);// BMI Category

        return repository.findByIsActiveTrueAndTagTypeIDNotIn(tagTypeIDs);
    }

    @Override
    public Iterable<Tag> getTagCreateExercise() {
        // tạo danh sách tag type phù hợp với exercise
        List<Integer> tagTypeIDs = new ArrayList<>();
        tagTypeIDs.add(5); //Exercise Type
        tagTypeIDs.add(7);// BMI Category

        return repository.findByIsActiveTrueAndTagTypeIDIn(tagTypeIDs);
    }

    @Override
    public Iterable<Tag> getTagCreateIngredient() {
        // tạo danh sách tag type phù hợp với exercise 
        int tagID = 6; // Ingredient Type
        return repository.findByTagTypeID(tagID);
    }

}
