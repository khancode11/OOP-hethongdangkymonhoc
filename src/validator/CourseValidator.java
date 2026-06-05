package validator;

import model.Course;
import model.Student;

// Interface dùng để kiểm tra điều kiện đăng ký môn học
public interface CourseValidator {

    // Hàm kiểm tra điều kiện đăng ký
    void validate(Student sinhVien, Course monHoc) throws Exception;
}