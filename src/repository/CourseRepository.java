package repository;

import model.Course;
import model.Schedule;

import java.util.*;
import java.util.stream.Collectors;

// Du lieu luu tru courseid->Course
public class CourseRepository {
    private final Map<String, Course> courseMap = new HashMap<>();
//them / cap nhat
    //neu courseid da ton tai thi ghi de
     public void save(Course course){
         if (course == null || course.getCourseId() == null){
             throw new IllegalArgumentException("Course hoac courseid khong duoc null.")
         }
         courseMap.put(course.getCourseId(), course);
     }
// tim mon hoc theo id
    public Optional<Course> findById(String courseId){
         return Optional.ofNullable(courseMap.get(courseId));

    }
    
}
