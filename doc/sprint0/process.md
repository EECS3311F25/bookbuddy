# Team Process

## Team Organization

**Team Size**: 2 members

**Roles**:

- **Sanae**: Primarily backend (Spring Boot, REST API, database)
- **OJ**: Full-stack (frontend React/TypeScript, backend integration, UI/UX)

Both members contribute across the stack when needed. This approach ensures knowledge sharing and prevents bottlenecks.

**Tools we use:**

- **GitHub**: Version control, pull requests, code reviews
- **Trello**: Track user stories and sprint progress
- **Discord**: Daily communication and quick questions
- **Git Flow**: Feature branch workflow (see [DEVELOPMENT_GUIDE.md](../../DEVELOPMENT_GUIDE.md))

---

## Decision Making

We make decisions through **consensus**:

1. Team member proposes an idea or approach (on Discord or during meetings)
2. Others share opinions or concerns
3. If no objections, we proceed
4. If disagreement exists, we discuss trade-offs and vote (majority wins)

**Technical decisions** (e.g., library choices, architecture patterns) are discussed in person or on Discord before implementation begins.

---

## Prioritizing User Stories

We prioritize stories based on **dependencies and user value**:

1. **High Priority**: Core features needed for MVP (authentication, book CRUD, shelves)
2. **Medium Priority**: Features that enhance usability (monthly tracker, ratings)
3. **Low Priority**: Nice-to-have features (dark mode, CSV export)

**Process:**

- Team reviews backlog together
- We identify which stories block other stories (e.g., authentication blocks everything)
- We discuss which features provide the most value to our key personas (Emily and Marcus)
- Stories are labeled High/Medium/Low priority in Trello
- **Voting**: Usually takes 1 round - we quickly agree on what's critical vs. optional

---

## Meetings

**Frequency**: 2 times per week (in-person or Discord call)

**Structure:**

- **Before Sprint**: Sprint planning (~30 min) - select stories, estimate effort, assign tasks
- **Mid-Sprint**: Progress check (~15 min) - blockers, help needed, adjust priorities
- **End of Sprint**: Sprint review (~20 min) - demo completed features, retrospective

**Async communication**: Daily updates in Discord for quick questions and status updates.

---

## Lessons for Next Phase

1. **Start Earlier**: We underestimated documentation time in Sprint 0
2. **Test Integration Sooner**: Set up backend-frontend connection early to catch CORS/API issues
3. **Clearer Story Breakdown**: Some user stories (e.g., "book management") are too broad - break them into smaller tasks
4. **Commit More Frequently**: Push work-in-progress branches daily to avoid large merge conflicts
5. **Time Management**: Balance feature work with testing and documentation throughout the sprint

---

**Overall**: Our process is lightweight and flexible. We focus on communication, quick feedback loops, and adapting as we learn what works.
