# Development Guide

Quick guide for contributing to BookBuddy.

---

## IDE Setup (Eclipse - Backend Only)

**Prerequisites:**

- Install [Eclipse IDE for Enterprise Java and Web Developers](https://www.eclipse.org/downloads/packages/)
- Install Java 21+
- Maven comes bundled with Eclipse

**Setup Steps:**

1. **Import Project:**

   - File → Import → Maven → Existing Maven Projects
   - Select the `backend` folder
   - Click Finish

2. **Run Backend:**

   - Right-click `backend` project → Run As → Spring Boot App
   - Backend runs at `http://localhost:8080`

3. **Run Tests:**
   - Right-click `backend` → Run As → Maven test

---

## Eclipse Git Workflow

**Create and work on a feature branch:**

1. **Create branch:**

   - Right-click project → Team → Switch To → New Branch
   - Name: `feat/your-feature-name`
   - Click Create

2. **Edit files:**

   - Make your code changes in Eclipse
   - Save files (Ctrl/Cmd + S)

3. **Commit changes:**

   - Right-click project → Team → Commit
   - Check files to include
   - Write commit message: `feat: description of changes`
   - Click "Commit and Push"
   - (First time: Enter GitHub credentials)

4. **Create Pull Request:**
   - Go to GitHub repository in browser
   - Click "Compare & pull request" button
   - Request review from teammate
   - Merge after approval

**Pull latest changes:**

- Right-click project → Team → Pull

**Switch branches:**

- Right-click project → Team → Switch To → [branch name]

---

## Running the Project

**Backend:**

```bash
cd backend
mvn clean install && mvn spring-boot:run
```

Backend: `http://localhost:8080`

**Frontend:**

```bash
cd frontend
npm install && npm run dev
```

Frontend: `http://localhost:5173`

See [backend/README.md](backend/README.md) and [frontend/README.md](frontend/README.md) for details.

---

## Git Workflow

**Branches:**

- `main` - Stable working code (protected - no direct commits)
- `feat/<name>` or `fix/<name>` - Feature/fix branches

**Examples:** `feat/reading-goals`, `fix/login-bug`

---

## Before You Start Coding

**1. Create a branch:**

```bash
git checkout main
git pull origin main
git checkout -b feat/reading-goals
```

**2. Work on your feature**

**3. Before pushing, run checks:**

Backend:

```bash
cd backend
mvn test
mvn clean install
```

Frontend:

```bash
cd frontend
npm test
npm run build
npm run lint
npx prettier --write .
```

**4. Commit and push:**

```bash
git add .
git commit -m "feat: add reading goals feature"
git push -u origin feat/reading-goals
```

**5. Create Pull Request:**

- Go to GitHub
- Create PR from your branch → `main`
- Request review from teammate
- Merge after approval
- Delete branch after merging

---

## Pull Request Checklist

Before submitting PR:

- [ ] Tests pass
- [ ] Code compiles
- [ ] Code is formatted
- [ ] No merge conflicts

---

## GitHub Issues

- Create issue for each task
- Use labels: `bug`, `enhancement`, `sprint-1`
- Link issue in PR (e.g., "Closes #23")
