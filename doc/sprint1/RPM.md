# Release Planning Meeting (RPM.md)

**Project:** BookBuddy  
**Course:** EECS 3311 – Software Design  
**Team Members:**  
- Sanae Faghfouri — Backend Developer / Testing Lead  
- OJ Adeyemi — Frontend Developer / UI Designer

---

## Release Goal
Develop **BookBuddy**, a personal reading-tracker application that allows users to manage their library, review books, and set monthly reading goals.  
By the end of Sprint 3, BookBuddy will have a functional backend connected to an interactive UI for managing users, books, and reading progress.

---

## System Scope (Epics / Key Features)

| Epic | Description |
|------|--------------|
| User Accounts | Register, log in, update profile, manage credentials |
| Book Catalog | View and search books from the catalog |
| User Library | Add and remove personal books, mark reading status |
| Reviews & Ratings | Submit and display book reviews |
| Monthly Tracker | Set and monitor monthly reading goals |
| UI & Dashboard | Web interface to interact with backend APIs |

---

## Sprint Overview

### Sprint 0 – Project Setup
**Goal:**  
Identify project objectives, roles, and technologies.  
Create personas, backlog, and initial GitHub/Trello setup.

**Deliverables:**  
- Personas.pdf  
- Product Backlog (PB.md)  
- Competition.md and Process.md  
- Initial README and Trello board

---

### Sprint 1 – Backend Foundation
**Goal:**  
Develop the backend foundation including Models, Repositories, Services, and Controllers.  
Establish the database connection and prepare documentation.

**Deliverables:**  
- Model, Repository, and Service classes  
- Controller layer exposing CRUD endpoints  
- CRC Cards and System Design Document  
- `RPM.md` and `sprint1.md`  
- Demo video showing backend API through Postman  

**Team Division:**  
- Sanae: Backend development (Models, Repositories, Services, Controllers)  
- Oj: Initial UI planning and wireframes  

---

### Sprint 2 – UI Integration and Backend Testing
**Goal:**  
Integrate the backend with a basic UI and begin backend testing.

**Deliverables:**  
- Frontend components (HTML, CSS, JS) connected to controllers  
- Backend testing with Postman and JUnit  
- Demo video with new features and bug fixes  

**Team Division:**  
- Sanae: Backend testing, debugging, and API validation  
- Oj: Frontend implementation and API connection  

**Planned Features:**  
1. User registration and profile update via UI  
2. Add/view personal books through UI  
3. Review and Monthly Tracker endpoints integrated  

---

### Sprint 3 – Full UI and Release 1
**Goal:**  
Finalize the complete product with all features integrated, tested, and visually refined.

**Deliverables:**  
- Finalized UI and connected backend  
- Testing and bug fixes  
- Demo video summarizing new and previous features  
- Peer evaluation submission  

**Team Division:**  
- Sanae: Final backend testing and documentation updates  
- Oj: UI polish, responsive layout, and presentation preparation  

**Planned Features:**  
1. Dashboard for reading statistics and progress tracking  
2. Enhanced error handling and UI responsiveness  
3. Final release preparation and documentation updates  

---

## Participants

| Name | Role | Responsibilities |
|------|------|------------------|
| **Sanae Faghfouri** | Backend Developer / Testing Lead | Develop backend logic, manage documentation, conduct backend testing. |
| **OJ Adeyemi** | Frontend Developer / UI Designer | Design and implement UI, connect frontend to backend controllers. |

---

## Tools and Environment
- **Backend:** Java 21, Spring Boot 3, Maven  
- **Database:** MySQL  
- **Frontend:** HTML, CSS, JavaScript  
- **IDE:** Eclipse / VS Code  
- **Version Control:** Git + GitHub  
- **Tracking:** Trello  
- **Testing:** Postman, JUnit 5  


---

## Release Vision
By Sprint 3, BookBuddy will provide a complete, functional backend and a user-friendly web interface for managing books, reviews, and reading goals — representing the project’s first stable release.
