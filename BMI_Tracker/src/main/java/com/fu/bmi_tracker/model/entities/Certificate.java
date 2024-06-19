/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.payload.request.CreateCertificateRequest;
import com.fu.bmi_tracker.payload.request.UpdateCertificateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
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
@Table(name = "Certificate")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CertificateID", nullable = false)
    private Integer certificateID;

    @Column(name = "CertificateName", nullable = false)
    private String certificateName;

    @Column(name = "CertificateLink", nullable = false)
    private String certificateLink;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "AdvisorID", nullable = false)
    private Advisor advisor;

    public Certificate(CreateCertificateRequest certificateRequest) {
        this.certificateName = certificateRequest.getCertificateName();
        this.certificateLink = certificateRequest.getCertificateLink();
        this.isActive = true;
        this.advisor = new Advisor(certificateRequest.getAdvisorID());
    }

    public void updateCertificate(UpdateCertificateRequest certificateRequest) {
        this.certificateName = certificateRequest.getCertificateName();
        this.certificateLink = certificateRequest.getCertificateLink();
        this.isActive = certificateRequest.getIsActive();
    }
}
