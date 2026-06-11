package main;

import model.Student;
import model.Course;
import model.Registration;
import model.RegistrationDetail;
import service.RegistrationService;
import service.RegistrationServiceImpl;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<Student> students = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();
    private static RegistrationService registrationService = new RegistrationServiceImpl();

    public static void main(String[] args) {
        initializeData();
        
        try (Scanner scanner = new Scanner(System.in)) {
            Student currentStudent = login(scanner);
            if (currentStudent == null) {
                return;
            }

            RegistrationDetail registrationDetail = new RegistrationDetail(currentStudent);

            while (true) {
                displayMenu(currentStudent);
                int choice = readInteger(scanner, "Lua chon: ");
                
                if (choice == 0) {
                    System.out.println("Tam biet");
                    break;
                }

                switch (choice) {
                    case 1:
                        displayStudentInfo(currentStudent);
                        break;
                    case 2:
                        displayAllCourses();
                        break;
                    case 3:
                        String keyword = readNonEmptyString(scanner, "Tu khoa: ");
                        searchCourses(keyword);
                        break;
                    case 4:
                        registerCourse(scanner, registrationDetail);
                        break;
                    case 5:
                        cancelCourse(scanner, registrationDetail);
                        break;
                    case 6:
                        displayRegisteredCourses(currentStudent);
                        break;
                    case 7:
                        System.out.println("Tong tin chi hien tai: " + currentStudent.getTotalCredits());
                        break;
                    case 8:
                        Student newStudent = login(scanner);
                        if (newStudent != null) {
                            currentStudent = newStudent;
                            registrationDetail = new RegistrationDetail(currentStudent);
                        } else {
                            System.out.println("Khong the dang nhap. Van giu tai khoan cu");
                        }
                        break;
                    default:
                        System.out.println("Lua chon khong hop le");
                }
            }
        }
    }

    private static void initializeData() {
        students.add(new Student("SV1", "Nguyen Van A", "10A1", "CNTT", 20));
        students.add(new Student("SV2", "Tran Thi B", "10A2", "CNTT", 20));
        
        courses.add(new Course("C1", "Lap Trinh Java", 3, null, 30, null));
        courses.add(new Course("C2", "Co So Du Lieu", 4, null, 25, null));
    }

    private static void displayMenu(Student sv) {
        System.out.println("\n--- He Thong Dang Ky Mon Hoc ---");
        System.out.println("Sinh vien: " + sv.getId() + " - " + sv.getFullName());
        System.out.println("1. Xem thong tin ca nhan");
        System.out.println("2. Danh sach mon hoc mo");
        System.out.println("3. Tim kiem mon hoc");
        System.out.println("4. Dang ky mon hoc");
        System.out.println("5. Huy mon hoc da dang ky");
        System.out.println("6. Xem mon hoc da dang ky");
        System.out.println("7. Xem tong so tin chi");
        System.out.println("8. Dang nhap tai khoan khac");
        System.out.println("0. Thoat");
    }

    private static Student login(Scanner sc) {
        for (int i = 0; i < 3; i++) {
            System.out.print("Nhap ma sinh vien: ");
            String id = sc.nextLine().trim();
            Student student = findStudent(id);
            if (student != null) {
                System.out.println("Dang nhap thanh cong: " + student.getFullName());
                return student;
            }
            if (i < 2) {
                System.out.println("Loi: Ma sinh vien khong ton tai (" + (2 - i) + " lan thu lai)");
            } else {
                System.out.println("Loi: Ma sinh vien khong ton tai");
            }
        }
        return null;
    }

    private static void registerCourse(Scanner sc, RegistrationDetail registrationDetail) {
        System.out.print("Nhap ma mon hoc muon dang ky: ");
        String courseId = sc.nextLine().trim();
        Course course = findCourse(courseId);
        
        if (course == null) {
            System.out.println("Loi: Khong tim thay mon hoc");
            return;
        }

        try {
            Registration registration = new Registration(course);
            registrationService.registerCourse(registrationDetail, registration);
        } catch (Exception e) {
            System.out.println("Loi dang ky: " + e.getMessage());
        }
    }

    private static void cancelCourse(Scanner sc, RegistrationDetail registrationDetail) {
        System.out.print("Nhap ma mon hoc muon huy: ");
        String courseId = sc.nextLine().trim();
        
        try {
            registrationService.cancelCourse(registrationDetail, courseId);
        } catch (Exception e) {
            System.out.println("Loi huy mon: " + e.getMessage());
        }
    }

    private static void displayStudentInfo(Student sv) {
        System.out.println("Ma SV: " + sv.getId());
        System.out.println("Ho Ten: " + sv.getFullName());
        System.out.println("Lop: " + sv.getClassName());
        System.out.println("Nganh: " + sv.getMajor());
        System.out.println("Gioi han tin chi: " + sv.getMaxCredits());
    }

    private static void displayAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("Khong co du lieu mon hoc");
            return;
        }
        System.out.printf("%-10s %-25s %-5s %-20s %-10s\n", "Ma MH", "Ten Mon Hoc", "TC", "Giang Vien", "Si So");
        for (Course course : courses) {
            System.out.printf("%-10s %-25s %-5d %-20s %-10s\n",
                course.getCourseId(), course.getCourseName(), course.getCredits(),
                "TBD", course.getEnrolledStudents() + "/" + course.getMaxStudents());
        }
    }

    private static void searchCourses(String keyword) {
        List<Course> results = new ArrayList<>();
        for (Course course : courses) {
            if (course.getCourseName().toLowerCase().contains(keyword.toLowerCase()) ||
                course.getCourseId().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(course);
            }
        }
        
        if (results.isEmpty()) {
            System.out.println("Khong tim thay mon hoc phu hop");
            return;
        }
        
        System.out.printf("%-10s %-25s %-5s %-20s %-10s\n", "Ma MH", "Ten Mon Hoc", "TC", "Giang Vien", "Si So");
        for (Course course : results) {
            System.out.printf("%-10s %-25s %-5d %-20s %-10s\n",
                course.getCourseId(), course.getCourseName(), course.getCredits(),
                "TBD", course.getEnrolledStudents() + "/" + course.getMaxStudents());
        }
    }

    private static void displayRegisteredCourses(Student sv) {
        if (sv.getRegisteredCourses().isEmpty()) {
            System.out.println("Sinh vien chua dang ky mon nao");
            return;
        }
        
        System.out.printf("%-10s %-25s %-5s\n", "Ma MH", "Ten Mon Hoc", "TC");
        for (Course course : sv.getRegisteredCourses()) {
            System.out.printf("%-10s %-25s %-5d\n",
                course.getCourseId(), course.getCourseName(), course.getCredits());
        }
    }

    private static Student findStudent(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    private static Course findCourse(String courseId) {
        for (Course course : courses) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }

    private static int readInteger(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap so nguyen");
            }
        }
    }

    private static String readNonEmptyString(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Loi: Khong duoc de trong");
        }
    }
}

