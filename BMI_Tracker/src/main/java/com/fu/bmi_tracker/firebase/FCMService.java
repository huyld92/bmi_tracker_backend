/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.firebase;

import com.fu.bmi_tracker.payload.request.PnsRequest;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Duc Huy
 */
@Service
public class FCMService {

    public String pushNotification(PnsRequest pnsRequest) {
        Message message = Message.builder()
                .putData("content", pnsRequest.getContent())
                .setToken(pnsRequest.getFcmToken())
                .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
        return response;
    }

    public String uploadImages(File imageFile, String fileName) throws FileNotFoundException {
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.create(fileName, new FileInputStream(imageFile), "image/jpg");
        return blob.getMediaLink();
    }
}
