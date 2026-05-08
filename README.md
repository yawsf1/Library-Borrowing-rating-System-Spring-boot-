# 📚 BookSpring API

A professional-grade **Library Management System** built with **Spring Boot 3**, focusing on security, scalability, and clean architecture.

---

## 🌟 Key Features

- 🔐 **Security First** — Stateless JWT Authentication with Role-Based Access Control (User/Admin)
- 🛡 **Secure Configuration** — Zero-exposure of sensitive credentials using Environment Variables
- 📖 **API Documentation** — Interactive Swagger UI for easy testing and integration
- 🗄 **Optimized Persistence** — PostgreSQL integration with Hibernate SQL formatting for transparent debugging

---

## 🛠 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17+ |
| Framework | Spring Boot 3.x |
| Security | Spring Security + JWT (JJWT 0.12.x) |
| Persistence | Spring Data JPA + PostgreSQL |
| Build Tool | Maven |
| Documentation | Swagger / OpenAPI 3 (springdoc) |
| Utilities | Lombok |

---

## 🚀 Getting Started

### Prerequisites

- JDK 17 or higher
- PostgreSQL installed and running
- Your favorite IDE (IntelliJ, VS Code, etc.)

### Local Setup

**1. Clone the repository:**

```bash
git clone https://github.com/YOUR_USERNAME/book-spring-app.git
cd book-spring-app
```

**2. Create your PostgreSQL database:**

```sql
CREATE DATABASE bookSpring;
```

**3. Set Environment Variables:**

For security, this app requires the following environment variables. Set them in your IDE run configuration or system environment:

| Variable | Description |
|---|---|
| `DB_USERNAME` | Your PostgreSQL username |
| `DB_PASSWORD` | Your PostgreSQL password |
| `JWT_SECRET_KEY` | A secure string for token signing (min 32 characters) |

> 💡 **Tip:** Generate a secure JWT key with `openssl rand -base64 32`

**4. Run the application:**

```bash
mvn spring-boot:run
```

The server will start on **http://localhost:8081**

---

## 📖 API Documentation

Once the app is running, visit the interactive Swagger UI at:

**http://localhost:8081/swagger-ui/index.html**

---

## 🔑 Authentication Flow

```
POST /api/v1/auth/register     → Register a new user
POST /api/v1/auth/authenticate → Login and receive JWT token
POST /api/v1/auth/logout       → Invalidate session
```

For protected endpoints, include the token in the request header:

```
Authorization: Bearer <your_token>
```

---

## 👥 Role-Based Access Control

| Endpoint | USER | ADMIN |
|---|---|---|
| `GET /api/v1/users` | ❌ | ✅ |
| `DELETE /api/v1/users/**` | ❌ | ✅ |
| `GET /api/v1/users/**` | ✅ | ✅ |

---

## 🏗 Project Structure

```
src/main/java/com/testing/bookspringapp
├── auth/           # Authentication controller, service & DTOs
├── config/         # Security, JWT & app configurations
├── exception/      # Global exception handling
├── user/           # User entity, repository & DTOs
├── bookRating/     # Book rating logic
├── bookBorrowing/  # Book borrowing logic
└── BaseEntity.java # Shared auditing fields (createdAt, updatedAt)
```

---

## ⚙️ Configuration Reference

```properties
server.port=8081
spring.datasource.url=jdbc:postgresql://localhost:5432/bookSpring
spring.jpa.hibernate.ddl-auto=update
springdoc.swagger-ui.path=/swagger-ui/index.html
```

---

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).
