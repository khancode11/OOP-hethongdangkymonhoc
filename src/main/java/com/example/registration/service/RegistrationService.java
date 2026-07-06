package com.example.registration.service;

import com.example.registration.exception.CourseNotFoundException;
import com.example.registration.exception.CreditLimitException;
import com.example.registration.exception.DuplicateRegistrationException;
import com.example.registration.model.*;
import com.example.registration.repository.CourseRepository;
import com.example.registration.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegistrationService {
    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentRepository studentRepository;

    // Lưu trữ phiếu đăng ký tổng của các sinh viên (Key: studentId)
    private final Map<String, RegistrationDetail> detailMap = new HashMap<>();

    public RegistrationService() {
        this(new CourseRepository(), new StudentRepository());
    }

    public RegistrationService(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseService = new CourseService(courseRepository);
        this.studentRepository = studentRepository;
    }

    public RegistrationDetail getRegistrationByStudent(String studentId) {
        return detailMap.computeIfAbsent(studentId, id -> {
            // Tìm sinh viên trong file, nếu không thấy tự tạo giả lập tránh sập hệ thống
            Student student = studentRepository.findAll().stream()
                    .filter(s -> s.getId().equals(id))
                    .findFirst()
                    .orElse(new Student(id, "Sinh vien " + id, "Lop_Default", "CNTT", 24));
            return new RegistrationDetail(student);
        });
    }

    public void registerCourse(String studentId, String courseId)
            throws CourseNotFoundException, DuplicateRegistrationException, CreditLimitException {

        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            throw new CourseNotFoundException("Không tìm thấy môn học với mã: " + courseId);
        }

        if (!course.hasAvailableSlot()) {
            throw new CourseNotFoundException("Mon hoc da het cho: " + course.getCourseName());
        }

        RegistrationDetail detail = getRegistrationByStudent(studentId);
        Student student = detail.getStudent();

        // 1. Kiểm tra môn học đã đăng ký chưa (duyệt qua danh sách các dòng Registration)
        boolean isRegistered = detail.getDetails().stream()
                .anyMatch(r -> r.getCourse().getCourseId().equals(courseId));
        if (isRegistered) {
            throw new DuplicateRegistrationException("Bạn đã đăng ký môn học này rồi!");
        }

        // 2. Kiểm tra giới hạn tín chỉ dựa trên maxCredits của Student gốc của bạn
        int currentCredits = detail.getDetails().stream()
                .mapToInt(r -> r.getCourse().getCredits())
                .sum();
        if (currentCredits + course.getCredits() > student.getMaxCredits()) {
            throw new CreditLimitException("Vượt quá giới hạn tín chỉ tối đa của sinh viên (" + student.getMaxCredits() + " tín chỉ)!");
        }

        // 3. Kiểm tra trùng lịch học (gọi hàm isConflict trong Schedule gốc của bạn)
        if (course.getSchedule() != null) {
            for (Registration r : detail.getDetails()) {
                if (r.getCourse().getSchedule() != null &&
                        course.getSchedule().isConflict(r.getCourse().getSchedule())) {
                    throw new CreditLimitException("Trùng lịch học với môn: " + r.getCourse().getCourseName());
                }
            }
        }

        // 4. Thêm môn học thành công
        Registration registrationRow = new Registration(course);
        detail.addDetail(registrationRow);
        student.addCourse(course); // Đồng bộ vào danh sách registeredCourses của lớp Student gốc
        course.increaseEnrolled(); // Tăng sĩ số môn học
    }

    public void cancelCourse(String studentId, String courseId) throws CourseNotFoundException {
        RegistrationDetail detail = detailMap.get(studentId);
        if (detail == null) {
            throw new CourseNotFoundException("Không tìm thấy dữ liệu đăng ký của sinh viên này.");
        }

        boolean removed = detail.getDetails().removeIf(r -> {
            if (r.getCourse().getCourseId().equals(courseId)) {
                detail.getStudent().removeCourse(r.getCourse());
                r.getCourse().decreaseEnrolled();
                return true;
            }
            return false;
        });

        if (!removed) {
            throw new CourseNotFoundException("Sinh viên chưa đăng ký môn học này.");
        }
    }

    public void displayRegistration(String studentId) {
        RegistrationDetail detail = detailMap.get(studentId);
        if (detail == null || detail.getDetails().isEmpty()) {
            System.out.println("Sinh vien chua dang ky mon hoc nao.");
            return;
        }

        System.out.println(detail);
        int totalCredits = detail.getDetails().stream()
                .mapToInt(r -> r.getCourse().getCredits())
                .sum();
        System.out.println("Tong tin chi: " + totalCredits + "/" + detail.getStudent().getMaxCredits());
    }
}
