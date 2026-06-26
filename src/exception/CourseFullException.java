package exception;

public class CourseFullException extends RegistrationException {
    public CourseFullException() {
        super("Không thể đăng ký. Môn học đã đủ số lượng sinh viên!");
    }
}