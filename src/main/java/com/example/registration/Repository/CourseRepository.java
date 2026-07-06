package com.example.registration.repository;

import com.example.registration.model.Course;
import com.example.registration.model.Lecturer;
import com.example.registration.model.Schedule;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CourseRepository {
    private final String FILE_PATH = "courses.txt";
    private List<Course> courses;

    public List<Course> findAll() {
        if (courses != null) {
            return courses;
        }

        List<Course> courses = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            this.courses = courses;
            return this.courses;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                if (parts.length >= 7) {
                    String courseId = parts[0];
                    String courseName = parts[1];
                    int credits = Integer.parseInt(parts[2]);
                    int maxStudents = Integer.parseInt(parts[3]);
                    int dayOfWeek = Integer.parseInt(parts[4]);
                    int startPeriod = Integer.parseInt(parts[5]);
                    int endPeriod = Integer.parseInt(parts[6]);

                    // Khởi tạo giả lập Lecturer và Schedule để khớp constructor Course gốc của bạn
                    Lecturer dummyLecturer = new Lecturer("GV01", "Giang vien Mac dinh", "CNTT");
                    Schedule schedule = new Schedule(dayOfWeek, startPeriod, endPeriod);

                    Course course = new Course(courseId, courseName, credits, dummyLecturer, maxStudents, schedule);
                    courses.add(course);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.courses = courses;
        return this.courses;
    }

    public void saveAll(List<Course> courses) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Course course : courses) {
                Schedule s = course.getSchedule();
                bw.write(String.format("%s;%s;%d;%d;%d;%d;%d",
                        course.getCourseId(),
                        course.getCourseName(),
                        course.getCredits(),
                        course.getMaxStudents(),
                        s != null ? s.getDayOfWeek() : 2,
                        s != null ? s.getStartPeriod() : 1,
                        s != null ? s.getEndPeriod() : 3));
                bw.newLine();
            }
            this.courses = courses;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
