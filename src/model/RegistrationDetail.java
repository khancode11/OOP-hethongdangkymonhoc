package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Phieu dang ky mon hoc cua 1 sinh vien
 * 1 sinh vien co 1 phieu dang ky, phieu do chua nhieu RegistrationDetail
 */
public class RegistrationDetail {

    private Student student;

    // Danh sach cac dong dang ky (moi dong = 1 mon)
    private List<Registration> details;

    public RegistrationDetail(Student student) {
        this.student = student;
        this.details = new ArrayList<>();
    }

    /**
     * Them 1 mon vao phieu dang ky
     */
    public void addDetail(Registration detail) {
        details.add(detail);
    }

    /**
     * Xoa 1 mon khoi phieu dang ky theo courseId
     */
//    public void removeDetail(String courseId) {
//        details.removeIf(d -> d.getCourse().getCourseId().equals(courseId));
//    }

    // Getters
    public Student getStudent()              { return student; }
    public List<Registration> getDetails() { return details; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Phieu dang ky cua: ").append(student.getFullName()).append("\n");
        for (Registration d : details) {
            sb.append("  - ").append(d).append("\n");
        }
        return sb.toString();
    }
}