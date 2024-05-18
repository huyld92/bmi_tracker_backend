package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Certificate;
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
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@Tag(name = "Certificate", description = "Certificate level management APIs")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    @Autowired
    CertificateService service;

    @Operation(
            summary = "Create new certificate",
            description = "Create new certificate with form",
            tags = {"ADMIN", "TRAINER"})
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Certificate.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
//    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    public ResponseEntity<?> createNewCertificate(@Valid @RequestBody CreateCertificateRequest certificateRequest) {
        // Create new object
        Certificate certificate = new Certificate(certificateRequest);

        // Store to database
        Certificate certificateSave = service.save(certificate);

        // check result
        if (certificateSave == null) {
            return new ResponseEntity<>("Failed to create new certificate", HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(certificateSave, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update certificate by certificate id",
            description = "Update certificate name and link",
            tags = {"ADMIN", "TRAINER"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Certificate.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping(value = "/update")
//    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    public ResponseEntity<?> updateCertificate(@Valid @RequestBody UpdateCertificateRequest certificateRequest) {
        // Check exist certificate
        Optional<Certificate> certificate = service.findById(certificateRequest.getCertificateID());

        if (!certificate.isPresent()) {
            return new ResponseEntity<>("Cannot find certificate with id{" + certificateRequest.getCertificateID() + "}", HttpStatus.NOT_FOUND);
        }
        // update certificate attribute
        certificate.get().updateCertificate(certificateRequest);

        // Store to database
        Certificate certificateSave = service.save(certificate.get());

        // check resutl
        if (certificateSave == null) {
            return new ResponseEntity<>("Failed to update certificate", HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(certificateSave, HttpStatus.OK);
    }

    @Operation(
            summary = "Update status certificate by certificate id",
            description = "Update certificate status",
            tags = {"ADMIN"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Certificate.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping(value = "/updateStatus")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateStatusCertificate(@RequestParam Integer certificateID, @RequestParam String status) {
        // Check exist certificate
        Optional<Certificate> certificate = service.findById(certificateID);

        if (!certificate.isPresent()) {
            return new ResponseEntity<>("Cannot find certificate with id{" + certificateID + "}", HttpStatus.NOT_FOUND);
        }
        // update status
        certificate.get().setStatus(status);

        // Store to database
        Certificate certificateSave = service.save(certificate.get());

        // check resutl
        if (certificateSave == null) {
            return new ResponseEntity<>("Failed to update certificate", HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(certificateSave, HttpStatus.OK);
    }

    @Operation(summary = "Retrieve all Certificates (ADMIN)", tags = {"ADMIN"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Certificate.class))}),
        @ApiResponse(responseCode = "204", description = "There are no Certificates", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getAll")
//    @PreAuthorize("hasRole('ADMIN') ")
    public ResponseEntity<?> getAllCertificates() {

        Iterable certificates = service.findAll();

        if (!certificates.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(certificates, HttpStatus.OK);

    }

    @Operation(
            summary = "Retrieve a Certificate by Id",
            description = "Get a Certificate object by specifying its id. The response is Certificate object",
            tags = {"ADMIN", "TRAINER"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Certificate.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getByID")
    //    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    public ResponseEntity<?> getCertificateById(@RequestParam("id") int id) {
        Optional<Certificate> certificate = service.findById(id);

        if (certificate.isPresent()) {
            return new ResponseEntity<>(certificate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find certificate with id{" + id + "}", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Retrieve a Certificate by trainer ID",
            description = "Get a Certificate object by specifying trainer id. The response is Certificate object",
            tags = {"ADMIN", "TRAINER"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Certificate.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getAllByID")
    //    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    public ResponseEntity<?> getAllCertificateByTrainerId(@RequestParam int trainerID) {

        Iterable certificates = service.findAllByTrainerTrainerID(trainerID);

        if (!certificates.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete certificate ",
            description = "Delete certificate by Id",
            tags = {"ADMIN", "TRAINER"})
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/delete")
    //    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    public ResponseEntity<?> deleteCertificateById(@RequestParam int certificateID) {
        service.deleteById(certificateID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
