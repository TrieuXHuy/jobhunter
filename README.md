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

1. **Clone dự án về máy:**
   ```sh
   git clone https://github.com/<username>/<repo-name>.git
   cd <repo-name>
   ```

2. **Cấu hình database trong `src/main/resources/application.properties`:**
   - Sửa thông tin MySQL: username, password, tên database.

3. **Chạy dự án bằng Gradle Wrapper:**
   - Trên Linux/macOS:
     ```sh
     ./gradlew bootRun
     ```
   - Trên Windows:
     ```cmd
     .\gradlew.bat bootRun
     ```

4. **Truy cập API tại:**
   ```
   http://localhost:8080/api/v1/...
   ```

---

## 📚 Tham khảo thêm

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Gradle Documentation](https://docs.gradle.org/current/userguide/userguide.html)