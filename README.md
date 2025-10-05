# 🚀 Job Portal Backend - RESTful APIs với Spring Boot

Đây là dự án **backend** cho ứng dụng **web việc làm** được xây dựng bằng **Java Spring Boot**.

---

## 📖 Giới thiệu

Dự án cung cấp hệ thống RESTful API để quản lý:

* 👤 Người dùng (ứng viên, nhà tuyển dụng, admin).
* 💼 Tin tuyển dụng (tạo, cập nhật, tìm kiếm).
* 📝 Ứng tuyển & quản lý hồ sơ.
* 🔐 Xác thực & phân quyền với JWT + Spring Security.

---

## 🛠️ Công nghệ sử dụng

* Java 17
* Spring Boot (REST API, Validation, Exception Handling)
* Spring Security + JWT (Authentication & Authorization)
* MySQL (Database)
* Hibernate / JPA (ORM)
* Gradle (Build & Dependency Management)

---

## ⚡ Hướng dẫn chạy dự án

1. Clone dự án về máy:

   git clone https://github.com/<username>/<repo-name>.git cd <repo-name>
2. Cấu hình **application.properties** (MySQL username, password).
3. Chạy lệnh bằng **Gradle Wrapper**:

   ```bash
   ./gradlew bootRun

   > Nếu đang dùng Windows thì chạy:

   ```bash
   gradlew.bat bootRun
   ```
4. API sẽ chạy tại:

   ```
   http://localhost:8080/api/v1/...
   ```

---

