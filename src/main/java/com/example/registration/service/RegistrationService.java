package com.example.registration.service;

import com.example.registration.exception.CourseNotFoundException;
import com.example.registration.exception.CreditLimitException;
import com.example.registration.exception.DuplicateRegistrationException;
import com.example.registration.model.*;
import com.example.registration.repository.StudentRepository;
import com.example.registration.validator.CourseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegistrationService {
    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentRepository studentRepository;

    // Tự động quét và nạp toàn bộ các lớp hiện thực CourseValidator (Capacity, Credit, Schedule)
    @Autowired
    private List<CourseValidator> validators;

    private final Map<String, RegistrationDetail> detailMap = new HashMap<>();

    public RegistrationDetail getRegistrationByStudent(String studentId) {
        return detailMap.computeIfAbsent(studentId, id -> {
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
            throw new CourseNotFoundException("Khong tim thay mon hoc voi ma: " + courseId);
        }

        RegistrationDetail detail = getRegistrationByStudent(studentId);
        Student student = detail.getStudent();

        // 1. Kiểm tra trùng môn (Giữ nguyên)
        boolean isRegistered = detail.getDetails().stream()
                .anyMatch(r -> r.getCourse().getCourseId().equals(courseId));
        if (isRegistered) {
            throw new DuplicateRegistrationException("Ban ya dang ky mon hoc nay roi!");
        }

        // 2. Chạy qua toàn bộ các bộ Validator mẫu (Ăn điểm Polymorphism tuyệt đối)
        for (CourseValidator validator : validators) {
            validator.validate(detail, course);
        }

        // 3. Đăng ký thành công
        Registration registrationRow = new Registration(course);
        detail.addDetail(registrationRow);
        student.addCourse(course);
        course.increaseEnrolled();
    }

    public void cancelCourse(String studentId, String courseId) throws CourseNotFoundException {
        RegistrationDetail detail = detailMap.get(studentId);
        if (detail == null) {
            throw new CourseNotFoundException("Khong tim thay du lieu dang ky cua sinh vien.");
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
            throw new CourseNotFoundException("Sinh vien chua dang ky mon hoc nay.");
        }
    }
}