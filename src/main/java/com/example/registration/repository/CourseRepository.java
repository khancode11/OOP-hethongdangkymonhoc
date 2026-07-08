package com.example.registration.repository;

import com.example.registration.model.Course;
import com.example.registration.model.Lecturer;
import com.example.registration.model.Schedule;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CourseRepository {
    // Thay đổi cách lấy File Path từ trong resources
    private File getFile() throws IOException {
        return new ClassPathResource("courses.txt").getFile();
    }

    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();
        try {
            File file = getFile();
            if (!file.exists()) return courses;

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] parts = line.split(";");
                    if (parts.length >= 8) {
                        String courseId = parts[0];
                        String courseName = parts[1];
                        int credits = Integer.parseInt(parts[2]);
                        int maxStudents = Integer.parseInt(parts[3]);
                        int enrolledStudents = Integer.parseInt(parts[4]);
                        int dayOfWeek = Integer.parseInt(parts[5]);
                        int startPeriod = Integer.parseInt(parts[6]);
                        int endPeriod = Integer.parseInt(parts[7]);

                        Lecturer dummyLecturer = new Lecturer("GV01", "Giang vien Mac dinh", "CNTT");
                        Schedule schedule = new Schedule(dayOfWeek, startPeriod, endPeriod);

                        Course course = new Course(courseId, courseName, credits, dummyLecturer, maxStudents, schedule);
                        for (int i = 0; i < enrolledStudents; i++) {
                            course.increaseEnrolled();
                        }
                        courses.add(course);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public void saveAll(List<Course> courses) {
        try {
            File file = getFile();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (Course course : courses) {
                    Schedule s = course.getSchedule();
                    bw.write(String.format("%s;%s;%d;%d;%d;%d;%d;%d",
                            course.getCourseId(),
                            course.getCourseName(),
                            course.getCredits(),
                            course.getMaxStudents(),
                            course.getEnrolledStudents(),
                            s != null ? s.getDayOfWeek() : 2,
                            s != null ? s.getStartPeriod() : 1,
                            s != null ? s.getEndPeriod() : 3));
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}