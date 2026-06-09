package repository;

import model.Student;

import java.util.*;
import java.util.stream.Collectors;

public class StudentRepository {
    private final Map<String, Student> studentMap = new HashMap<>();

    // them / cap nhat
    public void save(Student student) {
        if (student == null || student.getId() == null) {
            throw new IllegalArgumentException("Student hoac studentId khong duoc null.");
        }
        studentMap.put(student.getId(), student);
    }

    // tim theo id
    public Optional<Student> findById(String studentId) {
        return Optional.ofNullable(studentMap.get(studentId));
    }

    // lay tat ca
    public List<Student> findAll() {
        return new ArrayList<>(studentMap.values());
    }

    // tim theo ten (hoac phan cua ten)
    public List<Student> findByName(String name) {
        if (name == null || name.isBlank()) return Collections.emptyList();
        String keyword = name.trim().toLowerCase();
        return studentMap.values().stream()
                .filter(s -> {
                    String fullName = s.getFullName();
                    return fullName != null && fullName.toLowerCase().contains(keyword);
                })
                .collect(Collectors.toList());
    }

    // xoa theo id
    public void deleteById(String studentId) {
        if (studentId == null) return;
        studentMap.remove(studentId);
    }

    // kiem tra ton tai
    public boolean existsById(String studentId) {
        return studentId != null && studentMap.containsKey(studentId);
    }
}