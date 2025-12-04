## 📁 Project Structure

DropBGBackend/
│
├── src/main/java/com/example/DropBGBackend/
│   ├── Client/
│   │   └── ClipDropClient.java                 # Feign client for ClipDrop API
│   │
│   ├── Config/
│   │   ├── FeignConfig.java                    # Feign configuration (if present)
│   │   └── SecurityConfig.java                  # Clerk JWT authentication config
│   │
│   ├── Controller/
│   │   ├── ImageController.java                # Handles background removal API
│   │   └── UserController.java                 # User creation, sync, credits
│   │
│   ├── DTO/
│   │   └── UserDTO.java                        # Data Transfer Object for user
│   │
│   ├── Entity/
│   │   └── User.java                           # JPA entity for user table
│   │
│   ├── Repository/
│   │   └── UserRepository.java                 # JPA repository
│   │
│   ├── Service/
│   │   ├── RemoveBackgroundService.java
│   │   ├── RemoveBackgroundServiceImpli.java
│   │   ├── UserService.java
│   │   └── UserServiceImpl.java
│   │
│   ├── Response/
│   │   └── DropBGResponse.java                 # Common API response wrapper
│   │
│   └── DropBgBackendApplication.java           # Main Spring Boot entry point
│
├── src/main/resources/
│   ├── application.properties                  # DB, Clerk, ClipDrop API keys
│   └── static/
│
├── pom.xml
└── README.md
