<div align="center">

# Amanak

### A mobile-first digital government services platform

Amanak is a fullstack MVP that reimagines how citizens access, track, and interact with government services through one clear mobile experience backed by a structured Spring Boot API.

<br />

<a href="https://layansabha.github.io/amanak-fullstack/"><strong>▶ Watch the Demo</strong></a>
&nbsp;&nbsp;•&nbsp;&nbsp;
<a href="#project-overview"><strong>Project Overview</strong></a>
&nbsp;&nbsp;•&nbsp;&nbsp;
<a href="#architecture"><strong>Architecture</strong></a>
&nbsp;&nbsp;•&nbsp;&nbsp;
<a href="#getting-started"><strong>Run Locally</strong></a>

<br />
<br />

![Status](https://img.shields.io/badge/status-Advanced%20MVP-2ea44f?style=for-the-badge)
![Type](https://img.shields.io/badge/type-Fullstack%20Project-0969da?style=for-the-badge)
![Backend](https://img.shields.io/badge/backend-Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Frontend](https://img.shields.io/badge/frontend-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Security](https://img.shields.io/badge/security-BCrypt%20%7C%20Env%20Config-black?style=for-the-badge)

</div>

---

## Project overview

Amanak was built around a simple idea: public services should feel organized, transparent, and reachable from one place.

Instead of forcing users to move between offices, phone calls, paper procedures, and unclear follow-ups, Amanak centralizes the experience into a mobile application connected to backend services. The platform demonstrates how a government-facing digital service product could handle authentication, user accounts, service-related content, messages, cases, and government-style datasets through a clean fullstack architecture.

This repository is structured as a **portfolio-ready monorepo** containing both the client application and the backend implementation.

---

## What Amanak solves

| Challenge | Amanak approach |
|---|---|
| Fragmented service experience | One mobile-first interface for the main user flow |
| Unclear citizen follow-up | Structured backend models for accounts, messages, posts, and cases |
| Weak demo realism | Mock government-style data prepared for presentation and testing |
| Security-sensitive workflows | Hashed authentication flow and environment-based configuration |
| Hard-to-review projects | Frontend, backend, documentation, and demo are organized in one repository |

---

## Demo

A recorded walkthrough is available through GitHub Pages:

<div align="center">

### [▶ Watch Amanak Demo](https://layansabha.github.io/amanak-fullstack/)

</div>

The demo presents the application flow, the user experience, and the core MVP features from a reviewer-friendly perspective.

---

## Core modules

### Mobile client

- Mobile-first user experience
- Application screens and assets for the Amanak flow
- Client-side structure prepared for backend-connected features
- Custom authentication support module

### Backend API

- User registration and login flow
- Password hashing with BCrypt
- User account lookup and validation
- OTP-related service layer
- Government-style posts and announcements
- Arabic and English content structure
- Success stories module
- Case and message-related data models
- REST controllers, services, and repositories

### Project delivery

- Monorepo organization
- Demo video page through GitHub Pages
- Environment-based configuration handling
- Mock dataset usage for safe demonstration

---

## Architecture

```text
Mobile App
   │
   ▼
REST API Layer
   │
   ▼
Spring Boot Controllers
   │
   ▼
Service Layer
   │
   ▼
Repository Layer
   │
   ▼
Database / Mock Government-Style Dataset
```

The backend follows a layered design so request handling, business logic, and data access stay separated. This makes the project easier to extend, test, and explain during technical reviews.

---

## Repository structure

```text
amanak-fullstack/
├── frontend/                     # Amanak mobile client
│   └── custom-auth-server/        # Custom authentication support
│
├── backend/                      # Backend workspace
│   └── amanakk-backend/           # Spring Boot API implementation
│
├── docs/                         # GitHub Pages demo player
│   ├── index.html
│   └── assets/
│       └── amanak-demo.mp4
│
├── README.md
└── .gitignore
```

---

## Tech stack

| Layer | Tools / Technologies |
|---|---|
| Mobile client | Android Studio project workflow |
| Backend | Java, Spring Boot |
| Security | Spring Security, BCrypt password encoding |
| API style | REST controllers and service-based backend design |
| Data access | Repository pattern / Spring Data style structure |
| Configuration | Environment-based local configuration |
| Demo delivery | GitHub Pages |
| Version control | Git + GitHub monorepo |

---

## Security decisions

Security-sensitive values are intentionally kept out of the repository. The project is prepared to read local configuration from the environment instead of committing machine-specific values.

The repository excludes:

- Local environment files
- Service account files
- Generated build outputs
- IDE metadata
- Runtime logs

---

## Getting started

Clone the repository:

```bash
git clone https://github.com/Layansabha/amanak-fullstack.git
cd amanak-fullstack
```

### Backend

Go to the backend project directory:

```bash
cd backend/amanakk-backend
```

Set the required local environment variables for your machine, then run the Spring Boot application from your IDE or through the project build tool if configured locally.

### Frontend

Open the frontend project in Android Studio:

```text
frontend/
```

Run it using an Android emulator or a connected Android device.

### Demo page

The demo player is served from:

```text
docs/index.html
```

Published page:

```text
https://layansabha.github.io/amanak-fullstack/
```

---

## What reviewers should notice

This project is not just a UI mockup. It demonstrates a complete product direction with backend implementation and security-aware configuration.

Key points to review:

- Clear separation between frontend and backend
- Real backend models, controllers, repositories, and services
- Authentication and password hashing practices
- Mock government-style dataset prepared for safe presentation
- Demo-first presentation through GitHub Pages
- Repository structured for portfolio and technical review

---

## Current status

Amanak is approximately **90% complete as an MVP**.

Implemented and prepared:

- Main user-facing flow
- Backend service structure
- Authentication-related classes
- Government-style content modules
- Demo video presentation
- Repository documentation

Remaining improvements are mostly production-readiness tasks such as deployment, automated tests, monitoring, and real institutional data integration.

---

## Disclaimer

Amanak is a portfolio MVP that demonstrates how a digital government services platform could be designed and implemented. It is not an official government system.
