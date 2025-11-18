package com.bookbuddy.dto;

/**
 * Response DTO for review data
 * Hides sensitive user information, only exposes username
 */
public class ReviewResponse {

    private Long id;
    private String username;
    private String bookTitle;
    private Long bookId;
    private Integer rating;
    private String reviewText;

    public ReviewResponse() {}

    public ReviewResponse(Long id, String username, String bookTitle, Long bookId, Integer rating, String reviewText) {
        this.id = id;
        this.username = username;
        this.bookTitle = bookTitle;
        this.bookId = bookId;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
