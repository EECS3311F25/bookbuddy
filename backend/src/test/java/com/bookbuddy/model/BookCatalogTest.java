package com.bookbuddy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Advanced test cases for BookCatalog model.
 * Each test evaluates multiple behaviors (normal, null, invalid, edge cases, formatting, updates).
 */
class BookCatalogTest {

    private BookCatalog book;

    @BeforeEach
    void setUp() {
        book = new BookCatalog("Dune", "Frank Herbert");
    }

    /** 1. Normal behavior: constructor, getters, and simple updates */
    @Test
    void testNormalBehavior() {
        // constructor fields
        assertEquals("Dune", book.getTitle());
        assertEquals("Frank Herbert", book.getAuthor());
        assertEquals("", book.getDescription()); // Constructor sets empty string, not null

        // update details
        book.UpdateDetails("Dune Messiah", "Frank Herbert", "Sequel to Dune");
        assertEquals("Dune Messiah", book.getTitle());
        assertEquals("Sequel to Dune", book.getDescription());

        // set cover + open library
        book.setCoverUrl("http://example.com/cover.jpg");
        book.setOpenLibraryId("OL1234M");

        assertEquals("http://example.com/cover.jpg", book.getCoverUrl());
        assertEquals("OL1234M", book.getOpenLibraryId());
    }

    /** 2. Null behavior: UpdateDetails sets null values directly (no validation) */
    @Test
    void testNullBehavior() {
        book.UpdateDetails(null, null, null);

        // UpdateDetails sets values directly without validation
        assertNull(book.getTitle());
        assertNull(book.getAuthor());
        assertNull(book.getDescription());

        // null cover URL
        book.setCoverUrl(null);
        assertNull(book.getCoverUrl());
    }

    /** 3. Blank/invalid behavior: UpdateDetails sets blank values directly */
    @Test
    void testBlankInvalidBehavior() {
        book.UpdateDetails("", " ", " ");

        // UpdateDetails sets values directly without validation
        assertEquals("", book.getTitle());
        assertEquals(" ", book.getAuthor());
        assertEquals(" ", book.getDescription());
    }

    /** 4. Edge cases: partial updates + switching fields */
    @Test
    void testEdgeCases() {
        // Only change author
        book.UpdateDetails("Dune", "Author X", null);
        assertEquals("Author X", book.getAuthor());
        assertNull(book.getDescription());

        // Now only change description
        book.UpdateDetails(null, null, "Sci-Fi Classic");
        assertEquals("Sci-Fi Classic", book.getDescription());
    }

    /** 5. Genre behavior: assign and change genre */
    @Test
    void testGenreBehavior() {
        book.setGenre(Genre.SCIENCE_FICTION);
        assertEquals(Genre.SCIENCE_FICTION, book.getGenre());

        // change genre again
        book.setGenre(Genre.FANTASY);
        assertEquals(Genre.FANTASY, book.getGenre());
    }

    /** 6. Formatting: verify title + author integrity */
    @Test
    void testStringFormattingAndFields() {
        assertTrue(book.getTitle().startsWith("D"));
        assertTrue(book.getAuthor().contains("Herbert"));

        // update details and check formatting still correct
        book.UpdateDetails("Dune Messiah", "Frank Herbert", "Sequel");
        assertEquals("Dune Messiah", book.getTitle());
    }

    /** 7. Full update cycle: cover, ID, genre, description */
    @Test
    void testFullUpdateCycle() {
        book.setCoverUrl("http://covers.com/123.jpg");
        book.setOpenLibraryId("OL9988M");
        book.setGenre(Genre.SCIENCE_FICTION);
        book.UpdateDetails("Dune: Revised Edition", "Frank H.", "Updated version");

        assertEquals("Dune: Revised Edition", book.getTitle());
        assertEquals("Frank H.", book.getAuthor());
        assertEquals("Updated version", book.getDescription());
        assertEquals("http://covers.com/123.jpg", book.getCoverUrl());
        assertEquals("OL9988M", book.getOpenLibraryId());
        assertEquals(Genre.SCIENCE_FICTION, book.getGenre());
    }
}
