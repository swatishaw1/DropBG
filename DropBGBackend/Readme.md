# DropBG Backend (Spring Boot)

DropBG Backend is the core service powering the DropBG background-removal application.  
It handles image processing, user syncing, authentication, credit management, and external API communication via Ngrok tunnels.

---

## 🚀 Features

- Clerk-authenticated user syncing (via JWT)
- Background removal through external API (ClipDrop)
- Credit-based system for processing images
- Secure webhook flow for user validation
- Full CRUD user management
- Optimized Spring Boot architecture
- MySQL database integration

---

## 🏗 Project Structure

DropBGBackend/
│
├── src/main/java/com/example/DropBGBackend
│   ├── Client/
│   │   └── ClipDropClient.java               # Feign client for ClipDrop API
│   │
│   ├── Config/
│   │   ├── FeignConfig.java                  # Feign configuration (if present)
│   │   └── SecurityConfig.java               # Clerk JWT authentication config
│   │
│   ├── Controller/
│   │   ├── ImageController.java              # Handles background removal API
│   │   └── UserController.java               # User creation, sync, credits
│   │
│   ├── DTO/
│   │   └── UserDTO.java                      # Data Transfer Object for user
│   │
│   ├── Entity/
│   │   └── User.java                         # JPA entity for user table
│   │
│   ├── Repository/
│   │   └── UserRepository.java               # JPA repository
│   │
│   ├── Service/
│   │   ├── RemoveBackgroundService.java
│   │   ├── RemoveBackgroundServiceImpli.java
│   │   ├── UserService.java
│   │   └── UserServiceImpl.java
│   │
│   ├── Response/
│   │   └── DropBGResponse.java               # Common API response wrapper
│   │
│   └── DropBgBackendApplication.java         # Main Spring Boot entry point
│
├── src/main/resources/
│   ├── application.properties                # DB, Clerk, ClipDrop API keys
│   └── static/
│
├── pom.xml
└── README.md

---

## 🔑 Environment Variables (`application.properties`)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/dropbg
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASSWORD

clipDrop.apiKey=${CLIPDROP_API_KEY}
clerk.jwt.key=${CLERK_JWT_PUBLIC_KEY}

## 🚀 Tech Stack
- **Java 17**
- **Spring Boot**
  - Spring Web
  - Spring Security (JWT validation)
  - Spring Data JPA
- **MySQL Database**
- **Hibernate**
- **OpenFeign** (ClipDrop API integration)
- **Ngrok** (public tunneling for backend)
- **Lombok**
- **Maven**

---

Spring Boot
│
├── spring-boot-starter-web
├── spring-boot-starter-security
├── spring-boot-starter-data-jpa
├── spring-boot-devtools
└── spring-boot-starter-test
Database & ORM
│
├── mysql-connector-j
└── lombok (1.18.38)
JWT & Authentication
│
├── jjwt-api (0.12.6)
├── jjwt-impl (0.12.6)
└── jjwt-jackson (0.12.6)
API Integrations
│
├── spring-cloud-starter-openfeign
└── spring-cloud-dependencies (${spring-cloud.version})
Webhook & Payment Libs
│── svix (1.11.0) — used for Clerk webhook handling
