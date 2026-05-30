package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Sinh vien - ke thua tu User
 * Quan ly danh sach mon da dang ky va so tin chi
 */
public class Student extends User {

    private String className;
    private String major;
    private int maxCredits;

    // Danh sach mon hoc da dang ky (dung ArrayList - Collection)
    private List<Course> registeredCourses;

    public Student(String id, String fullName, String className, String major, int maxCredits) {
        super(id, fullName); // Goi constructor cua User
        this.className = className;
        this.major = major;
        this.maxCredits = maxCredits;
        this.registeredCourses = new ArrayList<>();
    }

    /**
     * Tinh tong so tin chi hien tai
     */
    public int getTotalCredits() {
        int total = 0;
        for (Course course : registeredCourses) {
            total += course.getCredits();
        }
        return total;
    }

    /**
     * Them mon vao danh sach da dang ky
     * Chi goi sau khi da validate xong ben RegistrationService
     */
    public void addCourse(Course course) {
        registeredCourses.add(course);
    }

    /**
     * Xoa mon khoi danh sach da dang ky
     */
    public void removeCourse(Course course) {
        registeredCourses.remove(course);
    }

    /**
     * Kiem tra sinh vien da dang ky mon nay chua
     */
    public boolean hasRegistered(Course course) {
        return registeredCourses.contains(course);
    }

    // Override abstract method tu User (Polymorphism)
    @Override
    public String getInfo() {
        return "[Sinh vien] " + getId() + " - " + getFullName()
                + " | Lop: " + className
                + " | Tin chi: " + getTotalCredits() + "/" + maxCredits;
    }

    // Getters
    public String getClassName()          { return className; }
    public String getMajor()              { return major; }
    public int getMaxCredits()            { return maxCredits; }
    public List<Course> getRegisteredCourses() { return registeredCourses; }

    // Setters
    public void setClassName(String className) { this.className = className; }
    public void setMajor(String major)         { this.major = major; }
    public void setMaxCredits(int maxCredits)  { this.maxCredits = maxCredits; }
}