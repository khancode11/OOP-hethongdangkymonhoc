package exception;

public class ScheduleConflictException extends RegistrationException {
    public ScheduleConflictException(String courseName) {
        super("Không thể đăng ký. Môn học bị trùng lịch với môn: " + courseName);
    }
}