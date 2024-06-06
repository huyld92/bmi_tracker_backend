/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.fu.bmi_tracker.model.enums;

/**
 *
 * @author Duc Huy
 */
public enum EOrderStatus {
    PENDING,
    PAID, // Đơn hàng đã được thanh toán
    CANCELLED, // Đơn hàng đã bị hủy
    MEMBER_PAID; // khách hàng đã thanh toán

    @Override

    public String toString() {
        switch (this) {
            case PENDING -> {
                return "Pending";
            }
            case PAID -> {
                return "Paid";
            }
            case CANCELLED -> {
                return "Cancelled";
            }
            case MEMBER_PAID -> {
                return "Paid";
            }
            default ->
                throw new IllegalArgumentException();
        }
    }
}
