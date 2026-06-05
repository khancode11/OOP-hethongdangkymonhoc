# Hệ Thống Đăng Ký Môn Học Cho Sinh Viên

## Giới thiệu

Đây là dự án cuối kỳ môn Lập Trình Hướng Đối Tượng (OOP) được xây dựng bằng Java.

Hệ thống mô phỏng quy trình đăng ký môn học của sinh viên trong một học kỳ. Sinh viên có thể xem danh sách môn học đang mở, đăng ký môn học, hủy môn học đã đăng ký và theo dõi tổng số tín chỉ hiện tại.

---

## Chức năng chính

### Quản lý sinh viên

* Xem danh sách môn học đang mở.
* Đăng ký môn học.
* Hủy môn học đã đăng ký.
* Xem danh sách môn học đã đăng ký.
* Xem tổng số tín chỉ hiện tại.

### Quản lý môn học

* Hiển thị danh sách môn học.
* Tìm kiếm môn học theo mã hoặc tên môn học.
* Kiểm tra môn học còn chỗ hay không.

### Đăng ký môn học

Hệ thống kiểm tra:

* Môn học có tồn tại hay không.
* Môn học còn chỗ hay không.
* Sinh viên đã đăng ký môn học trước đó chưa.
* Có vượt quá số tín chỉ tối đa hay không.
* Có bị trùng lịch học hay không.

### Hủy đăng ký môn học

* Hủy môn học đã đăng ký.
* Cập nhật lại số lượng sinh viên của môn học.

---

## Công nghệ sử dụng

* Java
* OOP (Object-Oriented Programming)
* Collections Framework
* Exception Handling
* File IO
* Git & GitHub

---

## Cấu trúc Project

```text
src
├── model
├── service
├── repository
├── validator
├── utils
├── exception
└── main
```

### Mô tả package

| Package    | Chức năng                  |
| ---------- | -------------------------- |
| model      | Chứa các lớp dữ liệu       |
| service    | Xử lý nghiệp vụ            |
| repository | Đọc/Ghi dữ liệu            |
| validator  | Kiểm tra điều kiện đăng ký |
| utils      | Các hàm hỗ trợ             |
| exception  | Custom Exception           |
| main       | Chương trình chạy chính    |

---

## Các lớp chính

### Model Layer

* User
* Student
* Lecturer
* Course
* Schedule
* Registration
* RegistrationDetail

---

## Yêu cầu nghiệp vụ

Không cho phép:

* Đăng ký môn học không tồn tại.
* Đăng ký môn đã đủ số lượng.
* Đăng ký trùng môn học.
* Đăng ký vượt quá số tín chỉ tối đa.
* Đăng ký môn học bị trùng lịch.
* Hủy môn học chưa từng đăng ký.

---

## Cấu trúc Git Branch

```text
main
│
└── develop
     ├── DieuVanKhan
     ├── PhamDucNguyen
     ├── VuTienDat
     ├── DaoMinhNghia
     └── BuiVietHung
```

### Quy trình làm việc

1. Tạo branch từ `develop`.
2. Thực hiện chức năng được phân công.
3. Commit và push lên branch cá nhân.
4. Merge vào `develop`.
5. Sau khi hoàn thành toàn bộ dự án, merge `develop` vào `main`.

---

## Phân công công việc

| Thành viên             | Công việc                                     |
|------------------------| --------------------------------------------- |
| Điêu Văn Khản (Leader) | Model Layer, Class Diagram, Tích hợp hệ thống |
| Người 2                | Service Layer                                 |
| Vũ Tiến Đạt            | Validator                                     |
| Người 4                | Repository & File IO                          |
| Người 5                | Main Program, Menu, Test Cases                |

---

## Cách chạy chương trình

1. Clone project:

```bash
git clone <repository-url>
```

2. Mở project bằng IntelliJ IDEA.

3. Chạy:

```text
src/main/Main.java
```

4. Sử dụng menu để thao tác với hệ thống.

---

## Thành viên nhóm

* Thành viên 1: Điêu Văn Khản
* Thành viên 2: Phạm Đức Nguyên
* Thành viên 3: Vũ Tiến Đạt
* Thành viên 4: Đào Minh Nghĩa
* Thành viên 5: Bùi Việt Huùng

---

## Giảng viên hướng dẫn
Thầy: Trần Đình Sơn Nam
