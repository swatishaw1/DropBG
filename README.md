# DropBG — Full Stack Project

A full-stack AI background removal platform built with React (frontend) and Spring Boot (backend).
The system integrates Clerk authentication, JWT verification through JWKS, and secure user data storage.

---

## Project Overview

This project delivers an end-to-end solution for image background removal.
The frontend handles image upload, UI rendering, pricing, testimonials, routing, and user interactions.
The backend handles user authentication, JWT verification, database persistence, and secured API endpoints.

---

# Frontend

## Tech Stack

* React
* React Router
* TailwindCSS
* Lucide Icons
* React Hot Toast

## Installation

```
npm install react
npm install react-dom
npm install react-router-dom
npm install tailwindcss
npm install lucide-react
npm install react-hot-toast
```

## Folder Structure

```
frontend/
│
├── Components/
│   ├── BgRemovalSteps.jsx
│   ├── BgSlider.jsx
│   ├── Footer.jsx
│   ├── Header.jsx
│   ├── Menubar.jsx
│   ├── Pricing.jsx
│   ├── Testimonials.jsx
│   └── TryNow.jsx
│
├── assets/
│   ├── assets.jsx
│   ├── dollar.png
│   ├── home-page-banner.mp4
│   ├── logo.png
│   ├── people.png
│   ├── people-org.png
│   └── slide_icon.svg
│
├── pages/
│   └── Home.jsx
│
├── App.jsx  
├── App.css  
├── index.css  
└── main.jsx
```

## Environment Variables

```
VITE_CLERK_PUBLISHABLE_KEY=
VITE_BACKEND_URL=
```

---

# Backend

## Tech Stack

* Java 17
* Spring Boot
* Spring Security
* JPA / Hibernate
* MySQL
* JWT + JWKS (Clerk)

## Key Features

* Clerk JWT verification using JWKS
* Custom OncePerRequestFilter for JWT validation
* Role-based authentication
* MySQL persistence with JPA
* DTO-Entity mapping
* CORS configuration for frontend integration

## Folder Structure

```
backend/
│
├── src/main/java/com/example/DropBGBackend/
│   ├── Config/
│   │   └── SecurityConfig.java
│   │
│   ├── Controller/
│   │   └── UserController.java
│   │
│   ├── DTO/
│   │   └── UserDTO.java
│   │
│   ├── Entity/
│   │   └── UserEntity.java
│   │
│   ├── Repository/
│   │   └── UserRepository.java
│   │
│   ├── Response/
│   │   └── DropBGResponse.java
│   │
│   ├── Security/
│   │   ├── ClerkJwksProvider.java
│   │   └── ClerkJwtAuthFilter.java
│   │
│   ├── Service/
│   │   └── DropBgApplication.java
│   │
│   └── DropBgApplication.java
│
├── src/main/resources/
│   ├── static/
│   ├── templates/
│   └── application.properties
└── pom.xml
```

## Backend Environment Variables

```
clerk.issuer=
clerk.jwks-url=
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

