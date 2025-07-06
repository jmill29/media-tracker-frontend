# TV Tracker Frontend – Cognixia Capstone Project [![Coverage](https://img.shields.io/badge/Coverage-80%25-brightgreen.svg)](https://github.com/jmill29/tvtrackerfrontend/actions)

This is a Java-based CLI frontend for my TV Tracker Capstone. It interacts with the backend API (**[tv-show-tracker-api](https://github.com/jmill29/tv-show-tracker-api)**) to register users, authenticate, and manage TV show watch histories.

---

## 📸 Demo Screenshot

> 🖼️ **Screenshot of the CLI in action**  

![Demo Screenshot](https://cognixia-capstone-resources.s3.us-east-1.amazonaws.com/demo.png)

---

## 📌 Project Links

- [**Backend API**](https://github.com/jmill29/tv-show-tracker-api)  
- [**Kanban Board**](https://github.com/users/jmill29/projects/3)  
- [**Javadoc HTML**](./docs/index.html)

---

## 🛠 Features

- **User Registration** and **Login** (basic auth)
- **View Shows** retrieved from the API
- **Manage Watch History**: Add, update, and remove shows
- **Clean CLI UI** with emojis and prompts
- **HttpClient + Gson** integration with DTOs
- **Comprehensive Test Coverage** (80%+ JaCoCo badge included)
- **Modular code**: `dto`, `service`, `ui`, `util`, `enums`

---

## 🔧 Getting Started

1. Clone frontend repo:
   ```bash
   git clone https://github.com/jmill29/tvtrackerfrontend.git
   cd tvtrackerfrontend
   ```

2. Ensure backend is running on `http://localhost:8080`

3. Build and run the CLI:
   ```bash
   mvn clean package
   java -jar target/tvtrackerfrontend-1.0-SNAPSHOT.jar
   ```

4. Follow prompts to Register, Login, and manage your watch history.

---

## 🚀 CLI Flow Overview

```
=== TV Tracker CLI ===
1. View All TV Shows
2. View Watch History
3. Add Show to Watch History
4. Update Watch Status
5. Delete a Show from Watch History
6. Logout
7. Exit
👉 Enter your choice here:
```

---

## 🔍 Example Usage

- **Register**: enter name, username, password, email  
- **Login**: enter credentials  
- **View Shows**: lists all shows with name, year, episodes, image URL  
- **Add**: select show ID + status (“Not Watched”, etc.)  
- **Update / Delete**: choose ID and action  
- Visual feedback via emojis and clear success/error messages

---

## 🧩 Next Steps

- Add a **browser-based frontend** using React or similar  
- Deploy both frontend and backend to cloud (Heroku, AWS, etc.)  
- Improve UI with better formatting or interactive elements

---

## 🙏 Thanks

Huge thanks to Cognixia for the comprehensive training and support—it’s been a fantastic learning journey, and this project reflects my ability to tackle full-stack development, testing, and documentation solo.

— Jacob Miller
