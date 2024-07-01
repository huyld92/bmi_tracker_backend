/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Certificate;
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
public class CertificateResponse {

    private Integer certificateID;
    private String certificateName;
    private String certificateLink;
    private Boolean isActive;
    private Integer advisorID;
    private String accountPhoto;
    private String fullName;

    public CertificateResponse(Certificate certificate) {
        this.certificateID = certificate.getCertificateID();
        this.certificateName = certificate.getCertificateName();
        this.certificateLink = certificate.getCertificateLink();
        this.isActive = certificate.getIsActive();
        this.advisorID = certificate.getAdvisor().getAdvisorID();
        this.accountPhoto = certificate.getAdvisor().getAccount().getAccountPhoto();
        this.fullName = certificate.getAdvisor().getAccount().getFullName();
    }
}
