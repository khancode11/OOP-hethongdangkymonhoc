package validator;

import model.Course;
import model.Student;

// Kiểm tra môn học còn chỗ hay không
public class CapacityValidator implements CourseValidator {

    @Override
    public void validate(Student sinhVien, Course monHoc) throws Exception {

        // Nếu môn học đã đủ số lượng sinh viên thì báo lỗi
        if (!monHoc.hasAvailableSlot()) {
            throw new Exception("Không thể đăng ký. Môn học đã đủ số lượng sinh viên!");
        }
    }
}