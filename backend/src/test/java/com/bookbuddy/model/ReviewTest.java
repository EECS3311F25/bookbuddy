package com.bookbuddy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Advanced test cases for Review model.
 * Each test evaluates multiple behaviors (normal, null, invalid, formatting, updates).
 */
class ReviewTest {

    private User user;
    private BookCatalog book;
    private Review review;

    @BeforeEach
    void setUp() {
        user = new User("John", "Doe", "john123", "john@example.com", "Password123");
        book = new BookCatalog("Dune", "Frank Herbert");
        review = new Review(book, user, 4);
        review.setComment("Great book!");
    }

    /** 1. Normal behavior: constructor, getters */
    @Test
    void testNormalBehavior() {
        assertEquals(4, review.getRating());
        assertEquals("Great book!", review.getComment());
        assertEquals(user, review.getUser());
        assertEquals(book, review.getBook());
    }

    /** 2. Null behavior: null comment + null associations */
    @Test
    void testNullBehavior() {
        review.setComment(null);
        assertNull(review.getComment());

        review.setUser(null);
        assertNull(review.getUser());

        review.setBook(null);
        assertNull(review.getBook());
    }

    /** 3. Invalid behavior: rating out of range */
    @Test
    void testInvalidRatingBehavior() {
        review.setRating(-10);
        assertNotEquals(-10, review.getRating());

        review.setRating(100);
        assertNotEquals(100, review.getRating());
    }

    /** 4. Edge cases: update rating + comment together */
    @Test
    void testEdgeCases() {
        review.setRating(5);
        review.setComment("Updated review");

        assertEquals(5, review.getRating());
        assertEquals("Updated review", review.getComment());
    }

    /** 5. Relationship behavior: verify linked user + book fields */
    @Test
    void testRelationshipBehavior() {
        assertEquals("john123", review.getUser().getUsername());
        assertEquals("Dune", review.getBook().getTitle());
    }

    /** 6. String/field formatting tests */
    @Test
    void testStringFormatting() {
        assertTrue(review.getComment().contains("Great"));
        assertTrue(review.getBook().getAuthor().contains("Herbert"));
    }

    /** 7. Full update cycle: update everything at once */
    @Test
    void testFullUpdateCycle() {
        review.setRating(2);
        review.setComment("Not bad");
        User newUser = new User("Alice", "Smith", "alice88", "alice@example.com", "AlicePass22");
        review.setUser(newUser);

        assertEquals(2, review.getRating());
        assertEquals("Not bad", review.getComment());
        assertEquals("alice88", review.getUser().getUsername());
    }
}
