package com.bookbuddy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("John", "Doe", "john123", "john@example.com", "Password123");
    }

    /**
     * 1. Test update methods should NOT accept blank or null values.
     */
    @Test
    void testUpdateMethodsRejectInvalidValues() {
        user.updateFirstName("");          // blank
        user.updateLastName(null);         // null
        user.updateEmail(" ");             // blank
        user.updateUsername(null);         // null

        // Values should remain unchanged
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("john123", user.getUsername());
    }

    /**
     * 2. Test adding the SAME UserBook twice should NOT duplicate entries.
     */
    @Test
    void testAddDuplicateUserBookDoesNotDuplicate() {
        BookCatalog book = new BookCatalog("Dune", "Frank Herbert");
        UserBook userBook = new UserBook(user, book, ShelfStatus.WANT_TO_READ);

        user.addUserBook(userBook);
        user.addUserBook(userBook); // try adding the same object again

        assertEquals(1, user.getTotalBooks(), "Duplicate books should not be added.");
    }

    /**
     * 3. Test removeBook() correctly removes and clears user reference.
     */
    @Test
    void testRemoveBookCorrectlyDetachesUser() {
        BookCatalog book = new BookCatalog("1984", "George Orwell");
        UserBook userBook = new UserBook(user, book, ShelfStatus.READ);

        user.addUserBook(userBook);
        user.removeBook(userBook);

        assertEquals(0, user.getTotalBooks());
        assertNull(userBook.getUser(), "UserBook should no longer reference user.");
    }

    /**
     * 4. Test getBooks() formatting when multiple books exist.
     */
    @Test
    void testGetBooksFormatting() {
        user.addUserBook(new UserBook(
                user, new BookCatalog("Dune", "Frank Herbert"), ShelfStatus.READ));

        user.addUserBook(new UserBook(
                user, new BookCatalog("The Hobbit", "J.R.R. Tolkien"), ShelfStatus.READ));

        String result = user.getBooks();
        assertEquals("Dune by Frank Herbert, The Hobbit by J.R.R. Tolkien.", result);
    }

    /**
     * 5. Test password update should overwrite old password but NOT accept blank/null.
     */
    @Test
    void testUpdatePasswordValidation() {
        user.updatePassword("NewPass123");
        assertEquals("NewPass123", user.getPassword());

        // Invalid updates — should NOT overwrite
        user.updatePassword(null);
        user.updatePassword("");
        assertEquals("NewPass123", user.getPassword(), "Invalid passwords must be ignored.");
    }
}
