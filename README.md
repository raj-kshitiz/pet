# Project Overview: PET (Personal Expense Tracker)

This project is a modern, full-stack application designed to help users manage their finances effectively. It features a robust Spring Boot backend and a responsive React frontend.

## 🚀 Features

- **Expense Tracking:** Log and monitor daily expenditures with ease.
- **Smart Filtering:** Filter expenses by specific timeframes (days) or categories.
- **Responsive UI:** A clean, intuitive dashboard built for seamless desktop and mobile use.
- **Secure Backend:** RESTful API architecture powered by Spring Boot and PostgreSQL.

## 🛠️ Tech Stack

### Backend
- **Framework:** Spring Boot 3
- **Language:** Java 21
- **Database:** PostgreSQL
- **ORM:** Spring Data JPA
- **Utilities:** Lombok

### Frontend
- **Library:** React (Vite)
- **Styling:** Vanilla CSS / Modern UI Patterns
- **API Interaction:** Native Fetch API

---

## 🏁 Getting Started

### Prerequisites
- JDK 21
- Node.js (v18+)
- PostgreSQL

### 1. Backend Setup
1. Navigate to the root directory.
2. Update `src/main/resources/application.properties` with your PostgreSQL credentials.
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

### 2. Frontend Setup
1. Navigate to the frontend directory:
   ```bash
   cd pet-frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm run dev
   ```

---

## 📁 Project Structure

```text
PET/
├── src/main/java/      # Spring Boot Backend source
├── pet-frontend/       # React Frontend source
├── pom.xml             # Maven dependencies
└── ...
```
