package exception;

public class CreditLimitExceededException extends RegistrationException {
    public CreditLimitExceededException() {
        super("Không thể đăng ký. Tổng số tín chỉ vượt quá giới hạn cho phép!");
    }
}