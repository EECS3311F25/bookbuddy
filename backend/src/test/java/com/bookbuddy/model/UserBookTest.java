package com.bookbuddy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


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

    /** 1. BASIC TEST – default values */
    @Test
    void testConstructorInitializesFields() {
        assertEquals(user, userBook.getUser());
        assertEquals(book, userBook.getBook());
        assertEquals(ShelfStatus.WANT_TO_READ, userBook.getShelf());
    }

    /** 2. markAsRead() should change shelf status */
    @Test
    void testMarkAsRead() {
        userBook.markAsRead();
        assertEquals(ShelfStatus.READ, userBook.getShelf());
    }

    /** 3. markAsCurrentlyReading() should update shelf */
    @Test
    void testMarkAsCurrentlyReading() {
        userBook.markAsCurrentlyReading();
        assertEquals(ShelfStatus.CURRENTLY_READING, userBook.getShelf());
    }

    /** 4. markAsWantToRead() should update shelf */
    @Test
    void testMarkAsWantToRead() {
        userBook.markAsWantToRead();
        assertEquals(ShelfStatus.WANT_TO_READ, userBook.getShelf());
    }

    /** 5. Ensures user can be changed */
    @Test
    void testSetUser() {
        User newUser = new User("Alice", "Smith", "alice88", "alice@example.com", "Aliceee22");
        userBook.setUser(newUser);
        assertEquals(newUser, userBook.getUser());
    }

    /** 6. getBook details should match */
    @Test
    void testGetBookDetails() {
        assertEquals("Dune", userBook.getBook().getTitle());
        assertEquals("Frank Herbert", userBook.getBook().getAuthor());
    }

    /** 7. Shelf status should not incorrectly revert */
    @Test
    void testShelfDoesNotChangeIncorrectly() {
        userBook.markAsRead();
        assertEquals(ShelfStatus.READ, userBook.getShelf());
        userBook.markAsRead(); // calling again shouldn’t change logic
        assertEquals(ShelfStatus.READ, userBook.getShelf());
    }
}
