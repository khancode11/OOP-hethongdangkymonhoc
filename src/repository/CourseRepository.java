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
             throw new IllegalArgumentException("Course hoac co courseid khong duoc null.")
         }
         courseMap.put(course.getCourseId(), course);
     }
// tim mon hoc theo id
    public Optional<Course> findById(String courseId) {
        return Optional.ofNullable(courseMap.get(courseId));

    }
//lay tat ca mon hoc
    public List<Course> findAll(){
         return new ArrayList<>(courseMap.values());
    }
//tim mon hoc theo ten
    public List<Course> findByName(String name){
         if (name == null || name.isBlank()) return Collections.emptyList();
         String keyword = name.trim().toLowerCase();
         return courseMap.values().stream().filter(c->c.getCourseName() != null && c.getCourseName().toLowerCase().contains(keyword)).collect(Collectors.toList());
    }
    public List<Course>findByLecturer(String lecturerId){
         if (lecturerId == null) return Collections.emptyList();
         return courseMap.values().stream()
                 .
    }

}
