# Sprint 1 Retrospective (SR1.md)

**Team Name:** BookBuddy  
**Sprint Duration:** 2 weeks  
**Date:** Nov 3, 2025 
**Participants:** O.J., Sanae F. 

---

## Participants in the Meeting
All team members attended.  
- **Sanae** explained the data model and relationships between entities (`BookCatalog`, `UserBook`, and `MonthlyTrackerBook`).  
- **O.J.** demonstrated the **Swagger API testing**, showing that all backend endpoints for book search, user registration, and catalog updates were functional.  

---

## Unfinished Tasks → to be continued in Sprint 2
### Story Group: Monthly Tracker and Review Feature + Testing 
**As a user**, I want to set monthly reading goals and link books to them **so that** I can track my progress.  
- Implement `MonthlyTracker` and `MonthlyTrackerBook` entities.  
- Add `MonthlyTrackerController`.  
- Connect `UserBook` with `MonthlyTrackerBook`.  

**As a user**, I want to write reviews and rate books **so that** I can share feedback.  
- Implement `Review` entity and controller.  
- Add rating and text review endpoints.  
- Display reviews in `BookCatalog`.  

These new stories will be added to `PB.md` for Sprint 2 under `/doc/sprint2`.

---

## Practices to Continue
- Use **Swagger** to validate and debug API endpoints early in the sprint.  
- Conduct team debugging sessions before merging branches.  
- Maintain active communication on **Trello** and **GitHub**.  
- Keep a consistent **branch-per-task** workflow.  
- Continue documenting entity logic clearly before implementation.  

---

## New Practices to Try
- Add short **integration sessions** between backend and frontend teams.  
- Include **story point estimation** for better capacity tracking.  
- Perform **automated Swagger testing** or export **Postman collections** for quick regression testing.  
- Introduce **daily short stand-ups** to report blockers faster.  

---

## Practices to Stop / Avoid
- Avoid leaving Swagger setup and debugging for the last few hours; test endpoints as soon as they’re added.  
- Don’t push directly to shared branches — use feature branches with pull requests for every task.  

---

## Best and Worst Experiences
**Best:** Getting Swagger fully functional after debugging for ~3 hours — seeing all endpoints working and documented felt rewarding and validated our design.  
**Worst:** Swagger configuration consumed extra time, delaying testing of smaller modules. Balancing coding, debugging, and documentation near the deadline was difficult.  

---

## Summary
Sprint 1 built a solid backend base for BookBuddy, tested successfully through Swagger. In Sprint 2, the team will expand with MonthlyTracker, Review features, and front-end integration while improving test automation and earlier validation.  
