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
public class PackageAdvisorResponse {

    private Integer packageID;
    private String packageCode;
    private String packageName;
    private BigDecimal price;
    private String description;
    private Integer packageDuration;
    private Integer advisorID;
    private String fullName;
    private Integer numberOfUses;
    private String packageStatus;
    private Boolean isActive;

    public PackageAdvisorResponse(Package p) {
        this.packageID = p.getPackageID();
        this.packageCode = p.getPackageCode();
        this.packageName = p.getPackageName();
        this.price = p.getPrice();
        this.description = p.getDescription();
        this.packageDuration = p.getPackageDuration();
        this.advisorID = p.getAdvisor().getAdvisorID();
        this.fullName = p.getAdvisor().getAccount().getFullName();
        this.numberOfUses = p.getNumberOfUses();
        this.packageStatus = p.getPackageStatus();
        this.isActive = p.getIsActive();
    }

}
