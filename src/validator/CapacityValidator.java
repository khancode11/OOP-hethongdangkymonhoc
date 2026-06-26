package validator;

import exception.CourseFullException;
import exception.RegistrationException;
import model.Course;
import model.Student;

// Kiểm tra môn học còn chỗ hay không
public class CapacityValidator implements CourseValidator {

    @Override
    public void validate(Student sinhVien, Course monHoc) throws RegistrationException {
        // Nếu môn học đã đủ số lượng sinh viên thì báo lỗi
        if (!monHoc.hasAvailableSlot()) {
            throw new CourseFullException();
        }
    }
}