package com.bookbuddy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


class MonthlyTrackerTest {

    private User user;
    private MonthlyTracker tracker;

    @BeforeEach
    void setUp() {
        user = new User("John", "Doe", "john123", "john@example.com", "Password123");
        tracker = new MonthlyTracker(user, Months.NOVEMBER);
        tracker.setTargetBooksNum(5);
    }

    /** 1. Normal behavior: constructor, getters, adding books */
    @Test
    void testNormalBehavior() {
        assertEquals("john123", tracker.getUser().getUsername());
        assertEquals(Months.NOVEMBER, tracker.getMonth());
        assertEquals(String.valueOf(LocalDate.now().getYear()), tracker.getYear());
        assertEquals(5, tracker.getTargetBooksNum());

        // Add a book to tracker
        BookCatalog bc = new BookCatalog("Dune", "Frank Herbert");
        UserBook ub = new UserBook(user, bc, ShelfStatus.WANT_TO_READ);

        tracker.addToTracker(ub);
        assertEquals(1, tracker.getGoalBooks().size());
    }

    /** 2. Null behavior: setting fields to null and adding null books */
    @Test
    void testNullBehavior() {
        tracker.setUser(null);
        assertNull(tracker.getUser());

        tracker.setMonth(null);
        assertNull(tracker.getMonth());

        tracker.addToTracker(null);
        assertEquals(0, tracker.getGoalBooks().size());
    }

    /** 3. Invalid behavior: wrong user + READ books should not be added */
    @Test
    void testInvalidBehavior() {
        User otherUser = new User("Alice", "Smith", "alice88", "alice@example.com", "AlicePass22");
        BookCatalog bc = new BookCatalog("Dune", "Frank Herbert");

        // Wrong user's book
        UserBook wrongUserBook = new UserBook(otherUser, bc, ShelfStatus.WANT_TO_READ);
        tracker.addToTracker(wrongUserBook);
        assertEquals(0, tracker.getGoalBooks().size());

        // READ books cannot be added
        UserBook readBook = new UserBook(user, bc, ShelfStatus.READ);
        tracker.addToTracker(readBook);
        assertEquals(0, tracker.getGoalBooks().size());
    }

    /** 4. Edge cases: removing non-existing books, adding multiple entries */
    @Test
    void testEdgeCases() {
        BookCatalog bc = new BookCatalog("Dune", "Frank Herbert");
        UserBook ub1 = new UserBook(user, bc, ShelfStatus.CURRENTLY_READING);
        UserBook ub2 = new UserBook(user, bc, ShelfStatus.WANT_TO_READ);

        tracker.addToTracker(ub1);
        tracker.addToTracker(ub2);
        assertEquals(2, tracker.getGoalBooks().size());

        // Remove a book not in the list
        UserBook fakeBook = new UserBook(user, new BookCatalog("1984", "George Orwell"), ShelfStatus.WANT_TO_READ);
        tracker.removeFromTracker(fakeBook);
        assertEquals(2, tracker.getGoalBooks().size());
    }

    /** 5. Relationship behavior: tracker should keep books tied to correct user */
    @Test
    void testRelationshipBehavior() {
        BookCatalog bc = new BookCatalog("Dune", "Frank Herbert");
        UserBook ub = new UserBook(user, bc, ShelfStatus.WANT_TO_READ);

        tracker.addToTracker(ub);

        assertEquals(user, tracker.getGoalBooks().get(0).getUserBook().getUser());
    }

    /** 6. Formatting behavior: toString must show correct fields */
    @Test
    void testFormatting() {
        String result = tracker.toString();
        assertTrue(result.contains("john123"));
        assertTrue(result.contains("NOVEMBER"));
        assertTrue(result.contains(String.valueOf(LocalDate.now().getYear())));
    }

    /** 7. Full update cycle: year, month, target and cleanup completed books */
    @Test
    void testFullUpdateCycle() {
        // Update month and year
        tracker.setMonth(Months.JANUARY);
        tracker.setYear("2030");
        tracker.setTargetBooksNum(12);

        assertEquals(Months.JANUARY, tracker.getMonth());
        assertEquals("2030", tracker.getYear());
        assertEquals(12, tracker.getTargetBooksNum());

        // Add two books
        BookCatalog bc = new BookCatalog("Dune", "Frank Herbert");
        UserBook ub1 = new UserBook(user, bc, ShelfStatus.WANT_TO_READ);
        UserBook ub2 = new UserBook(user, bc, ShelfStatus.WANT_TO_READ);

        tracker.addToTracker(ub1);
        tracker.addToTracker(ub2);

        // Mark one completed
        tracker.getGoalBooks().get(0).setCompleted(true);

        // Clean up completed
        tracker.cleanUpCompletedBooks();

        // Only 1 left
        assertEquals(1, tracker.getGoalBooks().size());
    }
}
