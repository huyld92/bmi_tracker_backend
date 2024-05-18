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
    @Column(name = "CertificateID")
    private int certificateID;

    @Column(name = "CertificateName")
    private String certificateName;

    @Column(name = "CertificateLink")
    private String certificateLink;

    @Column(name = "Status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "TrainerID")
    private Trainer trainer;

    public Certificate(CreateCertificateRequest certificateRequest) {
        this.certificateName = certificateRequest.getCertificateName();
        this.certificateLink = certificateRequest.getCertificateLink();
        this.status = "Not verified";
        this.trainer = new Trainer(certificateRequest.getTrainerID());

    }

    public void updateCertificate(UpdateCertificateRequest certificateRequest) {
        this.certificateName = certificateRequest.getCertificateName();
        this.certificateLink = certificateRequest.getCertificateLink();
    }
}
