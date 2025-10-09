# BookBuddy Backend

Spring Boot REST API for BookBuddy.

> **Sprint 0 Note**: This is a basic skeleton to demonstrate MVC connectivity. Full features coming in Sprint 1+.

## Tech Stack

- Java 21
- Spring Boot 3.3.0
- Maven
- SQLite (local database)
- Spring Data JPA

## Prerequisites

- Java 21+
- Maven 3.6+

## IDE Setup

### Using Eclipse

1. **Import Project**:

   - `File` → `Import` → `Existing Maven Projects`
   - Select `backend` folder
   - Click `Finish`

2. **Run Server**:
   - Right-click project → `Run As` → `Spring Boot App`
   - Or: Right-click `Application.java` → `Run As` → `Java Application`

### Using IntelliJ IDEA

1. **Open Project**: `File` → `Open` → Select `backend` folder
2. **Run Server**: Right-click `Application.java` → `Run`

### Using VS Code

1. **Install Extensions**: Java Extension Pack, Spring Boot Extension Pack
2. **Open `backend` folder**
3. **Run**: Click `Run` above `main()` method in `Application.java`

### Using Terminal (Any IDE)

Works with any editor (Eclipse, IntelliJ, VS Code, or just a text editor):

```bash
cd backend
mvn spring-boot:run
```

## Quick Start

### 1. Install Dependencies

```bash
cd backend
mvn clean install
```

Expected: `BUILD SUCCESS`

### 2. Run Server

```bash
mvn spring-boot:run
```

Server runs on `http://localhost:8080`

**Keep terminal open** - server must stay running.

### 3. Test It Works

Open new terminal:

```bash
curl http://localhost:8080/api/books
```

Expected: `[]` (empty array = working!)

**Stop server**: Press `Ctrl+C`

---

## Before Pushing Code

Run these commands (all must pass):

```bash
mvn test              # Run tests
mvn clean install     # Verify build
mvn spring-boot:run   # Verify server starts
```

---

## Database

- **SQLite** used for development
- Database file: `bookbuddy.db` (auto-created on first run)
- Each team member has their own local database
- Schema auto-generated from Java entities

**For production (later):** Migrate to PostgreSQL - see comments in `application.properties`

---

## Common Issues

**Port 8080 in use:**

```bash
lsof -ti:8080 | xargs kill -9
```

**Database locked:**
Delete `bookbuddy.db` and restart server.

---

## Next Steps (Sprint 1)

- [ ] Implement JWT authentication
- [ ] Add user registration/login
- [ ] Integrate Open Library API
- [ ] Add reading goals
- [ ] Write unit tests
