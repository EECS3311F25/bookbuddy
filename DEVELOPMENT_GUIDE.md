# Development Guide

Quick guide for contributing to BookBuddy.

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

- `main` - Production code (never commit directly here)
- `develop` - Integration branch
- `feature/<issue>-<name>` - New features
- `bugfix/<issue>-<name>` - Bug fixes

**Example:** `feature/23-add-reading-goals`

---

## Before You Start Coding

**1. Create a branch:**

```bash
git checkout develop
git pull origin develop
git checkout -b feature/23-add-reading-goals
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
git commit -m "feat: add reading goals (#23)"
git push origin feature/23-add-reading-goals
```

**5. Create Pull Request:**

- Go to GitHub
- Create PR from your branch → `develop`
- Request review from teammate
- Merge after approval

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
