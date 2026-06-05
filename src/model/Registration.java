package model;

/**
 * Mot dong trong phieu dang ky = 1 mon hoc cu the
 * Vi du: sinh vien A dang ky mon Lap trinh OOP
 */
public class Registration {

    private Course course;

    public Registration(Course course) {
        this.course = course;
    }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    @Override
    public String toString() {
        return course.toString();
    }
}