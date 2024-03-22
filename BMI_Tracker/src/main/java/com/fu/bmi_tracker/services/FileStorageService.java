/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Duc Huy
 */
public interface FileStorageService {

    public String store(MultipartFile file, String name) throws IOException;
}
