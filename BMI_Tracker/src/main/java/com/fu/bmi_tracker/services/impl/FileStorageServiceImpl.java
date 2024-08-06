///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.fu.bmi_tracker.services.impl;
//
//import com.fu.bmi_tracker.services.FileStorageService;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//@Service
//public class FileStorageServiceImpl implements FileStorageService {
//
//    private final Path uploadDir = Paths.get("uploads");
//
//    @Override
//    public String store(MultipartFile file, String name) throws IOException {
//
//        if (!Files.exists(uploadDir)) {
//            Files.createDirectories(uploadDir);
//        }
//
//        Path filePath = uploadDir.resolve(name + "_" + file.getOriginalFilename());
//        Files.copy(file.getInputStream(), filePath);
//
//        return filePath.toString();
//    }
//
//}
