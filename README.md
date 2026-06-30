# Amanak Fullstack

Amanak is a fullstack digital government service platform designed to make public services easier to access, track, and trust. The project brings together a mobile-first client experience, a Spring Boot backend, secure authentication, and structured government-style data flows into one cohesive application.

The idea behind Amanak is simple: citizens should not have to move between offices, papers, calls, and unclear procedures to follow up on a request. The platform centralizes the experience into a clean application where users can authenticate, manage account access, view official content, and interact with service-related data through a modern API-driven architecture.

<p align="center">
  <a href="https://github.com/Layansabha/amanak-fullstack/releases/download/v1.0-demo/amanak.2.mp4">
    <strong>▶ Watch Amanak Demo</strong>
  </a>
</p>

---

## Project status

Amanak is currently in an advanced MVP stage. The main application flow, backend services, authentication structure, database models, and content modules are implemented. The project uses mock government-style datasets for demonstration and portfolio purposes, with the architecture prepared to be connected to real institutional data sources in a production environment.

---

## Repository structure

```text
amanak-fullstack/
├── frontend/              # Amanak client application
├── backend/               # Spring Boot backend services
├── README.md
└── .gitignore
```

The repository is organized as a monorepo so the full product can be reviewed from one place: interface, backend, APIs, models, and configuration.

---

## Core features

- User registration and login flow
- Password hashing using Spring Security and BCrypt
- OTP-related service layer
- Account verification and user lookup logic
- Government-style posts and announcements
- Arabic and English content support structure
- Success stories module
- Case and message-related backend models
- REST API controllers and service-based backend design
- Repository layer using Spring Data patterns
- Environment-based configuration for sensitive values
- Mock dataset integration for realistic project demonstration

---

## Tech stack

### Frontend

- Mobile-first application structure
- Android Studio project workflow
- Client-side screens and assets for the Amanak experience
- Custom authentication support module

### Backend

- Java
- Spring Boot
- Spring Security
- BCrypt password encoding
- REST APIs
- Spring Data repositories
- Layered controller-service-repository architecture
- Externalized configuration through environment variables

### Security and configuration

Sensitive values are intentionally excluded from the repository. Database passwords, Firebase credentials, and private keys should be provided through environment variables or local secret files that are not committed to Git.

Example:

```properties
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
```

For Firebase or Google service account credentials, use a local environment variable instead of committing the JSON key:

```bash
GOOGLE_APPLICATION_CREDENTIALS=/path/to/service-account.json
```

---

## Backend overview

The backend follows a clean layered structure:

```text
Controller  ->  Service  ->  Repository  ->  Database
```

This keeps request handling, business logic, and data access separated. It also makes the backend easier to test, extend, and maintain.

Main backend responsibilities include:

- Authentication and authorization configuration
- User account management
- Login and password validation
- Content retrieval and publishing endpoints
- Success story APIs
- Case and message data handling
- Database entity modeling

---

## Why Amanak

Amanak was built as a practical fullstack project, not just a static prototype. It focuses on a real-world problem: improving the way citizens interact with public digital services.

The project demonstrates:

- Product thinking
- Backend architecture
- Secure authentication practices
- API design
- Database modeling
- Government-service workflow simulation
- Fullstack repository organization
- Readiness for future integration with real datasets

---

## Getting started

Clone the repository:

```bash
git clone https://github.com/Layansabha/amanak-fullstack.git
cd amanak-fullstack
```

### Run the backend

Go to the backend directory:

```bash
cd backend
```

Set the required environment variables, then run the Spring Boot application from your IDE or using Maven/Gradle depending on the project setup.

Example environment variable:

```bash
SPRING_DATASOURCE_PASSWORD=your_database_password
```

### Run the frontend

Open the `frontend` directory in Android Studio and run the application using an emulator or a connected Android device.

---

## Demo

A recorded walkthrough is available here:

[▶ Watch Amanak Demo](https://github.com/Layansabha/amanak-fullstack/releases/download/v1.0-demo/amanak.2.mp4)

The demo shows the application flow, user experience, and core MVP features.

---

## Future improvements

- Connect the mock dataset to real institutional data sources
- Add role-based dashboards for citizens and administrators
- Improve API documentation with Swagger/OpenAPI
- Add automated backend tests
- Add CI/CD pipeline for build validation
- Containerize backend services with Docker
- Add deployment documentation
- Improve monitoring and logging for production readiness

---

## Author

**Layan Sabha**  
Cybersecurity & DevOps

---

## Note

This repository is a portfolio-ready MVP that demonstrates how Amanak could operate as a scalable digital government services platform. It is not an official government system.