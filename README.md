# ⏰ RemindMe - Task Reminder App

A full-stack task reminder web application built with Java Spring Boot, HTML/CSS/JS, PostgreSQL, Docker, and deployed on AWS EC2.

## 🚀 Live Demo
http://54.242.45.11

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Frontend | HTML, CSS, JavaScript |
| Backend | Java 17, Spring Boot, Spring Security |
| Database | PostgreSQL |
| Auth | JWT Tokens |
| Containerization | Docker, Docker Compose |
| Deployment | AWS EC2, Elastic IP, Nginx |

## ✨ Features
- User registration and login with JWT authentication
- Create, edit, delete reminders
- Set priority (High, Medium, Low)
- Mark reminders as Done
- Filter by status and priority
- Data persists in PostgreSQL database

## 🏃 Run Locally

### Prerequisites
- Docker
- Docker Compose

### Steps
```bash
git clone https://github.com/YOUR_USERNAME/reminder-app.git
cd reminder-app
docker compose up --build
```
Open http://localhost in your browser.

## 📁 Project Structure
reminder-app/
├── backend/          # Java Spring Boot API
│   ├── src/          # Source code
│   ├── pom.xml       # Maven dependencies
│   └── Dockerfile
├── frontend/         # HTML/CSS/JS
│   └── index.html
├── nginx/            # Reverse proxy config
│   └── default.conf
└── docker-compose.yml

## 👨‍💻 Author
Fida Hussain Wani
