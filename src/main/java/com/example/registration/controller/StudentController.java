package com.example.registration.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @GetMapping
    public String getAllStudentsRaw() {
        try {
            File file = new ClassPathResource("students.txt").getFile();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                return br.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            return "Loi: Khong the doc file students.txt";
        }
    }
}