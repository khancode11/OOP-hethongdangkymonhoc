package service;

import model.Course;
import java.util.ArrayList;
import java.util.List;

public class CourseService {

    // Danh sách môn học hệ thống (thường được nạp từ CourseRepository của Người 4)
    private List<Course> allCourses;

    public CourseService(List<Course> allCourses) {
        this.allCourses = allCourses;
    }

    /**
     * 2.2 Hiển thị danh sách toàn bộ môn học đang mở
     */
    public void displayAllCourses() {
        if (allCourses == null || allCourses.isEmpty()) {
            System.out.println("Hiện tại không có môn học nào được mở.");
            return;
        }
        System.out.println("\n========= DANH SÁCH MÔN HỌC ĐANG MỞ =========");
        for (Course course : allCourses) {
            System.out.println(course.toString()); // Sử dụng hàm toString() Người 1 đã custom rất đẹp
        }
    }

    /**
     * 2.2 Tìm kiếm môn học theo mã môn hoặc tên môn
     */
    public List<Course> searchCourse(String keyword) {
        List<Course> foundCourses = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            return foundCourses;
        }

        String searchKey = keyword.toLowerCase().trim();
        for (Course course : allCourses) {
            // Khớp chuẩn với getCourseId() và getCourseName() của lớp Course
            if (course.getCourseId().toLowerCase().contains(searchKey) ||
                    course.getCourseName().toLowerCase().contains(searchKey)) {
                foundCourses.add(course);
            }
        }
        return foundCourses;
    }

    /**
     * 2.2 Kiểm tra môn học còn chỗ hay không
     */
    public boolean checkCourseAvailability(Course course) {
        if (course == null) return false;
        return course.hasAvailableSlot(); // Gọi trực tiếp logic của Người 1
    }
}