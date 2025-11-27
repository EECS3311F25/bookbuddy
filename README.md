# BookBuddy

A simple personal reading tracker that helps users organize their book collection, track what they've read, and monitor reading goals.

## Motivation

Many readers struggle to keep track of their personal libraries, remember which books they've read, and maintain consistent reading habits. While existing solutions like Goodreads focus heavily on social features, BookBuddy is designed for readers who want a clean, personal reading tracker without the clutter.

**Problems BookBuddy Solves:**

- **Lost track of books**: Forgetting what you own or what you wanted to read next
- **Duplicate purchases**: Buying books you already have because you can't remember your collection
- **Reading motivation**: Lacking visual progress tracking to stay motivated
- **Information overload**: Getting overwhelmed by social features when you just want simple organization

**Why It Exists:**
BookBuddy prioritizes speed and simplicity. Users should be able to add a book in under 20 seconds, move books between shelves with a single click, and see their reading progress at a glance. It's built for casual readers and students who want personal organization, not social networking.

## Features

- 📚 **Personal Library**: Organize books into shelves (Want to Read, Currently Reading, Read)
- ⭐ **Ratings & Reviews**: Rate books 1-5 stars and write personal notes
- 🎯 **Reading Goals**: Set and track annual reading goals with visual progress
- 📅 **Monthly Tracker**: Track reading progress month by month with completion tracking
- 📖 **Custom Books**: Add books manually with custom titles, authors, and covers
- 🔍 **Book Search**: Search books using [Open Library API](https://openlibrary.org/developers/api) or add manually
- 📱 **Responsive Design**: Works seamlessly on desktop and mobile devices

## Tech Stack

### Frontend

- React 18 + TypeScript
- Vite (build tool with SWC)
- Responsive web design

### Backend

- Spring Boot 3.5.6 (Java 21)
- Spring Data JPA (ORM)
- Maven (build tool)

### Database

- **Development**: SQLite (local file-based database)
- **Production**: PostgreSQL (planned migration)

### Architecture

- **Pattern**: Model-View-Controller (MVC)
- **API**: RESTful JSON endpoints

## Project Structure

```
PROJECT/
├── frontend/              # React + TypeScript frontend
│   ├── src/
│   │   ├── components/   # UI components
│   │   │   ├── common/   # Shared components
│   │   │   ├── home/     # Home page components
│   │   │   ├── layout/   # Layout components
│   │   │   ├── library/  # Library page components
│   │   │   ├── search/   # Search components
│   │   │   ├── settings/ # Settings components
│   │   │   ├── tracker/  # Monthly tracker components
│   │   │   └── ui/       # Base UI components
│   │   ├── contexts/     # React contexts
│   │   ├── hooks/        # Custom React hooks
│   │   ├── pages/        # Page components
│   │   ├── services/     # API service layer
│   │   ├── types/        # TypeScript types
│   │   └── utils/        # Utility functions
│   └── README.md
│
├── backend/               # Spring Boot REST API
│   └── src/
│       ├── main/java/com/bookbuddy/
│       │   ├── config/       # Configuration classes
│       │   ├── controller/   # REST controllers
│       │   ├── dto/          # Data Transfer Objects
│       │   ├── model/        # JPA entities
│       │   ├── repository/   # Database repositories
│       │   └── service/      # Business logic
│       └── test/             # Unit tests
│
└── doc/
    ├── sprint0/          # Initial project documentation
    ├── sprint1/          # Sprint 1 deliverables
    ├── sprint2/          # Sprint 2 deliverables
    └── sprint3/          # Sprint 3 deliverables
```

## Installation

### Prerequisites

- Java 21+
- Node.js 18+
- Maven 3.6+
- npm 9+

### Quick Start

**Backend:**

```bash
cd backend
mvn clean install && mvn spring-boot:run
```

**Frontend:**

```bash
cd frontend
npm install && npm run dev
```

**For detailed setup instructions**, see:

- [Backend README](backend/README.md)
- [Frontend README](frontend/README.md)
- [Development Guide](DEVELOPMENT_GUIDE.md)

## Contribution

We use **Git Flow** with feature branches and pull requests.

> **IMPORTANT**: Always create a new branch before writing code. Never commit directly to `main`.

**See [DEVELOPMENT_GUIDE.md](DEVELOPMENT_GUIDE.md) for:**

- Complete Git workflow
- Branch naming conventions
- Pre-push checklist
- Pull request requirements
- Code review process

## Team

See [doc/sprint0/team.md](doc/sprint0/team.md) for team member information.

## Documentation

**Project Planning:**
- [Project Summary](doc/sprint0/summary.md) - Objectives, personas, scenarios
- [Product Backlog](doc/sprint0/PB.md) - User stories and priorities
- [Competition Analysis](doc/sprint0/competition.md) - Comparison with GoodReads

**Development:**
- [Backend README](backend/README.md) - API documentation and setup
- [Frontend README](frontend/README.md) - UI development guide
- [Development Guide](DEVELOPMENT_GUIDE.md) - Git workflow and contribution guidelines

**Sprints:**
- [Sprint 0](doc/sprint0/) - Initial planning and documentation
- [Sprint 1](doc/sprint1/) - Core features implementation
- [Sprint 2](doc/sprint2/) - Enhanced features and improvements
- [Sprint 3](doc/sprint3/) - Final features and polish

## License

This project is for educational purposes as part of EECS 3311 - Software Design.

## Support

For questions or issues:

1. Check existing documentation
2. Search GitHub Issues
3. Create a new issue with detailed description
4. Tag appropriate team members
