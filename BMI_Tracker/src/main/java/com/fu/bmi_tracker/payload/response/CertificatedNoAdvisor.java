package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Certificate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Duc Huy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificatedNoAdvisor {

    private Integer certificateID;
    private String certificateName;
    private String certificateLink;
    private Boolean isActive;

    public CertificatedNoAdvisor(Certificate certificate) {
        this.certificateID = certificate.getCertificateID();
        this.certificateName = certificate.getCertificateName();
        this.certificateLink = certificate.getCertificateLink();
        this.isActive = certificate.getIsActive();
    }

}
