/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Tag;
import com.fu.bmi_tracker.repository.TagRepository;
import com.fu.bmi_tracker.services.TagService;
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

}
