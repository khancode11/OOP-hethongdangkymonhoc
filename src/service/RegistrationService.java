package service;

import model.RegistrationDetail;
import model.Registration;

public interface RegistrationService {

    /**
     * Thêm một môn học mới vào Phiếu đăng ký của sinh viên
     */
    void registerCourse(RegistrationDetail registrationDetail, Registration newDetail) throws Exception;

    /**
     * Hủy/Xóa một môn học khỏi Phiếu đăng ký dựa vào mã môn học (courseId)
     */
    void cancelCourse(RegistrationDetail registrationDetail, String courseId) throws Exception;
}