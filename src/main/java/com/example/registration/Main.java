package com.example.registration;

import com.example.registration.repository.*;
import com.example.registration.service.*;
import com.example.registration.exception.*;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static RegistrationService registrationService;
    private static CourseService courseService;

    public static void main(String[] args) {
        // 1. Khởi tạo dữ liệu và Service
        initializeSystem();

        // 2. Vòng lặp menu chính
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> courseService.displayCourses();
                case "2" -> handleRegister();
                case "3" -> handleCancel();
                case "4" -> handleViewRegistration();
                case "0" -> running = false;
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
        System.out.println("Đã thoát chương trình.");
    }

    private static void initializeSystem() {
        // Khởi tạo Repository và Service
        CourseRepository courseRepo = new CourseRepository();
        StudentRepository studentRepo = new StudentRepository();
        courseService = new CourseService(courseRepo);
        registrationService = new RegistrationService(courseRepo, studentRepo);
    }

    private static void handleRegister() {
        try {
            System.out.print("Nhập mã sinh viên: ");
            String studentId = scanner.nextLine();
            System.out.print("Nhập mã môn học: ");
            String courseId = scanner.nextLine();

            registrationService.registerCourse(studentId, courseId);
            System.out.println("Đăng ký thành công!");
        } catch (CourseNotFoundException | CreditLimitException | DuplicateRegistrationException e) {
            System.err.println("Lỗi đăng ký: " + e.getMessage());
        }
    }

    private static void handleCancel() {
        try {
            System.out.print("Nhập mã sinh viên: ");
            String studentId = scanner.nextLine();
            System.out.print("Nhập mã môn học muốn hủy: ");
            String courseId = scanner.nextLine();

            registrationService.cancelCourse(studentId, courseId);
            System.out.println("Hủy đăng ký thành công!");
        } catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }

    private static void handleViewRegistration() {
        System.out.print("Nhập mã sinh viên: ");
        String studentId = scanner.nextLine();
        registrationService.displayRegistration(studentId);
    }

    private static void printMenu() {
        System.out.println("\n--- HỆ THỐNG ĐĂNG KÝ MÔN HỌC ---");
        System.out.println("1. Xem danh sách môn học");
        System.out.println("2. Đăng ký môn học");
        System.out.println("3. Hủy đăng ký môn học");
        System.out.println("4. Xem danh sách đã đăng ký & Tổng tín chỉ");
        System.out.println("0. Thoát");
        System.out.print("Chọn: ");
    }
}


// ok