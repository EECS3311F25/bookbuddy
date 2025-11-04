package com.bookbuddy.dto;

import com.bookbuddy.model.Genre;
import com.bookbuddy.model.ShelfStatus;
import jakarta.validation.constraints.NotNull;

/**
 * Request DTO for adding a book from Open Library search directly to user's library
 */
public class AddBookFromSearchRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Open Library ID (key) is required")
    private String openLibraryId;

    private ShelfStatus shelf; // Optional: WANT_TO_READ, CURRENTLY_READING, READ

    // Book details from Open Library (required if creating new catalog entry)
    @NotNull(message = "Title is required")
    private String title;

    private String author;
    private String coverUrl;
    private Genre genre; // Optional genre

    public AddBookFromSearchRequest() {}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOpenLibraryId() {
        return openLibraryId;
    }

    public void setOpenLibraryId(String openLibraryId) {
        this.openLibraryId = openLibraryId;
    }

    public ShelfStatus getShelf() {
        return shelf;
    }

    public void setShelf(ShelfStatus shelf) {
        this.shelf = shelf;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
