# Sprint 3 Planning – BookBuddy

**Duration:** Nov 18 – Dec 1, 2025  
**Team Members:**

- OJ Adeyemi — Frontend Developer / UI/UX Lead
- Sanae Faghfouri — Backend Developer / Integration Lead

---

## Sprint Goal

Finalize all core features of BookBuddy, complete the Monthly Tracker system end-to-end, implement Manual Book Entry and Reviews, resolve duplicate-book logic issues, polish UI components, and stabilize the entire application for the final demo.

This sprint is the final release sprint for the project.

---

## Selected User Stories for Sprint 3

| ID  | User Story                                           | Priority | Story Points | Status    |
| --- | ---------------------------------------------------- | -------- | ------------ | --------- |
| #11 | Rewrite MonthlyTracker for accurate monthly stats.   | High     | 8            | Completed |
| #12 | Rewrite MonthlyTrackerBook relations.                | High     | 5            | Completed |
| #13 | Manual book entry (API and UI).                      | Medium   | 5            | Completed |
| #14 | Prevent duplicate books in library.                  | High     | 3            | Completed |
| #15 | Review system finalization (fix mapping and add UI). | High     | 8            | Completed |
| #16 | Login flow fixes and homepage.                       | Medium   | 3            | Completed |
| #17 | Final system integration and cleanup.                | High     | 5            | Completed |

---

## Task Breakdown

| Task                       | Description                      | Assignee | Est. Hours | Status |
| -------------------------- | -------------------------------- | -------- | ---------- | ------ |
| Rewrite MonthlyTracker     | Rebuild entity and service logic | Sanae    | 2          | Done   |
| Rewrite MonthlyTrackerBook | Fix model and relationships      | Sanae    | 3          | Done   |
| Monthly aggregation        | Add monthly stats logic          | Sanae    | 1          | Done   |
| Duplicate-book validation  | Fix logic preventing duplicates  | Both     | 1          | Done   |
| Review mapping fix         | Resolve UserBook–Review issue    | OJ       | 3          | Done   |
| Monthly Tracker UI         | Build stats and list pages       | OJ       | 2          | Done   |
| Manual Book Entry UI       | Form and backend integration     | OJ       | 5          | Done   |
| Review UI                  | Add/update review components     | OJ       | 5          | Done   |
| Login/Home UI polish       | Fix flow and navigation          | OJ       | 3          | Done   |
| Final integration          | Connect all subsystems           | Both     | 4          | Done   |

---

## Definition of Done

A story is considered complete when:

- Backend logic compiles with no errors
- Swagger validates all endpoints
- UI communicates with backend correctly
- Review, manual entry, and tracker features work end-to-end
- Duplicate-book prevention works in backend and UI
- Demo flow is clean and stable
- Documentation is updated

---

## Sprint Timeline

**Week 1:**  
Backend: MonthlyTracker rewrite, Review mapping fix, duplicate-book fix.  
Frontend: Build Monthly Tracker UI, login polish, start manual book form.

**Week 2:**  
Frontend: Manual book entry UI, Review UI, homepage.  
Backend: Integration and DTO cleaning.  
Both: Final testing, bug fixes, and demo preparation.

---

## Risks and Mitigation

- Review mapping conflicts → resolved with relationship restructuring
- Duplicate-book issue → fixed with backend and UI validation
- Integration complexity → mitigated with daily syncs
- API changes → mitigated by updating DTOs and Swagger

---

## Sprint Deliverables

- Final Monthly Tracker (backend and UI)
- Manual Book Entry (backend and UI)
- Complete Review system
- Duplicate-book logic fix
- Stable login and homepage
- Final integrated system

**Demo Video:** [Watch Sprint 2 Demo](https://drive.google.com/file/d/1VvK5ev9ErAcV1qrnpe-Sc3F6Jyh3aS4J/view?usp=drive_link)
