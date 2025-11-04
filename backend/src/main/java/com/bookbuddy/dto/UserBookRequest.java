package com.bookbuddy.dto;

import com.bookbuddy.model.ShelfStatus;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for adding/updating books in user's personal library
 */
public class UserBookRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Book ID is required")
    private Long bookId;

    private ShelfStatus shelf; // WANT_TO_READ, CURRENTLY_READING, READ

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public ShelfStatus getShelf() {
        return shelf;
    }

    public void setShelf(ShelfStatus shelf) {
        this.shelf = shelf;
    }
}
