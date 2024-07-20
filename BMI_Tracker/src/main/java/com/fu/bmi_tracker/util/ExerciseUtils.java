/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.util;

/**
 *
 * @author Duc Huy
 */
public class ExerciseUtils {

    /**
     * Tính toán lượng calories burned dựa trên giá trị METs, weight, duration
     *
     * @param mets Metabolic Equivalent of Task
     * @param weight Trọng lượng cơ thể (kg)
     * @param duration Thời gian tập luyện (phút)
     * @return Tổng lượng calo đốt cháy
     */
    public static int calculateCalories(double mets, double weight, int duration) {
        // link công thức
        //https://www.healthline.com/health/what-are-mets#bottom-line

        // Tính toán lượng calo đốt cháy mỗi phút
        double caloriesPerMinute = (mets * 3.5 * weight / 200);

        // Nhân với thời gian để có tổng lượng calo đốt cháy
        double totalCalories = caloriesPerMinute * duration;

        // Làm tròn kết quả và chuyển đổi sang int
        return (int) Math.round(totalCalories);
    }
}
