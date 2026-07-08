package com.example.registration.validator;

import com.example.registration.exception.CreditLimitException;
import com.example.registration.exception.DuplicateRegistrationException;
import com.example.registration.model.Course;
import com.example.registration.model.RegistrationDetail;

public interface CourseValidator {
    void validate(RegistrationDetail detail, Course course)
            throws CreditLimitException, DuplicateRegistrationException;
}