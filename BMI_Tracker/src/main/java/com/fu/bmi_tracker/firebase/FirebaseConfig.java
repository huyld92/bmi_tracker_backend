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

/**
 *
 * @author Bao
 */
@Configuration
public class FirebaseConfig {
    
    private final String FIREBASE_KEY_FILE = "test-ultilites-firebase-adminsdk-5p57u-901551d732.json";
    
    @Bean
    public FirebaseApp initializeFirebaseApp() throws IOException {
        /*
        System.out.println("--------------------");
        System.out.println(getClass().getClassLoader().getResource(FIREBASE_KEY_FILE).getPath());
        System.out.println("--------------------");
        */
        InputStream serviceAccount
                = getClass().getClassLoader().getResourceAsStream(FIREBASE_KEY_FILE);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
