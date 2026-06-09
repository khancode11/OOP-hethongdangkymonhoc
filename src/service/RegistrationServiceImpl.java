package service;

import model.RegistrationDetail;
import model.Registration;

public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public void registerCourse(RegistrationDetail registrationDetail, Registration newDetail) throws Exception {
        // 1. Kiểm tra null dữ liệu đầu vào
        if (registrationDetail == null || newDetail == null) {
            throw new Exception("Dữ liệu đăng ký không hợp lệ!");
        }

        // 2. Logic kiểm tra: Nếu môn học này đã có trong phiếu rồi thì không cho đăng ký trùng
        String newCourseId = newDetail.getCourse().getCourseId(); // (Hãy đảm bảo class Course đã có hàm getCourseId())

        for (Registration currentReg : registrationDetail.getDetails()) {
            if (currentReg.getCourse().getCourseId().equals(newCourseId)) {
                throw new Exception("Lỗi: Môn học này đã có trong danh sách đăng ký!");
            }
        }

        // 3. Nếu hợp lệ, thêm dòng đăng ký này vào phiếu của sinh viên
        registrationDetail.addDetail(newDetail);
        System.out.println(">> Đăng ký thành công môn: " + newDetail.getCourse().getCourseName());
    }

    @Override
    public void cancelCourse(RegistrationDetail registrationDetail, String courseId) throws Exception {
        // 1. Kiểm tra null dữ liệu đầu vào
        if (registrationDetail == null || courseId == null || courseId.trim().isEmpty()) {
            throw new Exception("Thông tin hủy môn không hợp lệ!");
        }

        // 2. Logic kiểm tra xem môn học có tồn tại trong phiếu hay không trước khi xóa
        boolean isExist = false;
        for (Registration currentReg : registrationDetail.getDetails()) {
            if (currentReg.getCourse().getCourseId().equals(courseId)) {
                isExist = true;
                break;
            }
        }

        if (!isExist) {
            throw new Exception("Lỗi: Không tìm thấy môn học có mã " + courseId + " trong phiếu đăng ký!");
        }

        // 3. Thực hiện xóa dòng đăng ký dựa trên Lambda Expression (mở khóa đoạn code bạn đã comment ở class RegistrationDetail)
        registrationDetail.getDetails().removeIf(d -> d.getCourse().getCourseId().equals(courseId));
        System.out.println(">> Đã hủy thành công môn học có mã: " + courseId);
    }
}