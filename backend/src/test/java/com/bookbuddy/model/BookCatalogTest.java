package com.bookbuddy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BookCatalogTest {

    private BookCatalog book;

    @BeforeEach
    void setUp() {
        book = new BookCatalog("Dune", "Frank Herbert");
    }

    /** 1. BASIC TEST – constructor fields */
    @Test
    void testConstructorInitializesValues() {
        assertEquals("Dune", book.getTitle());
        assertEquals("Frank Herbert", book.getAuthor());
        assertNull(book.getDescription());
        assertNull(book.getCoverUrl());
        assertNull(book.getOpenLibraryId());
    }

    /** 2. updateDetails() should update all fields */
    @Test
    void testUpdateDetails() {
        book.UpdateDetails("Dune Messiah", "Frank Herbert", "Sequel");
        assertEquals("Dune Messiah", book.getTitle());
        assertEquals("Frank Herbert", book.getAuthor());
        assertEquals("Sequel", book.getDescription());
    }

    /** 3. updateDetails() should reject blank title */
    @Test
    void testUpdateDetailsRejectsBlankTitle() {
        book.UpdateDetails("", "New Author", "New Desc");
        assertEquals("Dune", book.getTitle());
    }

    /** 4. updateDetails() should reject null author */
    @Test
    void testUpdateDetailsRejectsNullAuthor() {
        book.UpdateDetails("New Title", null, "Desc");
        assertEquals("Frank Herbert", book.getAuthor());
    }

    /** 5. Cover URL setter should accept valid value */
    @Test
    void testSetCoverUrl() {
        book.setCoverUrl("http://example.com/cover.jpg");
        assertEquals("http://example.com/cover.jpg", book.getCoverUrl());
    }

    /** 6. OpenLibraryId should save correctly */
    @Test
    void testSetOpenLibraryId() {
        book.setOpenLibraryId("OL12345M");
        assertEquals("OL12345M", book.getOpenLibraryId());
    }

    /** 7. Genre assignment should work correctly */
    @Test
    void testSetGenre() {
        book.setGenre(Genre.FANTASY);
        assertEquals(Genre.FANTASY, book.getGenre());
    }
}
