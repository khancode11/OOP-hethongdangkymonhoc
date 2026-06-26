package validator;

import exception.RegistrationException;
import exception.ScheduleConflictException;
import model.Course;
import model.Student;

// Kiểm tra môn học mới có bị trùng lịch với môn đã đăng ký không
public class ScheduleConflictValidator implements CourseValidator {

    @Override
    public void validate(Student sinhVien, Course monHocMoi) throws RegistrationException {

        // Duyệt qua từng môn sinh viên đã đăng ký
        for (Course monHocDaDangKy : sinhVien.getRegisteredCourses()) {

            // Nếu lịch môn đã đăng ký trùng với lịch môn mới thì báo lỗi
            if (monHocDaDangKy.getSchedule().isConflict(monHocMoi.getSchedule())) {
                throw new ScheduleConflictException(monHocDaDangKy.getCourseName());
            }
        }
    }
}