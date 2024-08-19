/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.fu.bmi_tracker.model.enums;

/**
 *
 * @author Duc Huy
 */
public enum ESubscriptionStatus {
    PENDING, // Subscription đang còn trong thời gian hiệu lực
    NOT_STARTED, // Subscription chưa bắt đầu đang trong thời gian đặt trước
    FINISHED, // Subscription đã hoàn thành
    CANCELLED; // Đơn hàng đã bị hủy 

//    @Override
//    public String toString() {
//        switch (this) {
//            case PENDING -> {
//                return "Pending";
//            }
//            case NOT_STARTED -> {
//                return "Not Started";
//            }
//            case FINISHED -> {
//                return "Finished";
//            }
//            case CANCELLED -> {
//                return "Cancelled";
//            }
//            default ->
//                throw new IllegalArgumentException();
//        }
//    }
}
