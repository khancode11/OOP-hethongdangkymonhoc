package service;

import model.Student;
import model.Course;

public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public void registerCourse(Student student, Course course) throws Exception {
        // Business Rule 1: Kiểm tra môn học có tồn tại trên hệ thống không
        if (course == null) {
            throw new Exception("Lỗi nghiệp vụ: Môn học không tồn tại trên hệ thống!");
        }

        // Business Rule 2: Kiểm tra sinh viên đã đăng ký môn này trước đó chưa
        if (student.hasRegistered(course)) {
            System.out.println("Lỗi: Sinh viên đã đăng ký môn học [" + course.getCourseName() + "] trước đó rồi.");
            return;
        }

        // Business Rule 3: Kiểm tra sĩ số tối đa (Sử dụng hàm có sẵn của Người 1 trong Course.java)
        if (!course.hasAvailableSlot()) {
            throw new Exception("Lỗi nghiệp vụ: Môn học " + course.getCourseName() + " đã đủ số lượng sinh viên tối đa!");
        }

        // Business Rule 4: Kiểm tra giới hạn số tín chỉ tối đa của sinh viên
        int creditAfterRegistration = student.getTotalCredits() + course.getCredits();
        if (creditAfterRegistration > student.getMaxCredits()) {
            throw new Exception("Lỗi nghiệp vụ: Vượt quá giới hạn! Tổng số tín chỉ sau khi đăng ký ("
                    + creditAfterRegistration + ") vượt quá mức trần cho phép ("
                    + student.getMaxCredits() + ").");
        }

        // Business Rule 5: Kiểm tra trùng lịch học (Duyệt danh sách môn đã học và gọi hàm isConflict trong Schedule.java)
        for (Course registeredCourse : student.getRegisteredCourses()) {
            if (course.getSchedule().isConflict(registeredCourse.getSchedule())) {
                throw new Exception("Lỗi nghiệp vụ: Trùng lịch học! Môn học mới trùng thời gian với môn ["
                        + registeredCourse.getCourseName() + "] đã đăng ký thành công.");
            }
        }

        // --- NẾU VƯỢT QUA TẤT CẢ CÁC ĐIỀU KIỆN TRÊN THÌ TIẾN HÀNH LƯU DỮ LIỆU ---
        student.addCourse(course);         // Thêm môn học vào danh sách của Sinh viên
        course.increaseEnrolled();         // Tăng số lượng sinh viên đã đăng ký của môn đó lên 1

        System.out.println("Chúc mừng! Đăng ký thành công môn học: " + course.getCourseName());
    }

    @Override
    public void cancelCourse(Student student, Course course) throws Exception {
        // 1. Kiểm tra môn học có hợp lệ không
        if (course == null) {
            throw new Exception("Lỗi nghiệp vụ: Môn học không tồn tại!");
        }

        // Business Rule 6: Không cho phép hủy môn mà sinh viên chưa đăng ký
        if (!student.hasRegistered(course)) {
            System.out.println("Lỗi: Sinh viên chưa từng đăng ký môn học [" + course.getCourseName() + "] nên không thể hủy.");
            return;
        }

        // --- NẾU HỢP LỆ THÌ TIẾN HÀNH HỦY ---
        student.removeCourse(course);      // Xóa môn học khỏi danh sách của Sinh viên
        course.decreaseEnrolled();         // Giảm số lượng sinh viên đã đăng ký của môn đó xuống 1

        System.out.println("Hủy đăng ký thành công môn học: " + course.getCourseName());
    }
}