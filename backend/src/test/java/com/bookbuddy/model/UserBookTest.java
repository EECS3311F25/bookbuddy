package com.bookbuddy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Advanced test cases for UserBook model.
 * Each test evaluates multiple behaviors (normal, null, edge cases, formatting, updates).
 */
class UserBookTest {

    private User user;
    private BookCatalog book;
    private UserBook userBook;

    @BeforeEach
    void setUp() {
        user = new User("John", "Doe", "john123", "john@example.com", "Password123");
        book = new BookCatalog("Dune", "Frank Herbert");
        userBook = new UserBook(user, book, ShelfStatus.WANT_TO_READ);
    }

    /** 1. Normal behavior: constructor, getters, initial shelf status */
    @Test
    void testNormalBehavior() {
        assertEquals(user, userBook.getUser());
        assertEquals(book, userBook.getBook());
        assertEquals(ShelfStatus.WANT_TO_READ, userBook.getShelf());

        // Change shelf status normally
        userBook.markAsRead();
        assertEquals(ShelfStatus.READ, userBook.getShelf());
    }

    /** 2. Null behavior: setting user or book to null should work safely */
    @Test
    void testNullBehavior() {
        userBook.setUser(null);
        assertNull(userBook.getUser());

        userBook.setBook(null);
        assertNull(userBook.getBook());
    }

    /** 3. Invalid or redundant shelf updates should not break anything */
    @Test
    void testInvalidShelfUpdates() {
        userBook.markAsRead();
        assertEquals(ShelfStatus.READ, userBook.getShelf());

        // Reapplying same status should not change anything
        userBook.markAsRead();
        assertEquals(ShelfStatus.READ, userBook.getShelf());

        // Set back to WANT_TO_READ
        userBook.markAsWantToRead();
        assertEquals(ShelfStatus.WANT_TO_READ, userBook.getShelf());
    }

    /** 4. Relationship behavior: user-book connection should remain consistent */
    @Test
    void testRelationshipBehavior() {
        assertEquals("john123", userBook.getUser().getUsername());
        assertEquals("Dune", userBook.getBook().getTitle());

        // Change user
        User newUser = new User("Alice", "Smith", "alice88", "alice@example.com", "AlicePass22");
        userBook.setUser(newUser);
        assertEquals(newUser, userBook.getUser());
    }

    /** 5. Edge cases: changing shelf from READ back to others, null changes */
    @Test
    void testEdgeCases() {
        userBook.markAsRead();
        assertEquals(ShelfStatus.READ, userBook.getShelf());

        // Edge: switching directly to CURRENTLY_READING
        userBook.markAsCurrentlyReading();
        assertEquals(ShelfStatus.CURRENTLY_READING, userBook.getShelf());

        // Edge: setting userBook's internals unexpectedly
        userBook.setUser(null);
        assertNull(userBook.getUser());
    }

    /** 6. String/field formatting: ensure book details remain correct */
    @Test
    void testStringAndFieldFormatting() {
        assertEquals("Dune", userBook.getBook().getTitle());
        assertEquals("Frank Herbert", userBook.getBook().getAuthor());

        // After shelf update, still formatted correctly
        userBook.markAsRead();
        assertEquals(ShelfStatus.READ, userBook.getShelf());
    }

    /** 7. Update behavior: changing book and shelf thoroughly */
    @Test
    void testUpdateBehavior() {
        BookCatalog newBook = new BookCatalog("1984", "George Orwell");
        userBook.setBook(newBook);

        assertEquals("1984", userBook.getBook().getTitle());
        assertEquals("George Orwell", userBook.getBook().getAuthor());

        // Update shelf multiple times
        userBook.markAsWantToRead();
        assertEquals(ShelfStatus.WANT_TO_READ, userBook.getShelf());

        userBook.markAsCurrentlyReading();
        assertEquals(ShelfStatus.CURRENTLY_READING, userBook.getShelf());
    }
}
