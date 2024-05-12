/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author Duc Huy
 */
public interface VNPayService {

    public String makePayment(int total, String orderInfor, String urlReturn);

    public  int orderReturn(HttpServletRequest request);
}
