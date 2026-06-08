package service;

import model.Student;
import model.Course;

public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public void registerCourse(Student student, Course course) throws Exception {
        // 1. Kiểm tra môn học có tồn tại không
        if (course == null) {
            throw new IllegalArgumentException("Lỗi: Môn học không tồn tại trên hệ thống!");
        }

        // 2. Kiểm tra sinh viên đã đăng ký môn này chưa (Dùng hàm của Người 1)
        if (student.hasRegistered(course)) {
            System.out.println("Lỗi: Sinh viên đã đăng ký môn học [" + course.getCourseName() + "] trước đó rồi.");
            return;
        }

        // 3. Kiểm tra sĩ số (Dùng trực tiếp hàm hasAvailableSlot() của Người 1 trong Course.java)
        if (!course.hasAvailableSlot()) {
            throw new IllegalStateException("Lỗi: Môn học " + course.getCourseName() + " đã đủ số lượng sinh viên tối đa!");
        }

        // 4. Kiểm tra giới hạn số tín chỉ (Tự tính toán dựa trên dữ liệu của Người 1)
        int creditAfterRegistration = student.getTotalCredits() + course.getCredits();
        if (creditAfterRegistration > student.getMaxCredits()) {
            System.out.println("Lỗi: Đăng ký thất bại! Tổng số tín chỉ sau khi đăng ký ("
                    + creditAfterRegistration + ") vượt quá giới hạn cho phép ("
                    + student.getMaxCredits() + ").");
            return;
        }

        // 5. Kiểm tra trùng lịch học (Duyệt danh sách và dùng hàm isConflict() của Người 1 trong Schedule.java)
        for (Course registeredCourse : student.getRegisteredCourses()) {
            if (course.getSchedule().isConflict(registeredCourse.getSchedule())) {
                System.out.println("Lỗi: Trùng lịch học! Môn mới trùng lịch với môn ["
                        + registeredCourse.getCourseName() + "] đã đăng ký.");
                return;
            }
        }

        // --- NẾU ĐẠT ĐỦ ĐIỀU KIỆN THÌ TIẾN HÀNH LƯU ---
        student.addCourse(course);         // Thêm môn vào danh sách của SV
        course.increaseEnrolled();         // Tăng sĩ số môn học lên 1

        System.out.println("Chúc mừng! Đăng ký thành công môn học: " + course.getCourseName());
    }

    @Override
    public void cancelCourse(Student student, Course course) throws Exception {
        if (course == null) {
            throw new IllegalArgumentException("Lỗi: Môn học không tồn tại!");
        }

        if (!student.hasRegistered(course)) {
            System.out.println("Lỗi: Sinh viên chưa từng đăng ký môn học [" + course.getCourseName() + "] nên không thể hủy.");
            return;
        }

        student.removeCourse(course);      // Xóa môn khỏi danh sách của SV
        course.decreaseEnrolled();         // Giảm sĩ số môn học xuống 1

        System.out.println("Hủy đăng ký thành công môn học: " + course.getCourseName());
    }
}