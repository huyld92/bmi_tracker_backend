/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.DailyRecord;
import com.fu.bmi_tracker.model.entities.Exercise;
import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.MemberBodyMass;
import com.fu.bmi_tracker.model.entities.Menu;
import com.fu.bmi_tracker.model.entities.Workout;
import com.fu.bmi_tracker.repository.DailyRecordRepository;
import com.fu.bmi_tracker.repository.ExerciseRepository;
import com.fu.bmi_tracker.repository.FoodRepository;
import com.fu.bmi_tracker.repository.MemberBodyMassRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fu.bmi_tracker.repository.MemberRepository;
import com.fu.bmi_tracker.repository.MenuRepository;
import com.fu.bmi_tracker.repository.WorkoutExerciseRepository;
import com.fu.bmi_tracker.repository.WorkoutRepository;
import com.fu.bmi_tracker.services.MemberService;
import com.fu.bmi_tracker.util.BMIUtils;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberBodyMassRepository bodyMassRepository;

    @Autowired
    WorkoutExerciseRepository workoutExerciseRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    DailyRecordRepository dailyRecordRepository;

    @Autowired
    BMIUtils bMIUtils;

    @Override
    public Iterable<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> findById(Integer id) {
        return memberRepository.findById(id);
    }

    @Override
    public Member save(Member t) {
        return memberRepository.save(t);
    }

    @Override
    public boolean existsByAccountID(int accountID) {
        return memberRepository.existsByAccountID(accountID);
    }

    @Override
    public Optional<Member> findByAccountID(int accountID) {
        return memberRepository.findByAccountID(accountID);
    }

    @Override
    public List<Exercise> getllExerciseResponseInWorkout(Integer accountID) {
        // Find member by accountID
        Member member = this.findByAccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find member!"));

        return workoutExerciseRepository.findExercisesByWorkoutID(member.getWorkoutID());

    }

    @Override
    public Menu getMenuSuggestion(int defaultCalories, String dietaryPreference) {
        return menuRepository.findTopByTagNameAndClosestCalories(dietaryPreference, defaultCalories);
    }

    @Override
    public Workout getWorkoutSuggestion(String classifyBMI) {
        return workoutRepository.findFirstByTagName(classifyBMI);
    }

    @Override
    public Page<Food> getPaginatedFoodWithPriority(Integer accountID, Pageable pageable) {
        // Tìm member từ accountID
        Member member = memberRepository.findByAccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find member!"));

        // Tìm danh sách Food phù hợp với DietaryPreference của Member
        List<Food> withTagList = foodRepository.findFoodWithTagName(member.getDietaryPreference());

        // Tìm danh sách Food còn lại khác vơi danh sách withTagList
        List<Food> withoutTagList = foodRepository.findFoodWithoutTagName(member.getDietaryPreference());

        // gộp 2 danh sách kết quả
        List<Food> combinedList = new ArrayList<>(withTagList);
        combinedList.addAll(withoutTagList);

        // Tạo phân trang 
        // Tính chỉ số bắt đầu cho trang của dữ liệu dựa trên đối tượng pageable
        int start = (int) pageable.getOffset();

        // Tính chỉ số kết thúc cho trang bằng cách lấy giá trị nhỏ nhất giữa tổng của start và kích thước trang,
        // và kích thước của danh sách kết hợp
        // start + pageable.getPageSize() là kích thước tại điểm bắt đầu và PageSize xem có vượt quá kích thước tổng
        // combinedList.size() là kích thước của toàn bộ danh sách
        // Math.min sẽ lấy giá trị nhỏ hơn, đảm báo end không lớn hơn kích thước tổng
        int end = Math.min((start + pageable.getPageSize()), combinedList.size());

        // Khởi tạo một danh sách mới để lưu trữ kết quả phân trang
        List<Food> paginatedList = new ArrayList<>();
        if (start < end) {
            // Trích xuất danh sách con từ danh sách kết hợp bằng cách sử dụng chỉ số bắt đầu và kết thúc
            paginatedList = combinedList.subList(start, end);
        }

        // tạo PageImpl và return kết quả 
        return new PageImpl<>(paginatedList, pageable, combinedList.size());
    }

    @Override
    public Page<Exercise> getPaginatedExerciseWithPriority(Integer accountID, Pageable pageable) {
        // Tìm member bodymass từ accountID
        MemberBodyMass memberBodyMass = bodyMassRepository.findLatestByAccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find member!"));

        // Nhận trạng thái BMI của member
        String classifyBMI = bMIUtils.classifyBMI(memberBodyMass.getBmi());

        // Tìm danh sách Exercise phù hợp với BMI của Member
        List<Exercise> withTagList = exerciseRepository.findExerciseWithTagName(classifyBMI);

        // Tìm danh sách Exercise còn lại khác vơi danh sách withTagList
        List<Exercise> withoutTagList = exerciseRepository.findExerciseWithoutTagName(classifyBMI);

        // gộp 2 danh sách kết quả
        List<Exercise> combinedList = new ArrayList<>(withTagList);
        combinedList.addAll(withoutTagList);

        // Tạo phân trang 
        // Tính chỉ số bắt đầu cho trang của dữ liệu dựa trên đối tượng pageable
        int start = (int) pageable.getOffset();

        // Tính chỉ số kết thúc cho trang bằng cách lấy giá trị nhỏ nhất giữa tổng của start và kích thước trang,
        // và kích thước của danh sách kết hợp
        // start + pageable.getPageSize() là kích thước tại điểm bắt đầu và PageSize xem có vượt quá kích thước tổng
        // combinedList.size() là kích thước của toàn bộ danh sách
        // Math.min sẽ lấy giá trị nhỏ hơn, đảm báo end không lớn hơn kích thước tổng
        int end = Math.min((start + pageable.getPageSize()), combinedList.size());

        // Khởi tạo một danh sách mới để lưu trữ kết quả phân trang
        List<Exercise> paginatedList = new ArrayList<>();
        if (start < end) {
            // Trích xuất danh sách con từ danh sách kết hợp bằng cách sử dụng chỉ số bắt đầu và kết thúc
            paginatedList = combinedList.subList(start, end);
        }

        // tạo PageImpl và return kết quả 
        return new PageImpl<>(paginatedList, pageable, combinedList.size());
    }

    @Override
    public DailyRecord getDailyRecordOfMember(Integer accountID, LocalDate localDate) {
        // gọi repository tìm member 
        Member member = memberRepository.findByAccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find member!"));

        // gọi repository tìm daily record bằng member id
        Optional<DailyRecord> dailyRecord = dailyRecordRepository.findByMember_MemberIDAndDate(member.getMemberID(), localDate);

        // kiểm tra kết  quả nếu không tồn tại thì tạo mới daily record
        if (!dailyRecord.isPresent()) {
            // gọi repository tạo mới daily record
            DailyRecord dr = new DailyRecord(0,
                    0,
                    member.getDefaultCalories(),
                    localDate,
                    member);
            return dailyRecordRepository.save(dr);

        } else {
            return dailyRecord.get();
        }

    }

}
