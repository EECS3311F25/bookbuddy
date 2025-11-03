# Sprint 1 Planning – BookBuddy
**Duration:** Oct 20 – Nov 3, 2025  
**Team Members:**  

- OJ Adeyemi (Frontend Developer)
- Sanae Faghfouri (Backend Developer)  


---

## 🎯 Sprint Goal
Deliver a complete **backend API** for BookBuddy that supports user and book management through the **Service** and **Controller** layers.

This sprint focuses on building a stable backend structure. Testing of backend features and frontend integration will be completed during Sprint 2.

---

## 📋 Selected User Stories for Sprint 1

| ID | User Story | Priority | Story Points | Status |
|----|-------------|-----------|---------------|--------|
| #1 | As a user, I can register, update, and delete my profile via API. | High | 5 | Completed |
| #2 | As a user, I can add and view books in my personal library. | High | 8 | Completed |
| #3 | As a user, I can view the book catalog through an endpoint. | Medium | 5 | Completed |
| #4 | As a user, I can set and view monthly reading goals. | Medium | 3 | In Progress |
| #5 | As a user, I can rate and review books. | Low | 2 | To Do |

---

## 🧱 Tasks Breakdown

| Task | Description | Assigned To | Est. Hours |
|------|--------------|--------------|-------------|
| 1 | Implement Model classes (`User`, `BookCatalog`, `UserBook`, `Review`, `MonthlyTrackerBook`). | Sanae | 4 |
| 2 | Create Repositories for each entity. | Sanae | 3 |
| 3 | Develop Service Layer (`UserService`, `BookCatalogService`, etc.). | Sanae | 4 |
| 4 | Build Controller Layer to expose REST endpoints for users and books. | Sanae | 4 |
| 5 | Write CRC Cards and System Design Document (`system_design.md`). | Sanae | 3 |
| 6 | Prepare demo video showing two working backend features (Postman demo). | OJ | 2 |

**Total Estimated Capacity:** ≈ 20 hours over 2 weeks  

---

## 📅 Sprint Timeline

| Week | Focus |
|------|--------|
| Week 1 | Model and Repository implementation + Database configuration. |
| Week 2 | Service and Controller layers + Documentation + Video Demo. |

---

## Expected Deliverables
- Fully functional backend (models + repositories + services + controllers)  
- Connected database with working CRUD operations  
- CRC Cards and System Design Document
- 3-minute demo video showing API features (Postman demo)

---

## Risks / Blockers
- Possible data validation issues in entity relationships  
- Limited time for final video and documentation before deadline  
- Database connection or mapping errors between entities  

---

## Participants and Roles

| Name | Role | Responsibilities | Contact |
|------|------|------------------|----------|
| OJ | Frontend Developer | Begin UI phase in Sprint 2 (HTML, CSS, JS) and connect frontend to controllers. |
| Sanae |Backend Developer | Implement models, repositories, services, controllers and documentation; lead backend testing next sprint. |


---

## 🔜 Next Steps (For Sprint 2)
- **OJ:** Develop and connect UI components to backend controllers.  
- **Sanae:** Perform backend testing (Postman + JUnit), bug fixes, and validation checks.  
- Add REST error handling and response messages.  
- Prepare demo for full backend + UI integration.
