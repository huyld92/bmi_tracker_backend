/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author Bao
 */
@Configuration
public class FirebaseConfig {
    
    @Value("${firebase.keyFile}")
    private String firebaseKeyFile;
    
    @Bean
    public FirebaseApp initializeFirebaseApp() throws IOException {
        
        InputStream serviceAccount
                = getClass().getClassLoader().getResourceAsStream(firebaseKeyFile);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
