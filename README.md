# Hệ Thống Đăng Ký Môn Học Cho Sinh Viên (Phiên bản Web REST API & Spring Boot)

## Giới thiệu

Đây là dự án cuối kỳ môn Lập Trình Hướng Đối Tượng (OOP) nâng cao, được phát triển từ nền tảng Java OOP thuần và nâng cấp lên kiến trúc hệ thống Web sử dụng **Spring Boot (Backend)** kết hợp với **HTML5/JavaScript (Frontend)**.

Hệ thống mô phỏng quy trình đăng ký môn học trực tuyến của sinh viên trong một học kỳ. Sinh viên tương tác trực tiếp trên giao diện Web để xem danh sách môn học, đăng ký, hủy môn và theo dõi tiến độ tín chỉ theo thời gian thực (Real-time).

---

## Chức năng chính

### Giao diện Sinh viên (Frontend Web UI)
* **Môn học**: Xem danh sách các môn học đang mở kèm sĩ số, lịch học, trạng thái còn chỗ/hết chỗ; tìm kiếm môn học trực quan.
* **Đăng ký**: Nhập Mã SV + Mã môn để đăng ký môn học trực tuyến. Hệ thống tự động kiểm tra điều kiện nghiệp vụ và báo lỗi trực tiếp trên giao diện nếu vi phạm.
* **Môn của tôi**: Xem danh sách các môn đã đăng ký thành công của cá nhân và thanh tiến độ tổng số tín chỉ tích lũy (Credit Progress Bar).

### Hệ thống Quản lý và Nghiệp vụ (Backend REST API)
* Đọc/Ghi và đồng bộ dữ liệu sinh viên, môn học lâu dài thông qua các file văn bản cơ sở (`students.txt`, `courses.txt`).
* Kiểm tra toàn diện các ràng buộc logic chặt chẽ (Validations):
    * Môn học có tồn tại trên hệ thống hay không.
    * Lớp học còn chỗ (Sĩ số < Số lượng tối đa) hay không.
    * Sinh viên đã đăng ký môn học này trước đó chưa.
    * Kiểm tra vượt quá giới hạn số tín chỉ tối đa (`maxCredits`) cấu hình riêng cho từng sinh viên.
    * Kiểm tra trùng lịch học dựa trên thuật toán so khớp khoảng tiết học (`Schedule.isConflict`).

---

## Công nghệ sử dụng

* **Backend**: Java 17+, Spring Boot (REST Web Services, Dependency Injection, IoC Container)
* **Build Tool**: Apache Maven
* **Frontend**: HTML5, CSS3, JavaScript (Fetch API để giao tiếp dữ liệu asynchronous không cần tải lại trang)
* **OOP & Clean Code**: Kế thừa (Inheritance), Đa hình xử lý lỗi ngoại lệ (Polymorphism Custom Exceptions), Collections Framework, File IO mã hóa UTF-8.
* **Quản lý mã nguồn**: Git & GitHub

---

## Cấu trúc Project thực tế (Maven Standard)

```text
StudentRegistrationSystem
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           └── registration/
│       │               ├── controller/  (Xử lý Rest Endpoints API)
│       │               ├── exception/   (Chứa các Custom Exception)
│       │               ├── model/       (Lớp dữ liệu thực thể OOP)
│       │               ├── repository/  (Đọc/Ghi dữ liệu file .txt)
│       │               ├── service/     (Xử lý logic nghiệp vụ xử lý phiếu)
│       │               └── Main.java    (File chạy chính ứng dụng Spring Boot)
│       └── resources/
│           └── static/
│               └── index.html (Giao diện Frontend ứng dụng Web)
├── courses.txt  (Cơ sở dữ liệu lưu trữ môn học)
├── students.txt (Cơ sở dữ liệu lưu trữ sinh viên)
└── pom.xml      (Quản lý thư viện và dependencies của dự án)