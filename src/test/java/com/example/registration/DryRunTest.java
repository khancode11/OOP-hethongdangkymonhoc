package com.example.registration;

import com.example.registration.exception.CourseNotFoundException;
import com.example.registration.exception.CreditLimitException;
import com.example.registration.exception.DuplicateRegistrationException;
import com.example.registration.repository.CourseRepository;
import com.example.registration.repository.StudentRepository;
import com.example.registration.service.RegistrationService;

public class DryRunTest {
    public static void main(String[] args) throws Exception {
        CourseRepository courseRepository = new CourseRepository();
        StudentRepository studentRepository = new StudentRepository();

        testValidRegistration(courseRepository, studentRepository);
        testDuplicateRegistration(courseRepository, studentRepository);
        testScheduleConflict(courseRepository, studentRepository);
        testCreditLimit(courseRepository, studentRepository);
        testCancelUnregisteredCourse(courseRepository, studentRepository);
        testCourseCapacity(courseRepository, studentRepository);

        System.out.println("DRY RUN PASSED");
    }

    private static void testValidRegistration(
            CourseRepository courseRepository,
            StudentRepository studentRepository)
            throws CourseNotFoundException, CreditLimitException, DuplicateRegistrationException {
        RegistrationService service = new RegistrationService(courseRepository, studentRepository);

        service.registerCourse("23010131", "IT001");

        int totalCredits = service.getRegistrationByStudent("23010131").getStudent().getTotalCredits();
        assertEquals(4, totalCredits, "Dang ky hop le phai cap nhat tong tin chi");
    }

    private static void testDuplicateRegistration(
            CourseRepository courseRepository,
            StudentRepository studentRepository)
            throws CourseNotFoundException, CreditLimitException, DuplicateRegistrationException {
        RegistrationService service = new RegistrationService(courseRepository, studentRepository);

        service.registerCourse("SV008", "IT008");

        assertThrows(DuplicateRegistrationException.class,
                () -> service.registerCourse("SV008", "IT008"),
                "Dang ky trung mon phai bi tu choi");
    }

    private static void testScheduleConflict(
            CourseRepository courseRepository,
            StudentRepository studentRepository)
            throws CourseNotFoundException, CreditLimitException, DuplicateRegistrationException {
        RegistrationService service = new RegistrationService(courseRepository, studentRepository);

        service.registerCourse("SV002", "IT001");
        assertThrows(CreditLimitException.class,
                () -> service.registerCourse("SV002", "IT005"),
                "Dang ky trung lich phai bi tu choi");
    }

    private static void testCreditLimit(
            CourseRepository courseRepository,
            StudentRepository studentRepository)
            throws CourseNotFoundException, CreditLimitException, DuplicateRegistrationException {
        RegistrationService service = new RegistrationService(courseRepository, studentRepository);

        service.registerCourse("SV006", "IT001");
        service.registerCourse("SV006", "IT002");
        service.registerCourse("SV006", "IT003");
        service.registerCourse("SV006", "IT006");

        assertThrows(CreditLimitException.class,
                () -> service.registerCourse("SV006", "IT007"),
                "Dang ky vuot gioi han tin chi phai bi tu choi");
    }

    private static void testCancelUnregisteredCourse(
            CourseRepository courseRepository,
            StudentRepository studentRepository) {
        RegistrationService service = new RegistrationService(courseRepository, studentRepository);

        assertThrows(CourseNotFoundException.class,
                () -> service.cancelCourse("SV003", "IT001"),
                "Huy mon chua dang ky phai bao loi");
    }

    private static void testCourseCapacity(
            CourseRepository courseRepository,
            StudentRepository studentRepository)
            throws CourseNotFoundException, CreditLimitException, DuplicateRegistrationException {
        RegistrationService service = new RegistrationService(courseRepository, studentRepository);

        service.registerCourse("SV004", "IT009");
        service.registerCourse("SV005", "IT009");

        assertThrows(CourseNotFoundException.class,
                () -> service.registerCourse("SV007", "IT009"),
                "Dang ky mon het cho phai bi tu choi");
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + ". Expected: " + expected + ", actual: " + actual);
        }
    }

    private static <T extends Throwable> void assertThrows(
            Class<T> expectedType,
            ThrowingAction action,
            String message) {
        try {
            action.run();
        } catch (Throwable e) {
            if (expectedType.isInstance(e)) {
                return;
            }
            throw new AssertionError(message + ". Sai loai loi: " + e.getClass().getSimpleName(), e);
        }

        throw new AssertionError(message + ". Khong co loi nao duoc nem ra");
    }

    @FunctionalInterface
    private interface ThrowingAction {
        void run() throws Exception;
    }
}
