/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.payload.request.CreateTagRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Duc Huy
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TagID")
    private Integer tagID;

    @Column(name = "TagName")
    private String tagName;

    @Column(name = "TagDescription")
    private String tagDescription;

    @Column(name = "TagTypeID")
    private Integer tagTypeID;

    @Column(name = "IsActive")
    private Boolean isActive;

    public Tag(CreateTagRequest createTagRequest) {
        this.tagName = createTagRequest.getTagName();
        this.tagDescription = createTagRequest.getTagDescription();
        this.tagTypeID = createTagRequest.getTagTypeID();
        this.isActive = true;
    }

    public Tag(Integer tagID) {
        this.tagID = tagID;
    }

}
