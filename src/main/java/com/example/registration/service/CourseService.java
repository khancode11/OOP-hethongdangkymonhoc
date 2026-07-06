package com.example.registration.service;

import com.example.registration.model.Course;
import com.example.registration.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public CourseService() {
        this(new CourseRepository());
    }

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> searchCourses(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllCourses();
        }
        return courseRepository.findAll().stream()
                .filter(c -> c.getCourseName().toLowerCase().contains(keyword.toLowerCase())
                        || c.getCourseId().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Course getCourseById(String courseId) {
        return courseRepository.findAll().stream()
                .filter(c -> c.getCourseId().equals(courseId))
                .findFirst()
                .orElse(null);
    }

    public void displayCourses() {
        List<Course> courses = getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("Khong co mon hoc nao trong he thong.");
            return;
        }

        courses.forEach(System.out::println);
    }
}
