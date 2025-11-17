# Sprint 2 Standups – BookBuddy  
**Team Members:** OJ Adeyemi, Sanae Faghfouri  
**Sprint Duration:** Nov 4 – Nov 18, 2025  

---

## 🟩 Standup 1 — November 12, 2025

### Participants  
- **OJ Adeyemi**  
- **Sanae Faghfouri**  

---

### 1. What did you work on since the last stand-up?

#### **OJ:**  
- Implemented the full **Search UI** (search input, results grid, book cards).  
- Mapped OpenLibrary results into a **consistent search result schema** used across all UI layers.  
- Added **Auth Context** to manage global authentication state and persist user sessions.  
- Debugged multiple UI state and fetch-related issues.

#### **Sanae:**  
- Completed backend entities: **MonthlyTracker**, **MonthlyTrackerBook**, and **Review**.  
- Implemented all corresponding **Service Layers**.  
- Tested backend endpoints with **Swagger** to ensure all controllers were working.  
- Verified correct linking between all backend layers (Model → Repository → Service → Controller).

---

### 2. What do you commit to next?

#### **OJ:**  
- Finish connecting Search UI to backend search endpoints.  
- Begin building the Settings/Profile UI and its flows.

#### **Sanae:**  
- Write complete **JUnit tests** for all backend services.  
- Debug backend logic found through Swagger and JUnit testing.

---

### 3. When do you think you’ll be done?
- Search UI + initial settings page: **end of this week**.  
- Full backend testing suite: **early next week**.

---

### 4. Do you have any blockers?
- No major blockers at this stage.  
- Need to finalize backend–frontend DTO alignment for search results.

---

---

## 🟩 Standup 2 — November 18, 2025 (After Class)

### Participants  
- **OJ Adeyemi**  
- **Sanae Faghfouri**

---

### 1. What did you work on since the last stand-up?

#### **OJ (major contributor for Sprint 2 UI work):**  
- Completed the full **Settings Page**, including:  
  - Profile editing  
  - Password change  
  - Account deletion  
  - Notifications toggle  
  - About dialog  
- Fully integrated **Search UI** with backend APIs.  
- Improved visual UI (added book covers, spacing, responsiveness).  
- Debugged authentication flow and fixed multiple UI validation errors.  
- Removed unused code and resolved multiple lint warnings.

#### **Sanae:**  
- Completed **all JUnit service tests** for:  
  - UserService  
  - BookCatalogService  
  - ReviewService  
  - UserBookService  
  - MonthlyTrackerService  
  - MonthlyTrackerBookService  
- Performed full backend debugging using **Swagger + JUnit**.  
- Ensured consistent API responses for frontend integration.  
- Verified that all backend layers function correctly end-to-end.

---

### 2. What do you commit to next?

#### **OJ:**  
- Final UI polishing and ensuring smooth routing between pages.  
- Prepare frontend portion of Sprint 2 demo.

#### **Sanae:**  
- Complete Sprint 2 documentation (standups, PB updates, SR2, UML).  
- Prepare backend explanation for the demo video.

---

### 3. When do you think you’ll be done?
- All Sprint 2 development: **end of this week**.  
- Demo: recorded once UI + backend are merged.

---

### 4. Do you have any blockers?
- No blockers — system is stable.  
- Only final polishing and documentation remain.

---

