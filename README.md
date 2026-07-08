# 📚 Hệ thống Đăng ký Môn học

Hệ thống đăng ký môn học được xây dựng bằng **Spring Boot**, sử dụng **File IO** để lưu trữ dữ liệu và cung cấp các **RESTful API** phục vụ chức năng đăng ký, hủy đăng ký và tra cứu thông tin môn học.

---

# 🚀 Công nghệ sử dụng

- Java 17+
- Spring Boot
- Maven
- Swagger UI
- REST API
- File IO (JSON)

---

# 📁 Cấu trúc API

| Phương thức | Endpoint | Chức năng | Tham số / Body |
|-------------|----------|-----------|----------------|
| **GET** | `/api/courses` | Lấy danh sách môn học hoặc tìm kiếm | `?search=keyword` *(tùy chọn)* |
| **GET** | `/api/students` | Lấy danh sách sinh viên mẫu | Không |
| **GET** | `/api/registration/{studentId}` | Xem phiếu đăng ký của sinh viên | `studentId` trên URL |
| **POST** | `/api/registration/{studentId}/register` | Đăng ký môn học | ```json { "courseId": "Mã_Môn" } ``` |
| **DELETE** | `/api/registration/{studentId}/cancel/{courseId}` | Hủy đăng ký môn học | `studentId`, `courseId` trên URL |

---

# 📖 Swagger UI

Hệ thống đã tích hợp **Swagger UI** để kiểm tra và nghiệm thu toàn bộ API.

Sau khi chạy chương trình, truy cập:

> **http://localhost:8080/swagger-ui/index.html**

Tại đây có thể:

- Xem toàn bộ API
- Chọn **Try it out**
- Nhập dữ liệu
- Chọn **Execute**
- Xem kết quả trả về trực tiếp từ hệ thống

---

# ▶️ Hướng dẫn chạy chương trình

## Cách 1. Chạy bằng IntelliJ IDEA

### Bước 1

Clone project hoặc giải nén source code.

### Bước 2

Mở IntelliJ IDEA.

Chọn

```
Open
```

và mở thư mục chứa file

```
pom.xml
```

### Bước 3

Đợi Maven tải dependency.

Sau đó chuột phải vào

```
pom.xml
```

chọn

```
Maven
→ Reload Project
```

### Bước 4

Đi tới

```
src/main/java/com/example/registration/Main.java
```

### Bước 5

Nhấn nút **Run (▶)** để khởi động ứng dụng.

---

## Cách 2. Chạy bằng Terminal

### Build project

```bash
mvn clean package -DskipTests
```

### Chạy ứng dụng

```bash
java -jar target/dangkymonhoc-1.0.0.jar
```

---

# 🌐 Truy cập hệ thống

Sau khi server chạy thành công trên cổng **8080**, mở trình duyệt và truy cập:

## Giao diện đăng ký môn học

```
http://localhost:8080/index.html
```

## Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

---

# 📂 Chức năng chính

- Xem danh sách môn học
- Tìm kiếm môn học
- Xem danh sách sinh viên
- Xem phiếu đăng ký
- Đăng ký môn học
- Hủy đăng ký môn học
- Lưu dữ liệu bằng File IO
- Kiểm tra API bằng Swagger UI

---

# 👥 Thành viên thực hiện

**Nhóm 02**

**Giảng viên hướng dẫn**

> Thầy Trần Đình Nam Sơn

| Vai trò | Thành viên | MSSV | Nhiệm vụ |
|----------|------------|------|----------|
| Trưởng nhóm | Điêu Văn Khản | 23010131 | Thiết kế hệ thống, Model Layer, Class Diagram, Tích hợp Service & API |
| Thành viên | Phạm Đức Nguyên | | Service Layer, Cài đặt Business Rules |
| Thành viên | Vũ Tiến Đạt | | Validator Layer, Interface, Custom Exception |
| Thành viên | Đào Minh Nghĩa | | Repository Layer, File IO, ClassPathResource |
| Thành viên | Bùi Việt Hùng | | Main Program, Frontend Web UI, Tài liệu, Test Cases |

---

# 📌 Ghi chú

- Ứng dụng sử dụng **Spring Boot Embedded Tomcat**.
- Dữ liệu được lưu bằng **File IO**.
- Toàn bộ API có thể kiểm thử trực tiếp trên **Swagger UI**.
- Không cần cài đặt máy chủ bên ngoài.

---

# 📄 License

Dự án phục vụ mục đích học tập và nghiên cứu.