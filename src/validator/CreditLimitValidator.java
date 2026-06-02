package validator;

import model.Course;
import model.Student;

// Kiểm tra tổng số tín chỉ sau khi đăng ký có vượt quá giới hạn không
public class CreditLimitValidator implements CourseValidator {

    @Override
    public void validate(Student sinhVien, Course monHoc) throws Exception {
        int tongTinChiHienTai = sinhVien.getTotalCredits();
        int tongTinChiSauKhiDangKy = tongTinChiHienTai + monHoc.getCredits();

        // Nếu tổng tín chỉ vượt quá giới hạn thì báo lỗi
        if (tongTinChiSauKhiDangKy > sinhVien.getMaxCredits()) {
            throw new Exception("Không thể đăng ký. Tổng số tín chỉ vượt quá giới hạn cho phép!");
        }
    }
}