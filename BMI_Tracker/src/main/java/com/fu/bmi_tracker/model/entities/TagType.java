/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
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
@Table(name = "TagType")
public class TagType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TagTypeID", nullable = false)
    private Integer tagTypeID;

    @Column(name = "TypeName", nullable = false)
    private String tagTypeName;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "TagTypeID")
    private List<Tag> tags;
}
