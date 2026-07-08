package com.example.registration.controller;

import com.example.registration.model.Student;
import com.example.registration.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        List<Student> students = studentRepository.findAll();
        students.add(student);
        studentRepository.saveAll(students);
        return ResponseEntity.ok(student);
    }
}