# 🏋️‍♂️ AI-Powered Fitness Analysis & Recommendation Platform 🤖

A **Java Spring Boot microservices** application that analyzes user activity data and generates **personalized fitness improvement recommendations** using the **Google Gemini AI API**.

Built with a **modern microservices architecture** for scalability, security, and maintainability.

---

## 🚀 Architecture Overview

**Microservices Components**
- **Eureka** → Service discovery
- **Spring Cloud Config Server** → Centralized configuration management
- **API Gateway** → Request routing & load balancing
- **Keycloak** → Authentication & authorization
- **RabbitMQ** → Asynchronous messaging between services

---

## 📌 Features

✅ **User Authentication & Authorization** – Secure login & role-based access via Keycloak  
✅ **User Synchronization** – Syncs user profiles between `user-service` and Keycloak  
✅ **Activity Tracking** – Log running, cycling, gym workouts, and more  
✅ **Asynchronous Messaging** – `activity-service` publishes activity data to RabbitMQ, consumed by `ai-service`  
✅ **AI-Driven Analysis** – Google Gemini API generates personalized improvement tips  
✅ **Service Discovery** – Eureka Server dynamically registers all services  
✅ **Centralized Configuration** – Managed via Spring Cloud Config Server  
✅ **API Gateway** – Single secure entry point for all client requests  
✅ **Database per Service** – Follows microservices best practices (MySQL, MongoDB)  

---

## 🛠 Tech Stack

| Category              | Technology                         |
| --------------------- | ---------------------------------- |
| **Backend**           | Java 21, Spring Boot, Spring Cloud |
| **Authentication**    | Keycloak (OpenID Connect / OAuth2) |
| **Service Discovery** | Netflix Eureka                     |
| **Configuration**     | Spring Cloud Config Server         |
| **Gateway**           | Spring Cloud API Gateway           |
| **Messaging**         | RabbitMQ                           |
| **AI Integration**    | Google Gemini API                  |
| **Databases**         | MySQL & MongoDB (per service)      |
| **Build Tool**        | Maven                              |
| **Containerization**  | Docker & Docker Compose            |

---

## 📂 Microservices

| Service              | Description                                                   |
| -------------------- | ------------------------------------------------------------- |
| **user-service**     | Manages user profiles & syncs with Keycloak                   |
| **activity-service** | Logs activities & publishes events to RabbitMQ                |
| **ai-service**       | Consumes messages, calls Google Gemini API for analysis       |
| **eureka-server**    | Service discovery registry                                    |
| **config-server**    | Centralized configuration for all microservices               |
| **api-gateway**      | Routes requests & integrates Keycloak security                |

---

## 🖥 System Architecture

<img width="904" height="736" alt="fitness-ms drawio" src="https://github.com/user-attachments/assets/89dee6a4-9696-4595-b056-dff2ea916c64" />

---

## ⚡ How It Works

1. **User Authentication**
   - User logs in via **Keycloak** through the **API Gateway**
   - User profile is stored in `user-service` and synced with Keycloak

2. **Activity Logging**
   - User submits activity data (e.g., running, cycling, gym workouts)
   - `activity-service` stores the data and publishes an event to **RabbitMQ** (asynchronous messaging)

3. **AI Analysis**
   - `ai-service` consumes the event from RabbitMQ
   - Calls **Google Gemini API** for personalized improvement recommendations
   - Stores recommendations in the database

4. **User Feedback**
   - Recommendations are returned to the user via the **API Gateway**

---

## 🔮 Future Enhancements
- Mobile application integration
- Real-time activity tracking via wearables
- Advanced analytics dashboard with progress tracking
- Multi-language AI recommendations

---

**👨‍💻 Author:** [ZarnChalamet](https://github.com/zarn-chalamet)
