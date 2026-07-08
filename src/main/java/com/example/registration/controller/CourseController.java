package com.example.registration.controller;

import com.example.registration.model.Course;
import com.example.registration.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> getCourses(@RequestParam(required = false) String search) {
        return courseService.searchCourses(search);
    }
}