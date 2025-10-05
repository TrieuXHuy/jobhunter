# 🚀 Jobhunter Backend – RESTful API với Spring Boot

Dự án này là **backend** cho hệ thống web tuyển dụng, xây dựng bằng **Java Spring Boot**.

---

## 📖 Giới thiệu

Cung cấp các RESTful API cho:

- 👤 Quản lý người dùng (ứng viên, nhà tuyển dụng, admin)
- 💼 Quản lý công ty, tin tuyển dụng (CRUD, tìm kiếm)
- 📝 Ứng tuyển, quản lý hồ sơ
- 🔐 Xác thực & phân quyền (JWT, Spring Security)

---

## 🛠️ Công nghệ sử dụng

- Java 17
- Spring Boot 3.x
- Spring Security + JWT
- MySQL
- Hibernate / JPA
- Gradle Wrapper

---

## ⚡ Hướng dẫn chạy dự án

### 1. Clone dự án về máy

```sh
git clone https://github.com/<username>/<repo-name>.git
cd <repo-name>
```

### 2. Cấu hình database

- Mở file `src/main/resources/application.properties`
- Sửa các thông tin:
  - `spring.datasource.url`
  - `spring.datasource.username`
  - `spring.datasource.password`
- Đảm bảo MySQL đã tạo database với tên phù hợp.

### 3. Build và chạy dự án bằng Gradle Wrapper

- **Trên Linux/macOS:**
  ```sh
  ./gradlew build
  ./gradlew bootRun
  ```
- **Trên Windows:**
  ```cmd
  .\gradlew.bat build
  .\gradlew.bat bootRun
  ```

### 4. Truy cập ứng dụng

- Mặc định chạy tại:  
  ```
  http://localhost:8080
  ```
- Các API thường có tiền tố:  
  ```
  /api/v1/...
  ```

---

## ⚙️ Cấu hình thêm

Các cấu hình chính nằm trong `src/main/resources/application.properties`:

- **Port:**  
  `server.port=8080` (có thể đổi nếu cần)
- **JWT:**  
  `hoidanit.jwt.base64-secret`, `hoidanit.jwt.access-token-validity-in-seconds`
- **Phân trang, upload file:**  
  Tùy chỉnh theo nhu cầu dự án.

---

## 🧑‍💻 Một số lệnh hữu ích

- **Clean & build lại toàn bộ dự án:**
  ```sh
  ./gradlew clean build
  ```
- **Chạy test:**
  ```sh
  ./gradlew test
  ```

---

## 📚 Tham khảo thêm

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Gradle Documentation](https://docs.gradle.org/current/userguide/userguide.html)