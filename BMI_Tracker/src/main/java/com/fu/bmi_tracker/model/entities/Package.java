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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
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
@Table(name = "[Package]")
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PackageID", nullable = false)
    private Integer packageID;

    @Column(name = "PackageCode", nullable = false)
    private String packageCode;

    @Column(name = "PackageName", nullable = false)
    private String packageName;

    @Column(name = "Price", nullable = false)
    private BigDecimal price;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "PackageDuration", nullable = false)
    private Integer packageDuration;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AdvisorID", nullable = false)
    private Advisor advisor;

    @Column(name = "NumberOfUses", nullable = false)
    private Integer numberOfUses;

    @Column(name = "PackageStatus", nullable = false)
    private String packageStatus;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;

    public Package(String packageName, BigDecimal price, String packageCode, String description,
            Integer packageDuration, Integer advisorID, String packageStatus, Boolean isActive, Integer numberOfUses) {
        this.packageName = packageName;
        this.packageCode = packageCode;
        this.price = price;
        this.description = description;
        this.packageDuration = packageDuration;
        this.advisor = new Advisor(advisorID);
        this.isActive = isActive;
        this.packageStatus = packageStatus;
        this.numberOfUses = numberOfUses;
    }
}
