package com.bookbuddy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Advanced tests for MonthlyTrackerBook model using the correct constructors.
 */
class MonthlyTrackerBookTest {

    private MonthlyTracker tracker;
    private UserBook userBook;
    private MonthlyTrackerBook trackerBook;

    @BeforeEach
    void setUp() {
        // User
        User user = new User("John", "Doe", "john123", "john@example.com", "Password123");

        // MonthlyTracker (correct constructor!)
        tracker = new MonthlyTracker(user, Months.NOVEMBER);
        tracker.setTargetBooksNum(5);

        // Book + UserBook
        BookCatalog book = new BookCatalog("Dune", "Frank Herbert");
        userBook = new UserBook(user, book, ShelfStatus.CURRENTLY_READING);

        // MonthlyTrackerBook
        trackerBook = new MonthlyTrackerBook(tracker, userBook);
    }

    /** 1. Normal behavior */
    @Test
    void testNormalBehavior() {
        assertEquals(tracker, trackerBook.getMonthlyTracker());
        assertEquals(userBook, trackerBook.getUserBook());
        assertFalse(trackerBook.isCompleted());
    }

    /** 2. Null behavior */
    @Test
    void testNullBehavior() {
        trackerBook.setMonthlyTracker(null);
        assertNull(trackerBook.getMonthlyTracker());

        trackerBook.setUserBook(null);
        assertNull(trackerBook.getUserBook());
    }

    /** 3. Toggle behavior for completed flag */
    @Test
    void testToggleCompletedBehavior() {
        trackerBook.setCompleted(true);
        assertTrue(trackerBook.isCompleted());

        trackerBook.setCompleted(false);
        assertFalse(trackerBook.isCompleted());
    }

    /** 4. Edge cases: repeat same update multiple times */
    @Test
    void testEdgeCases() {
        trackerBook.setCompleted(true);
        trackerBook.setCompleted(true); // should remain true
        assertTrue(trackerBook.isCompleted());
    }

    /** 5. Relationship behavior */
    @Test
    void testRelationshipBehavior() {
        assertEquals("john123", trackerBook.getUserBook().getUser().getUsername());
        assertEquals("Dune", trackerBook.getUserBook().getBook().getTitle());
    }

    /** 6. Formatting fields via toString */
    @Test
    void testStringFormatting() {
        String s = trackerBook.toString();
        assertNotNull(s);
        assertTrue(s.contains("completed") || s.contains("MonthlyTrackerBook"));
    }

    /** 7. Full update cycle */
    @Test
    void testFullUpdateCycle() {
        trackerBook.setCompleted(true);
        assertTrue(trackerBook.isCompleted());

        // Change tracker to a new one (using your constructor)
        User newUser = new User("Alice", "Smith", "alice88", "alice@example.com", "AAAaaa111");
        MonthlyTracker newTracker = new MonthlyTracker(newUser, Months.JANUARY);

        trackerBook.setMonthlyTracker(newTracker);
        assertEquals(Months.JANUARY, trackerBook.getMonthlyTracker().getMonth());
        assertEquals("alice88", trackerBook.getMonthlyTracker().getUser().getUsername());
    }
}
