package com.example.registration.controller;

import com.example.registration.model.RegistrationDetail;
import com.example.registration.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/registration")
@CrossOrigin(origins = "*")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/{studentId}")
    public ResponseEntity<RegistrationDetail> getRegistration(@PathVariable String studentId) {
        return ResponseEntity.ok(registrationService.getRegistrationByStudent(studentId));
    }

    @PostMapping("/{studentId}/register")
    public ResponseEntity<?> registerCourse(@PathVariable String studentId, @RequestBody Map<String, String> payload) {
        String courseId = payload.get("courseId");
        try {
            registrationService.registerCourse(studentId, courseId);
            return ResponseEntity.ok(Map.of("message", "Đăng ký môn học thành công!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{studentId}/cancel/{courseId}")
    public ResponseEntity<?> cancelCourse(@PathVariable String studentId, @PathVariable String courseId) {
        try {
            registrationService.cancelCourse(studentId, courseId);
            return ResponseEntity.ok(Map.of("message", "Hủy đăng ký môn học thành công!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}