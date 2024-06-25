/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.fu.bmi_tracker.model.enums;

/**
 *
 * @author Duc Huy
 */
public enum EBookingStatus {
    PENDING, // Booking đang còn trong thời gian hiệu lực
    NOT_STARTED, // Booking chưa bắt đầu đang trong thời gian đặt trước
    FINISHED, // Booking đã hoàn thành
    CANCELLED, // Đơn hàng đã bị hủy
    MEMBER_PAID; // khách hàng đã thanh toán

    @Override
    public String toString() {
        switch (this) {
            case PENDING -> {
                return "Pending";
            }
            case NOT_STARTED -> {
                return "Not Started";
            }
            case FINISHED -> {
                return "Finished";
            }
            case CANCELLED -> {
                return "Cancelled";
            }
            case MEMBER_PAID -> {
                return "Member Paid";
            }
            default ->
                throw new IllegalArgumentException();
        }
    }
}