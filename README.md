🏋️‍♂️ AI-Powered Fitness Analysis & Recommendation Platform 🤖

A Java Spring Boot microservices application that analyzes user activity data and generates personalized fitness improvement recommendations using Google Gemini AI API.

The system is built with a modern microservices architecture, using:

  Eureka → Service discovery
  
  Spring Cloud Config Server → Centralized configuration
  
  API Gateway → Request routing & load balancing
  
  Keycloak → Authentication & authorization
  
  RabbitMQ → Asynchronous messaging between services

        
📌 Features

✅ User Authentication & Authorization – Secure login & role-based access via Keycloak

✅ User Synchronization – User profiles are synced between user-service and Keycloak

✅ Activity Tracking – Log activities like running, cycling, or gym workouts

✅ Asynchronous Messaging – activity-service publishes activity data to RabbitMQ, consumed by ai-service

✅ AI-Driven Analysis – Uses Google Gemini API to generate personalized improvement recommendations

✅ Service Discovery – Eureka Server dynamically registers all services

✅ Centralized Configuration – Managed through Spring Cloud Config Server

✅ API Gateway – Single entry point for all client requests

✅ Database per Service – Following microservices best practices



🛠 Tech Stack

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




📂 Microservices

| Service              | Description                                                   |
| -------------------- | ------------------------------------------------------------- |
| **user-service**     | Manages user profiles & syncs with Keycloak                   |
| **activity-service** | Logs activities & publishes events to RabbitMQ                |
| **ai-service**       | Consumes messages, connects to Google Gemini API for analysis |
| **eureka-server**    | Service discovery registry                                    |
| **config-server**    | Centralized configuration for all microservices               |
| **api-gateway**      | Routes requests & integrates Keycloak security                |



🖥 System Architecture


<img width="904" height="736" alt="fitness-ms drawio" src="https://github.com/user-attachments/assets/89dee6a4-9696-4595-b056-dff2ea916c64" />



⚡ How It Works

User logs in via Keycloak through the API Gateway.

User profile is stored in user-service and synced with Keycloak.

User submits activity to activity-service.

activity-service publishes the activity event to RabbitMQ (asynchronous).

ai-service consumes the message, calls Google Gemini API, and generates a personalized analysis.

AI recommendations are stored & returned to the user via API Gateway.

