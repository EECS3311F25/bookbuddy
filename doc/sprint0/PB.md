# BookBuddy – Product Backlog

## Project Overview
BookBuddy is a personal reading tracker that helps users organize their book collection, monitor reading progress, and stay consistent with monthly goals.  
Unlike Goodreads, it focuses on personal progress rather than social reviews — featuring one main library with genre sections and a monthly reading tracker to promote goal-based reading.

---

## Epic 1: User Accounts and Authentication
| ID | User Story | Priority | Status |
|----|-------------|-----------|--------|
| 1.1 | As a user, I want to sign up and log in securely so that I can save my reading progress. | High | Not Started |
| 1.2 | As a user, I want to reset my password if I forget it. | High | Not Started |
| 1.3 | As a user, I want my session to remain active until I log out. | Medium | Not Started |

---

## Epic 2: Book Management (CRUD)
| ID | User Story | Priority | Status |
|----|-------------|-----------|--------|
| 2.1 | As a user, I want to add books by searching the Open Library API. | High | Not Started |
| 2.2 | As a user, I want to manually add books that are not in the API database. | High | Not Started |
| 2.3 | As a user, I want to edit or delete any book I have added. | High | Not Started |
| 2.4 | As a user, I want to view each book’s details (title, author, genre, rating, and notes). | High | Not Started |

---

## Epic 3: Library Organization and Book Statuses
| ID | User Story | Priority | Status |
|----|-------------|-----------|--------|
| 3.1 | As a user, I want one main library that contains all my currently reading books. | High | Not Started |
| 3.2 | As a user, I want to organize books in my library by genre sections (e.g., Fiction, Classic, Romance, Fantasy). | High | Not Started |
| 3.3 | As a user, I want to mark books as “Want to Read,” “Currently Reading,” or “Read.” | High | Not Started |
| 3.4 | As a user, when I finish a book and mark it as “Read,” it should automatically be removed from my main library and added to my Completed Books list. | High | Not Started |
| 3.5 | As a user, I want to view a Completed Books page with all books I have finished, including ratings and notes. | Medium | Not Started |

---

## Epic 4: Monthly Reading Tracker
| ID | User Story | Priority | Status |
|----|-------------|-----------|--------|
| 4.1 | As a user, I want to create a monthly reading list of books I plan to finish by the end of the month. | High | Not Started |
| 4.2 | As a user, I want to mark books in the monthly tracker as “Read” or “Not Yet Read.” | High | Not Started |
| 4.3 | As a user, I want the tracker to display my monthly completion rate (e.g., “3 / 5 books read”). | Medium | Not Started |
| 4.4 | As a user, I want my monthly tracker to reset automatically each month while keeping a history of past months. | Medium | Not Started |

---

## Epic 5: Reviews, Notes, and Ratings
| ID | User Story | Priority | Status |
|----|-------------|-----------|--------|
| 5.1 | As a user, I want to rate each completed book from one to five stars. | Medium | Not Started |
| 5.2 | As a user, I want to add personal notes or short reflections to a finished book. | Medium | Not Started |
| 5.3 | As a user, I want to view all my past ratings and notes together for reference. | Medium | Not Started |

---

## Epic 6: User Interface and Experience
| ID | User Story | Priority | Status |
|----|-------------|-----------|--------|
| 6.1 | As a user, I want a clean, minimal interface that focuses on simplicity. | High | Not Started |
| 6.2 | As a user, I want a responsive design that works on both desktop and mobile. | High | Not Started |
| 6.3 | As a user, I want the app to load pages and search results quickly. | High | Not Started |
| 6.4 | As a user, I want accessibility support such as keyboard navigation and ARIA labels. | Medium | Not Started |

---

## Epic 7: Technical and Backend Implementation
| ID | Technical Task | Priority | Status |
|----|----------------|-----------|--------|
| 7.1 | Set up the Spring Boot REST API structure (controllers, services, repositories). | High | In Progress |
| 7.2 | Connect the SQLite database and create tables for users, books, and monthly tracker data. | High | Not Started |
| 7.3 | Integrate the Open Library API for searching and fetching book data. | High | Not Started |
| 7.4 | Configure CORS, authentication middleware, and session management. | Medium | Not Started |
| 7.5 | Test and deploy backend endpoints locally using Postman. | Medium | Not Started |

---

## Epic 8: Future Enhancements
| ID | Feature | Priority | Status |
|----|----------|-----------|--------|
| 8.1 | Add a dark/light mode toggle for user customization. | Low | Future |
| 8.2 | Enable import/export of user library data as CSV or JSON. | Low | Future |
| 8.3 | Integrate with Goodreads or Kindle highlights for syncing. | Low | Future |

