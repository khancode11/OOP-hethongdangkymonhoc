package com.example.registration.validator;

import com.example.registration.exception.CreditLimitException;
import com.example.registration.model.Course;
import com.example.registration.model.RegistrationDetail;
import com.example.registration.model.Student;
import org.springframework.stereotype.Component;

@Component
public class CreditLimitValidator implements CourseValidator {
    @Override
    public void validate(RegistrationDetail detail, Course course) throws CreditLimitException {
        Student student = detail.getStudent();
        int currentCredits = detail.getDetails().stream()
                .mapToInt(r -> r.getCourse().getCredits())
                .sum();
        if (currentCredits + course.getCredits() > student.getMaxCredits()) {
            throw new CreditLimitException("Vuot qua gioi han tin chi toi da cua sinh vien (" + student.getMaxCredits() + " tin chi)!");
        }
    }
}