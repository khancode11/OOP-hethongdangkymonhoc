package com.example.registration.validator;

import com.example.registration.exception.CreditLimitException;
import com.example.registration.model.Course;
import com.example.registration.model.RegistrationDetail;
import org.springframework.stereotype.Component;

@Component
public class CapacityValidator implements CourseValidator {
    @Override
    public void validate(RegistrationDetail detail, Course course) throws CreditLimitException {
        if (!course.hasAvailableSlot()) {
            throw new CreditLimitException("Mon hoc " + course.getCourseName() + " da du so luong sinh vien toi da!");
        }
    }
}