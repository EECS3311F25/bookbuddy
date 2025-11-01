# BookBuddy – Product Backlog

## Project Overview

BookBuddy is a personal reading tracker that helps users organize their book collection, monitor reading progress, and stay consistent with monthly goals.  
Unlike Goodreads, it focuses on personal progress rather than social reviews — featuring one main library with genre sections and a monthly reading tracker to promote goal-based reading.

---

## Epic 1: User Accounts and Authentication

| ID  | User Story                                                                                                                                       | Priority | Status      |
| --- | ------------------------------------------------------------------------------------------------------------------------------------------------ | -------- | ----------- |
| 1.1 | As Emily, I want to sign up and log in securely so that I can save my reading progress and access it from any device.                            | High     | Not Started |
| 1.2 | As Marcus, I want to reset my password if I forget it so that I can regain access to my book collection without losing data.                     | High     | Not Started |
| 1.3 | As Emily, I want my session to remain active until I log out so that I don't have to re-enter my credentials every time I check my reading list. | Medium   | Not Started |

---

## Epic 2: Book Management (CRUD)

| ID  | User Story                                                                                                                                                          | Priority | Status      |
| --- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------- | ----------- |
| 2.1 | As Emily, I want to add books by searching the Open Library API so that I can quickly add books to my shelves without manual data entry.                            | High     | Not Started |
| 2.2 | As Marcus, I want to manually add books that are not in the API database so that I can track rare or older books in my collection.                                  | High     | Not Started |
| 2.3 | As Marcus, I want to edit or delete any book I have added so that I can fix mistakes or remove duplicate entries.                                                   | High     | Not Started |
| 2.4 | As Emily, I want to view each book's details (title, author, genre, rating, and notes) so that I can remember what I thought about it and decide what to read next. | High     | Not Started |

---

## Epic 3: Library Organization and Book Statuses

| ID  | User Story                                                                                                                                                                                            | Priority | Status      |
| --- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------- | ----------- |
| 3.1 | As Emily, I want one main library that contains all my currently reading books so that I can see my active reading list at a glance.                                                                  | High     | Not Started |
| 3.2 | As Marcus, I want to organize books in my library by genre sections (e.g., Fiction, Classic, Romance, Fantasy) so that I can quickly browse and choose what to read based on my mood.                 | High     | Not Started |
| 3.3 | As Emily, I want to mark books as "Want to Read," "Currently Reading," or "Read" so that I can track my reading pipeline and progress.                                                                | High     | Not Started |
| 3.4 | As Emily, when I finish a book and mark it as "Read," it should automatically be removed from my main library and added to my Completed Books list so that my active library stays clean and focused. | High     | Not Started |
| 3.5 | As Marcus, I want to view a Completed Books page with all books I have finished, including ratings and notes, so that I can reference my reading history and avoid buying duplicates.                 | Medium   | Not Started |

---

## Epic 4: Monthly Reading Tracker

| ID  | User Story                                                                                                                                                            | Priority | Status      |
| --- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------- | ----------- |
| 4.1 | As Emily, I want to create a monthly reading list of books I plan to finish by the end of the month so that I can set achievable short-term goals and stay motivated. | High     | Not Started |
| 4.2 | As Emily, I want to mark books in the monthly tracker as "Read" or "Not Yet Read" so that I can track my progress toward my monthly goal.                             | High     | Not Started |
| 4.3 | As Emily, I want the tracker to display my monthly completion rate (e.g., "3 / 5 books read") so that I can see how close I am to reaching my goal.                   | Medium   | Not Started |
| 4.4 | As Marcus, I want my monthly tracker to reset automatically each month while keeping a history of past months so that I can review my reading consistency over time.  | Medium   | Not Started |

---

## Epic 5: Reviews, Notes, and Ratings

| ID  | User Story                                                                                                                                                      | Priority | Status      |
| --- | --------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------- | ----------- |
| 5.1 | As Marcus, I want to rate each completed book from one to five stars so that I can remember which books I loved and which I didn't enjoy.                       | Medium   | Not Started |
| 5.2 | As Marcus, I want to add personal notes or short reflections to a finished book so that I can capture my thoughts while they're fresh and reference them later. | Medium   | Not Started |
| 5.3 | As Emily, I want to view all my past ratings and notes together for reference so that I can recommend books to friends or choose similar books in the future.   | Medium   | Not Started |

---

## Epic 6: User Interface and Experience

| ID  | User Story                                                                                                                                                              | Priority | Status      |
| --- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------- | ----------- |
| 6.1 | As Marcus, I want a clean, minimal interface that focuses on simplicity so that I can accomplish tasks quickly without distractions or a steep learning curve.          | High     | Not Started |
| 6.2 | As Emily, I want a responsive design that works on both desktop and mobile so that I can manage my reading list from my phone at bookstores and from my laptop at home. | High     | Not Started |
| 6.3 | As Emily, I want the app to load pages and search results quickly so that I can add books in under 20 seconds without frustration.                                      | High     | Not Started |
| 6.4 | As Marcus, I want accessibility support such as keyboard navigation and ARIA labels so that I can navigate efficiently and the app follows web standards.               | Medium   | Not Started |

---

## Epic 7: Technical and Backend Implementation

| ID  | Technical Task                                                                            | Priority | Status      |
| --- | ----------------------------------------------------------------------------------------- | -------- | ----------- |
| 7.1 | Set up the Spring Boot REST API structure (controllers, services, repositories).          | High     | In Progress |
| 7.2 | Connect the SQLite database and create tables for users, books, and monthly tracker data. | High     | Not Started |
| 7.3 | Integrate the Open Library API for searching and fetching book data.                      | High     | Not Started |
| 7.4 | Configure CORS, authentication middleware, and session management.                        | Medium   | Not Started |
| 7.5 | Test and deploy backend endpoints locally using Postman.                                  | Medium   | Not Started |

---

## Epic 8: Future Enhancements

| ID  | Feature                                                    | Priority | Status |
| --- | ---------------------------------------------------------- | -------- | ------ |
| 8.1 | Add a dark/light mode toggle for user customization.       | Low      | Future |
| 8.2 | Enable import/export of user library data as CSV or JSON.  | Low      | Future |
| 8.3 | Integrate with Goodreads or Kindle highlights for syncing. | Low      | Future |
