## BookBuddy - Project Summary

### Project Objective

Build a simple personal reading tracker that helps users organize their book collection, track what they've read, and monitor reading goals. The app should let users quickly add books to shelves (Want to Read, Currently Reading, Read), rate and review finished books, and see their reading progress at a glance.

---

### Key Personas

**Emily - The Motivated Reader**
27-year-old graduate student who loves reading fiction in her spare time. Sets a goal to read 30 books per year but often forgets what she's already read or wanted to read next. Needs a simple way to track her progress without getting overwhelmed by social features. Checks the app on her phone before bed and on weekends when browsing bookstores.

**Marcus - The Casual Book Collector**
35-year-old full-stack software developer with 200+ books on his shelves at home. Frequently buys duplicate books because he can't remember what he owns. Wants to quickly search his personal library and keep notes on books he's finished. Prefers clean interfaces over feature-heavy apps.

---

### Key Scenarios

**Scenario 1: Adding a New Book**
Emily is at a bookstore and sees a recommendation for "The Midnight Library." She pulls out her phone, opens BookBuddy, searches the title, and taps "Add to Want to Read" in under 20 seconds. The book appears on her shelf instantly.

**Scenario 2: Finishing a Book**
Marcus finishes reading "Project Hail Mary" late Sunday night. He opens BookBuddy on his laptop, moves it from "Currently Reading" to "Read," gives it 5 stars, and types a quick note: "Best sci-fi I've read this year." His annual goal counter updates from 11/25 to 12/25 books. He feels accomplished seeing the progress bar fill up.

**Scenario 3: Choosing What to Read Next**
Emily opens her "Want to Read" shelf on Saturday morning using her phone. She has 18 books listed. She sorts by date added, sees "The Night Circus" has been there for 3 months, and decides to start it. She moves it to "Currently Reading" and starts her weekend.

**Scenario 4: Adding a Book Manually**
Marcus receives a rare 1995 programming book as a gift from a colleague. He searches for it in BookBuddy but it's not in the Open Library database. He clicks "Add Manually," fills in the title and author, and saves it to his "Want to Read" shelf in 30 seconds.

---

### Key Principles

**Simplicity over Features**
Every interaction should be obvious without instructions. If a feature adds complexity, cut it. Users should accomplish tasks in 3 clicks or less.

**Speed Matters**
Pages load instantly, searches return results fast, and adding books takes seconds. No waiting, no friction.

**Progress is Motivating**
Visual progress indicators (goal bars, completed counts, reading stats) keep users engaged and coming back. Celebrate small wins.

---

### Technical Implementation

- **Frontend:** React + Vite (responsive web app)
- **Backend:** Spring Boot REST API (Java 21)
- **Database:** SQLite (development only)
- **Architecture:** MVC pattern
- **Book Data:**
  - Primary: Open Library Search API (free, paginated results)
  - Fallback: Manual book entry form
  - All books stored in local SQLite database

### Scope Boundaries

**In Scope:** User auth, book CRUD, shelves, reviews, ratings, paginated book search (Open Library API), reading goals, responsive design

**Out of Scope:** Social features, automated book recommendations, discussion forums, native mobile apps, production deployment, production-grade databases (PostgreSQL/MySQL)
