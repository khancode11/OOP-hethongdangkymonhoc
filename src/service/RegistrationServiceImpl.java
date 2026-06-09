package service;

import model.RegistrationDetail;
import model.Registration;
import model.Course;
import model.Student;

public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public void registerCourse(RegistrationDetail registrationDetail, Registration newDetail) throws Exception {
        // 1. Kiểm tra null dữ liệu đầu vào
        if (registrationDetail == null || newDetail == null) {
            throw new Exception("Dữ liệu đăng ký không hợp lệ!");
        }

        Course courseToRegister = newDetail.getCourse();
        Student student = registrationDetail.getStudent();

        if (courseToRegister == null || student == null) {
            throw new Exception("Thông tin môn học hoặc sinh viên bị trống!");
        }

        // Kiểm tra xem môn học còn chỗ không
        if (!courseToRegister.hasAvailableSlot()) {
            throw new Exception("Lỗi: Môn học " + courseToRegister.getCourseName() + " đã hết chỗ!");
        }

        // Kiểm tra số lượng tín chí tối đa của sinh viên
        if (student.getTotalCredits() + courseToRegister.getCredits() > student.getMaxCredits()) {
            throw new Exception("Lỗi: Đăng ký môn này sẽ vượt quá số tín chỉ tối đa cho phép (" + student.getMaxCredits() + " TC)!");
        }

        // Logic kiểm tra trùng môn hoặc trùng lịch học
        String newCourseId = courseToRegister.getCourseId();
        for (Registration currentReg : registrationDetail.getDetails()) {
            // Kiểm tra trùng môn
            if (currentReg.getCourse().getCourseId().equals(newCourseId)) {
                throw new Exception("Lỗi: Môn học này đã có trong danh sách đăng ký!");
            }
            // Kiểm tra trùng lịch (Schedule conflict)
            if (currentReg.getCourse().getSchedule().isConflict(courseToRegister.getSchedule())) {
                throw new Exception("Lỗi: Lịch học môn này bị trùng với môn " + currentReg.getCourse().getCourseName() + " đã đăng ký!");
            }
        }

        // Nếu tất cả hợp lệ -> Tiến hành đăng ký
        registrationDetail.addDetail(newDetail);     // Thêm vào phiếu chi tiết
        student.addCourse(courseToRegister);         // Cập nhật danh sách môn của sinh viên
        courseToRegister.increaseEnrolled();         // Tăng số lượng sinh viên đã đăng ký của môn học lên 1

        System.out.println(">> Đăng ký thành công môn: " + courseToRegister.getCourseName());
    }

    @Override
    public void cancelCourse(RegistrationDetail registrationDetail, String courseId) throws Exception {
        // 1. Kiểm tra null dữ liệu đầu vào của nghiệp vụ hủy môn
        if (registrationDetail == null || courseId == null || courseId.trim().isEmpty()) {
            throw new Exception("Thông tin hủy môn không hợp lệ!");
        }

        // 2. Tìm dòng đăng ký (Registration) tương ứng với mã môn học cần hủy trong phiếu
        Registration targetRegistration = null;
        for (Registration currentReg : registrationDetail.getDetails()) {
            if (currentReg.getCourse().getCourseId().equals(courseId)) {
                targetRegistration = currentReg;
                break;
            }
        }

        // Nếu không tìm thấy môn này trong phiếu đăng ký của sinh viên thì báo lỗi
        if (targetRegistration == null) {
            throw new Exception("Lỗi: Không tìm thấy môn học có mã " + courseId + " trong phiếu đăng ký!");
        }

        // Lấy đối tượng môn học và sinh viên ra để xử lý hoàn tác dữ liệu
        Course courseToCancel = targetRegistration.getCourse();
        Student student = registrationDetail.getStudent();

        // 3. Thực hiện hủy và cập nhật đồng bộ lại trạng thái của hệ thống

        // Bước A: Xóa dòng đăng ký này khỏi danh sách chi tiết (details) của Phiếu bằng Lambda Expression
        registrationDetail.getDetails().removeIf(d -> d.getCourse().getCourseId().equals(courseId));

        // Bước B: Xóa môn học khỏi danh sách quản lý riêng của Sinh viên (để trừ tổng số tín chỉ hiện tại)
        if (student != null) {
            student.removeCourse(courseToCancel);
        }

        // Bước C: Giảm số lượng sinh viên đang theo học của môn này đi 1 (nhường slot lại cho người khác)
        if (courseToCancel != null) {
            courseToCancel.decreaseEnrolled();
        }

        System.out.println(">> Đã hủy thành công môn học: " + courseToCancel.getCourseName() + " (Mã: " + courseId + ")");
    }
}