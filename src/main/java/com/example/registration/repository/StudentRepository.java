package com.example.registration.repository;

import com.example.registration.model.Student;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepository {
    private final String FILE_PATH = "students.txt";

    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return students;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                if (parts.length >= 5) {
                    String id = parts[0];
                    String fullName = parts[1];
                    String className = parts[2];
                    String major = parts[3];
                    int maxCredits = Integer.parseInt(parts[4]);

                    // Gọi đúng constructor 5 tham số của Student gốc
                    Student student = new Student(id, fullName, className, major, maxCredits);
                    students.add(student);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

    public void saveAll(List<Student> students) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Student student : students) {
                bw.write(String.format("%s;%s;%s;%s;%d",
                        student.getId(),
                        student.getFullName(),
                        student.getClassName(),
                        student.getMajor(),
                        student.getMaxCredits()));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}