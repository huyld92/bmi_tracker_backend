/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Package;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Duc Huy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageResponse {

    private Integer packageID;
    private String packageName;
    private String packageCode;
    private BigDecimal price;
    private String description;
    private Integer packageDuration;
    private Integer advisorID;
    private Integer numberOfUses;

    public PackageResponse(Package entityPackage) {
        this.packageID = entityPackage.getPackageID();
        this.packageName = entityPackage.getPackageName();
        this.packageCode = entityPackage.getPackageCode();
        this.price = entityPackage.getPrice();
        this.description = entityPackage.getDescription();
        this.packageDuration = entityPackage.getPackageDuration();
        this.advisorID = entityPackage.getAdvisor().getAdvisorID();
        this.numberOfUses = entityPackage.getNumberOfUses();
    }

}
