# BookBuddy Frontend

React + TypeScript web app for BookBuddy.

## Tech Stack

- React 18
- TypeScript
- Vite
- SWC (fast compiler)

## Prerequisites

- Node.js 18+
- npm 9+

## Quick Start

### 1. Install Dependencies

```bash
cd frontend
npm install
```

Expected: Dependencies installed successfully

### 2. Run Dev Server

```bash
npm run dev
```

Frontend runs on `http://localhost:5173`

**Keep terminal open** - dev server must stay running.

### 3. Test It Works

Open browser: `http://localhost:5173`

Expected: React welcome page

**Stop server**: Press `Ctrl+C`

---

## Available Scripts

```bash
npm run dev        # Start dev server
npm run build      # Build for production
npm run preview    # Preview production build
npm run lint       # Run linter
```

---

## Before Pushing Code

Run these commands (all must pass):

```bash
npm test                   # Run tests
npm run build              # Verify build
npm run lint               # Check code style
npx prettier --write .     # Format code
```

---

## Connecting to Backend

Backend runs on `http://localhost:8080`

Make sure backend is running before making API calls.

---

## Common Issues

**Port 5173 in use:**
Change port in `vite.config.ts`:

```typescript
export default defineConfig({
  server: { port: 3000 },
});
```

**CORS errors:**
Check backend `application.properties` has:

```properties
spring.web.cors.allowed-origins=http://localhost:5173
```

---

## Next Steps (Sprint 1)

- [ ] Create book list component
- [ ] Add book form
- [ ] Implement routing (React Router)
- [ ] Add authentication UI
- [ ] Integrate Open Library search
- [ ] Create reading goal tracker
- [ ] Responsive design
