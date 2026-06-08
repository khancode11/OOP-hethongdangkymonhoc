package service;

import model.Student;
import model.Course;

public interface RegistrationService {
    // Đăng ký môn học cho sinh viên
    void registerCourse(Student student, Course course) throws Exception;

    // Hủy môn học đã đăng ký
    void cancelCourse(Student student, Course course) throws Exception;
}