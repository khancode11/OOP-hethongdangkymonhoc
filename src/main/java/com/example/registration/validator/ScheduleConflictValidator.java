package com.example.registration.validator;

import com.example.registration.exception.CreditLimitException;
import com.example.registration.model.Course;
import com.example.registration.model.Registration;
import com.example.registration.model.RegistrationDetail;
import org.springframework.stereotype.Component;

@Component
public class ScheduleConflictValidator implements CourseValidator {
    @Override
    public void validate(RegistrationDetail detail, Course course) throws CreditLimitException {
        if (course.getSchedule() != null) {
            for (Registration r : detail.getDetails()) {
                if (r.getCourse().getSchedule() != null &&
                        course.getSchedule().isConflict(r.getCourse().getSchedule())) {
                    throw new CreditLimitException("Trung lich hoc voi mon: " + r.getCourse().getCourseName());
                }
            }
        }
    }
}