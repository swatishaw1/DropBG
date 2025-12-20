# **DropBG – AI Background Remover (Full-Stack Project)**

DropBG is a full-stack application designed for **fast, clean, and reliable background removal**.
It delivers a polished user experience on the frontend and a robust, production-style backend handling authentication, credits, and image processing.

---

## 🚀 **Features**

### 🌐 **Frontend (React)**

* Modern, responsive UI
* Smooth image upload and processing flow
* Before/After preview for removed background images
* Live credit display
* Clerk-powered authentication (Sign-in, Sign-up, session management)
* Toast notifications and elegant loading indicators

### 🛠 **Backend (Spring Boot)**

* Secure Clerk JWT validation
* User creation & syncing via Clerk
* Credit management (deduction & retrieval)
* Image background removal via **ClipDrop API**
* Ngrok tunnel support for webhook communication
* Clean DTO, Service, Repository architecture

### 🔐 **Authentication**

* Fully integrated **Clerk Authentication**
* Backend validates JWT on every protected endpoint
* Automatic user sync between Clerk → Database

---

## 🧰 **Tech Stack**

### **Frontend**

* React (Vite)
* Clerk Authentication
* Axios
* TailwindCSS
* React Router
* React Hot Toast
* Context API

### **Backend**

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* MySQL
* OpenFeign (ClipDrop API communication)
* Svix (Clerk webhook handling)
* Lombok
* Ngrok

---

## 📦 **Backend Dependencies (from `pom.xml`)**

### **Spring Boot**

* spring-boot-starter-web
* spring-boot-starter-security
* spring-boot-starter-data-jpa
* spring-boot-devtools
* spring-boot-starter-test

### **Database & ORM**

* mysql-connector-j
* lombok (1.18.38)

### **JWT & Authentication**

* jjwt-api (0.12.6)
* jjwt-impl (0.12.6)
* jjwt-jackson (0.12.6)

### **API Integrations**

* spring-cloud-starter-openfeign
* spring-cloud-dependencies (${spring-cloud.version})

### **Webhook & Payment Libraries**

* svix (1.11.0) — used for Clerk webhooks
* razorpay-java (1.4.1) — included but not used in final version

---

## 🔧 **Environment Variables**

### **Frontend (`.env`)**

```
VITE_BACKEND_URL=http://localhost:8080/api
VITE_CLERK_PUBLISHABLE_KEY=your_clerk_key
```

### **Backend (`application.properties`)**

```
spring.datasource.url=jdbc:mysql://localhost:3306/dropbg
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASSWORD

clipDrop.apiKey=${CLIPDROP_API_KEY}
clerk.jwt.key=${CLERK_JWT_PUBLIC_KEY}
```

---

## ▶ **How to Run the Project**

### **1️⃣ Start Backend**

```bash
cd DropBGBackend
mvn clean install
mvn spring-boot:run
```

Backend runs on:

```
http://localhost:8080
```

### **2️⃣ Start Frontend**

```bash
cd DropBGFrontend
npm install
npm run dev
```

Frontend runs on:

```
http://localhost:5173
```

---

## 🔄 **API Workflow**

### **1. User Sync (Frontend → Backend)**

Triggered when a user logs in via Clerk.

```
POST /api/users
```

### **2. Get User Credits**

```
GET /api/users/credits
```

### **3. Remove Background**

```
POST /api/images/remove-background
```

Flow:

1. Frontend uploads image
2. Backend sends to ClipDrop API
3. ClipDrop returns processed PNG
4. Backend converts PNG → Base64
5. Frontend displays output and updates credits

---

## 🎯 **What This Project Demonstrates**

* Real-world **full-stack architecture**
* Modern frontend UX with clean UI design
* Secure **JWT authentication** with Clerk
* Integration of external APIs (ClipDrop)
* State management + API coordination
* Production-style backend layering (Controller → Service → Repository)

---

## 📌 **Future Improvements**

* Add premium features
* Add image history
* Add ability to choose output resolution
* Deploy backend + frontend to cloud

---

## 🏁 **Conclusion**

DropBG is a complete end-to-end application showcasing:

* Strong frontend development
* Solid backend engineering
* Authentication, API integrations, and state management

A polished real-world project demonstrating full-stack capability.

