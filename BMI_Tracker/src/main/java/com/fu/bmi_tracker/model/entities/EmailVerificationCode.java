/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author BaoLG
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "[EmailVerificationCode]")
public class EmailVerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CodeID", nullable = false)
    private Integer codeID;

    @Column(name = "VerificationCode", nullable = false, unique = true)
    private String verificationCode;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "CreationTime", nullable = false)
    private LocalDateTime creationTime;

    public EmailVerificationCode(String verificationCode, String email) throws Exception {
        this.verificationCode = convertVerificationLink(verificationCode);
        this.email = email;
        this.creationTime = LocalDateTime.now();
    }

    private String convertVerificationLink(String verficationLink) throws Exception {
        URL url = new URL(verficationLink);
        String query = url.getQuery();
        Map<String, String> queryParams = splitQuery(query);

        if (queryParams.containsKey("oobCode")) {
            String oobCode = queryParams.get("oobCode");
            System.out.println("This is Verification Link: -------------------------------------");
            System.out.println(verficationLink);

            System.out.println("This is obbcode: -------------------------------------");
            System.out.println(oobCode);
            System.out.println(" -------------------------------------");
            return oobCode;
        } else {
            return null;
        }
    }

    private static Map<String, String> splitQuery(String query) throws Exception {
        Map<String, String> queryPairs = new HashMap<>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
            String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
            queryPairs.put(key, value);
        }
        return queryPairs;
    }
}
