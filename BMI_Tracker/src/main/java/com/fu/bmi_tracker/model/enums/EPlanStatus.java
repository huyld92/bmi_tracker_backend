/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.fu.bmi_tracker.model.enums;

/**
 *
 * @author Duc Huy
 */
public enum EPlanStatus {
    PENDING, // Kế hoạch đang chờ duyệt
    APPROVED, // Kế hoạch đã được phê duyệt
    REJECTED;// Kế hoạch bị từ chối
//
//    @Override
//    public String toString() {
//        switch (this) {
//            case PENDING -> {
//                return "Pending";
//            }
//            case APPROVED -> {
//                return "Approved";
//            }
//            case REJECTED -> {
//                return "Rejected";
//            }
//            default ->
//                throw new IllegalArgumentException();
//        }
//    }
 
}
