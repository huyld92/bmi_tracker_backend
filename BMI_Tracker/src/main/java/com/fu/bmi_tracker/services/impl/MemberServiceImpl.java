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
import com.fu.bmi_tracker.model.entities.MenuHistory;
import com.fu.bmi_tracker.model.entities.WorkoutHistory;
import com.fu.bmi_tracker.model.enums.EMealType;
import com.fu.bmi_tracker.payload.response.MemberBmiResponse;
import com.fu.bmi_tracker.repository.DailyRecordRepository;
import com.fu.bmi_tracker.repository.ExerciseRepository;
import com.fu.bmi_tracker.repository.FoodRepository;
import com.fu.bmi_tracker.repository.MemberBodyMassRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fu.bmi_tracker.repository.MemberRepository;
import com.fu.bmi_tracker.repository.MenuFoodRepository;
import com.fu.bmi_tracker.repository.MenuHistoryRepository;
import com.fu.bmi_tracker.repository.MenuRepository;
import com.fu.bmi_tracker.repository.WorkoutExerciseRepository;
import com.fu.bmi_tracker.repository.WorkoutHistoryRepository;
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
    WorkoutHistoryRepository workoutHistoryRepository;

    @Autowired
    MenuHistoryRepository menuHistoryRepository;

    @Autowired
    MenuFoodRepository menuFoodRepository;

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
        return memberRepository.existsByAccount_AccountID(accountID);
    }

    @Override
    public Optional<Member> findByAccountID(int accountID) {
        return memberRepository.findByAccount_AccountID(accountID);
    }

    @Override
    public List<Exercise> getllExerciseResponseInWorkout(Integer accountID) {
        // Find member by accountID
        Member member = this.findByAccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find member!"));

        // gọi workout history tìm tìm tất cả workout hiện tịa của member
        // *chưa kiểm soát việc trả về có nhiều hơn 1 result
        Optional<WorkoutHistory> workoutHistory = workoutHistoryRepository.findByMemberIDAndIsActiveTrue(member.getMemberID());

        // nếu không tìm thấy kết quả return mảng rỗng
        if (!workoutHistory.isPresent()) {
            return new ArrayList<>();
        }

        // gọi WorkoutExerciseRepository tìm List exercise
        return workoutExerciseRepository.findExercisesByWorkoutID(workoutHistory.get().getWorkoutID());
    }

    @Override
    public Page<Food> getPaginatedFoodWithPriority(Integer accountID, Pageable pageable) {
        // Tìm member từ accountID
        Member member = memberRepository.findByAccount_AccountID(accountID)
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
        double bmi = bMIUtils.calculateBMI(memberBodyMass.getWeight(), memberBodyMass.getHeight());
        String classifyBMI = bMIUtils.classifyBMI(bmi);

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
        Member member = memberRepository.findByAccount_AccountID(accountID)
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

    @Override
    public List<MemberBmiResponse> getMemberBMISummary() {
        // list MemberBMIresponse 
        List<MemberBmiResponse> memberBmiResponses = new ArrayList<>();

        // tìm danh sách member đang hoạt dộng
        List<Member> members = memberRepository.findAll();

        if (members.isEmpty()) {
            return memberBmiResponses;
        }

        // add value vào memberBmiResponses
        members.forEach(member -> {
            MemberBodyMass bodyMass = bodyMassRepository.findLatestByAccountID(member.getAccount().getAccountID()).get();

            double bmi = bMIUtils.calculateBMI(bodyMass.getWeight(), bodyMass.getHeight());

            memberBmiResponses.add(new MemberBmiResponse(
                    member.getMemberID(),
                    bodyMass.getDateInput().toLocalDate(),
                    bmi));
        });
        return memberBmiResponses;

    }

    @Override
    public List<Food> getFoodsInMenuByMealType(Integer accountID, EMealType mealType) {
        // Find member by accountID
        Member member = this.findByAccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find member!"));

        // gọi workout history tìm tìm tất cả workout hiện tịa của member
        // *chưa kiểm soát việc trả về có nhiều hơn 1 result
        Optional<MenuHistory> menuHistory = menuHistoryRepository.findByMember_MemberIDAndIsActiveTrue(member.getMemberID());

        // nếu không tìm thấy kết quả return mảng rỗng
        if (!menuHistory.isPresent()) {
            return new ArrayList<>();
        }

        // gọi menuFoodRepository tìm List food
        return menuFoodRepository.findFoodByMenu_MenuIDAndMealType(menuHistory.get().getMenu().getMenuID(), mealType);
    }

    @Override
    public List<Food> getAllFoodInMenu(Integer accountID) {
        // Find member by accountID
        Member member = this.findByAccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find member!"));

        // gọi workout history tìm tìm tất cả workout hiện tịa của member
        // *chưa kiểm soát việc trả về có nhiều hơn 1 result
        Optional<MenuHistory> menuHistory = menuHistoryRepository.findByMember_MemberIDAndIsActiveTrue(member.getMemberID());

        // nếu không tìm thấy kết quả return mảng rỗng
        if (!menuHistory.isPresent()) {
            return new ArrayList<>();
        }

        // gọi menuFoodRepository tìm List food
        return menuFoodRepository.findFoodByMenu_MenuID(menuHistory.get().getMenu().getMenuID());
    }

    @Override
    public Page<Food> getPaginatedFoodFilterTag(Pageable pageable, List<Integer> tagIDs) {
        // gọi food reposiotry tìm food theo Tagid
        Page<Food> foods = foodRepository.findAllByTagIDs(tagIDs, tagIDs.size(), pageable);

        return foods;
    }

    @Override
    public Long countTotalMember() {
        return foodRepository.count();
    }

}
