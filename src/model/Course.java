package model;

/**
 * Mon hoc duoc mo trong hoc ky
 */
public class Course {

    private String courseId;
    private String courseName;
    private int credits;
    private Lecturer lecturer;
    private int maxStudents;
    private int enrolledStudents; // So luong da dang ky hien tai
    private Schedule schedule;

    public Course(String courseId, String courseName, int credits,
                  Lecturer lecturer, int maxStudents, Schedule schedule) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.lecturer = lecturer;
        this.maxStudents = maxStudents;
        this.enrolledStudents = 0; // Moi tao ra chua co ai dang ky
        this.schedule = schedule;
    }

    /**
     * Kiem tra mon hoc con cho khong
     */
    public boolean hasAvailableSlot() {
        return enrolledStudents < maxStudents;
    }

    /**
     * Tang so luong khi co sinh vien dang ky thanh cong
     */
    public void increaseEnrolled() {
        enrolledStudents++;
    }

    /**
     * Giam so luong khi sinh vien huy dang ky
     */
    public void decreaseEnrolled() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
        }
    }

    // Getters
    public String getCourseId()       { return courseId; }
    public String getCourseName()     { return courseName; }
    public int getCredits()           { return credits; }
    public Lecturer getLecturer()     { return lecturer; }
    public int getMaxStudents()       { return maxStudents; }
    public int getEnrolledStudents()  { return enrolledStudents; }
    public Schedule getSchedule()     { return schedule; }

    // Setters
    public void setCourseId(String courseId)       { this.courseId = courseId; }
    public void setCourseName(String courseName)   { this.courseName = courseName; }
    public void setCredits(int credits)            { this.credits = credits; }
    public void setLecturer(Lecturer lecturer)     { this.lecturer = lecturer; }
    public void setMaxStudents(int maxStudents)    { this.maxStudents = maxStudents; }
    public void setSchedule(Schedule schedule)     { this.schedule = schedule; }

    @Override
    public String toString() {
        return courseId + " | " + courseName
                + " | " + credits + " TC"
                + " | GV: " + lecturer.getFullName()
                + " | " + schedule
                + " | SL: " + enrolledStudents + "/" + maxStudents;
    }
}