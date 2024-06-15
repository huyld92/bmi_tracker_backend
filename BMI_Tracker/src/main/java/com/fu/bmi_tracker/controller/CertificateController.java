package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Certificate;
import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.payload.request.CreateCertificateRequest;
import com.fu.bmi_tracker.payload.request.UpdateCertificateRequest;
import com.fu.bmi_tracker.services.CertificateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Duc Huy
 */
@Tag(name = "Certificate", description = "Certificate management APIs")
@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    @Autowired
    CertificateService certificateService;

    @Operation(
            summary = "Create new certificate",
            description = "Create new certificate with form")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Certificate.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ADVISOR')")
    public ResponseEntity<?> createNewCertificate(@Valid @RequestBody CreateCertificateRequest certificateRequest) {
        // Create new object
        Certificate certificate = new Certificate(certificateRequest);

        // Store to database
        Certificate certificateSave = certificateService.save(certificate);

        // check result
        if (certificateSave == null) {
            return new ResponseEntity<>("Failed to create new certificate", HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(certificateSave, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update certificate by certificate id",
            description = "Update certificate name and link")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Certificate.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping(value = "/update")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ADVISOR')")
    public ResponseEntity<?> updateCertificate(@Valid @RequestBody UpdateCertificateRequest certificateRequest) {
        // Check exist certificate
        Optional<Certificate> certificate = certificateService.findById(certificateRequest.getCertificateID());

        if (!certificate.isPresent()) {
            return new ResponseEntity<>("Cannot find certificate with id{" + certificateRequest.getCertificateID() + "}", HttpStatus.NOT_FOUND);
        }
        // update certificate attribute
        certificate.get().updateCertificate(certificateRequest);

        // Store to database
        Certificate certificateSave = certificateService.save(certificate.get());

        // check resutl
        if (certificateSave == null) {
            return new ResponseEntity<>("Failed to update certificate", HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(certificateSave, HttpStatus.OK);
    }

    @Operation(summary = "Retrieve all Certificates (ADMIN)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Certificate.class))}),
        @ApiResponse(responseCode = "204", description = "There are no Certificates", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN') ")
    public ResponseEntity<?> getAllCertificates() {

        Iterable<Certificate> certificates = certificateService.findAll();

        if (!certificates.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(certificates, HttpStatus.OK);

    }

    @Operation(summary = "Retrieve all Certificates personally (ADVISOR)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Certificate.class))}),
        @ApiResponse(responseCode = "204", description = "There are no Certificates", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/advisor/getAll")
    @PreAuthorize("hasRole('ADVISOR') ")
    public ResponseEntity<?> getAllOfAdvisor() {
        // Tìm account id từ context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // gọi service tìm tất cả certificate của advisor
        Iterable<Certificate> certificates = certificateService.findAllOfAdvisor(principal.getId());

        if (!certificates.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(certificates, HttpStatus.OK);

    }

    @Operation(
            summary = "Retrieve a Certificate by Id",
            description = "Get a Certificate object by specifying its id. The response is Certificate object")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Certificate.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getByID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ADVISOR')")
    public ResponseEntity<?> getCertificateById(@RequestParam("id") int id) {
        Optional<Certificate> certificate = certificateService.findById(id);

        if (certificate.isPresent()) {
            return new ResponseEntity<>(certificate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find certificate with id{" + id + "}", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Retrieve a Certificate by advisor ID",
            description = "Get a Certificate object by specifying advisor id. The response is Certificate object")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Certificate.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getAllByID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ADVISOR')")
    public ResponseEntity<?> getAllCertificateByAdvisorID(@RequestParam int advisorID) {

        Iterable<Certificate> certificates = certificateService.findAllByAdvisorAdvisorID(advisorID);

        if (!certificates.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete certificate ",
            description = "Delete certificate by Id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ADVISOR')")
    public ResponseEntity<?> deleteCertificateById(@RequestParam int certificateID) {
        certificateService.deleteById(certificateID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
