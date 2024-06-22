/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.enums;

/**
 *
 * @author Duc Huy
 */
public enum EPaymentStatus {
    PAID,
    UNPAID,
    PARTIALLY_PAID;// thanh toán một phần

    @Override
    public String toString() {
        switch (this) {
            case PAID -> {
                return "Paid";
            }
            case UNPAID -> {
                return "Unpaid";
            }
            case PARTIALLY_PAID -> {
                return "Partially Paid";
            }
            default ->
                throw new IllegalArgumentException();
        }
    }
}
