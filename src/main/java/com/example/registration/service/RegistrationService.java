package com.example.registration.service;

import com.example.registration.exception.CourseNotFoundException;
import com.example.registration.exception.CreditLimitException;
import com.example.registration.exception.DuplicateRegistrationException;
import com.example.registration.model.*;
import com.example.registration.repository.CourseRepository; // Bo sung import
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

    @Autowired
    private CourseRepository courseRepository; // Tiem them CourseRepository de ghi file

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

        // Lay tat ca mon hoc tu file ra de thao tac truc tiep tren danh sach ghi lai
        List<Course> allCourses = courseRepository.findAll();

        Course course = allCourses.stream()
                .filter(c -> c.getCourseId().equals(courseId))
                .findFirst()
                .orElse(null);

        if (course == null) {
            throw new CourseNotFoundException("Khong tim thay mon hoc voi ma: " + courseId);
        }

        RegistrationDetail detail = getRegistrationByStudent(studentId);
        Student student = detail.getStudent();

        // 1. Kiem tra trung mon
        boolean isRegistered = detail.getDetails().stream()
                .anyMatch(r -> r.getCourse().getCourseId().equals(courseId));
        if (isRegistered) {
            throw new DuplicateRegistrationException("Ban da dang ky mon hoc nay roi!");
        }

        // 2. Chay cac bo validate (Capacity, Credit, Schedule)
        for (CourseValidator validator : validators) {
            validator.validate(detail, course);
        }

        // 3. Dang ky thanh cong
        Registration registrationRow = new Registration(course);
        detail.addDetail(registrationRow);
        student.addCourse(course);

        course.increaseEnrolled(); // Tang si so trong bo nho

        // QUAN TRONG: Ghi de toan bo danh sach da cap nhat si so moi xuong file txt
        courseRepository.saveAll(allCourses);
    }

    public void cancelCourse(String studentId, String courseId) throws CourseNotFoundException {
        RegistrationDetail detail = detailMap.get(studentId);
        if (detail == null) {
            throw new CourseNotFoundException("Khong tim thay du lieu dang ky cua sinh vien.");
        }

        List<Course> allCourses = courseRepository.findAll();
        Course targetCourse = allCourses.stream()
                .filter(c -> c.getCourseId().equals(courseId))
                .findFirst()
                .orElse(null);

        boolean removed = detail.getDetails().removeIf(r -> {
            if (r.getCourse().getCourseId().equals(courseId)) {
                detail.getStudent().removeCourse(r.getCourse());
                if (targetCourse != null) {
                    targetCourse.decreaseEnrolled(); // Giam si so trong bo nho
                }
                return true;
            }
            return false;
        });

        if (!removed) {
            throw new CourseNotFoundException("Sinh vien chua dang ky mon hoc nay.");
        }

        // QUAN TRONG: Cap nhat lai file txt sau khi huy mon thanh cong
        courseRepository.saveAll(allCourses);
    }
}