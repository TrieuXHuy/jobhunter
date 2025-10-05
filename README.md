<<<<<<< HEAD
# 🚀 Jobhunter Backend – RESTful API với Spring Boot

Dự án này là **backend** cho hệ thống web tuyển dụng, xây dựng bằng **Java Spring Boot**.
=======
# 🚀 Job Portal Backend - RESTful APIs với Spring Boot

Đây là dự án **backend** cho ứng dụng **web việc làm** được xây dựng bằng **Java Spring Boot**.
>>>>>>> 0ca078b232158fcdcc17eeb79b629f0250adab8d

---

## 📖 Giới thiệu

<<<<<<< HEAD
Cung cấp các RESTful API cho:

- 👤 Quản lý người dùng (ứng viên, nhà tuyển dụng, admin)
- 💼 Quản lý công ty, tin tuyển dụng (CRUD, tìm kiếm)
- 📝 Ứng tuyển, quản lý hồ sơ
- 🔐 Xác thực & phân quyền (JWT, Spring Security)
=======
Dự án cung cấp hệ thống RESTful API để quản lý:

* 👤 Người dùng (ứng viên, nhà tuyển dụng, admin).
* 💼 Tin tuyển dụng (tạo, cập nhật, tìm kiếm).
* 📝 Ứng tuyển & quản lý hồ sơ.
* 🔐 Xác thực & phân quyền với JWT + Spring Security.
>>>>>>> 0ca078b232158fcdcc17eeb79b629f0250adab8d

---

## 🛠️ Công nghệ sử dụng

<<<<<<< HEAD
- Java 17
- Spring Boot 3.x
- Spring Security + JWT
- MySQL
- Hibernate / JPA
- Gradle Wrapper
=======
* Java 17
* Spring Boot (REST API, Validation, Exception Handling)
* Spring Security + JWT (Authentication & Authorization)
* MySQL (Database)
* Hibernate / JPA (ORM)
* Gradle (Build & Dependency Management)
>>>>>>> 0ca078b232158fcdcc17eeb79b629f0250adab8d

---

## ⚡ Hướng dẫn chạy dự án
<<<<<<< HEAD

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
=======

1. Clone dự án về máy:
    
    git clone https://github.com/<username>/<repo-name>.git cd <repo-name>
   
3. Cấu hình application.properties (MySQL username, password).
4. Chạy lệnh:
   
   mvn spring-boot:run
   
5. API sẽ chạy tại: `http://localhost:8080/api/v1/...`
>>>>>>> 0ca078b232158fcdcc17eeb79b629f0250adab8d
