package com.bookbuddy.model;

import jakarta.persistence.*;

/**
 * Review entity represents a review written by a user for a specific book
 * in the catalog. It connects {@link User} and {@link BookCatalog}
 * with additional fields for rating and optional text comments.
 */
@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // The book which has been reviewed
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookCatalog book;

    // The user who wrote the review
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = true)
    private String reviewText;  // actual review text

    @Column(nullable = false)
    private int rating;         // 1–5 stars

    /** Default constructor (required by JPA). */
    public Review() {}

    /**
     * Creates a Review object and automatically enforces rating range (0–5).
     * @param book the BookCatalog being reviewed
     * @param user the User who wrote the review
     * @param rating numeric rating between 0 and 5
     */
    public Review(BookCatalog book, User user, int rating) {
        this.book = book;
        this.user = user;
        setRating(rating); // use setter for validation
    }

    /** @return the review's unique ID */
    public long getId() {
        return id;
    }

    /** @return the BookCatalog being reviewed */
    public BookCatalog getBook() {
        return book;
    }

    /** @param book the BookCatalog to associate with this review */
    public void setBook(BookCatalog book) {
        this.book = book;
    }

    /** @return the User who wrote the review */
    public User getUser() {
        return user;
    }

    /** @param user the User who wrote the review */
    public void setUser(User user) {
        this.user = user;
    }

    /** @return the text content of the review */
    public String getComment() {
        return reviewText;
    }

    /** @param comment the text content of the review */
    public void setComment(String comment) {
        this.reviewText = comment;
    }

    /** @return the numeric rating (1–5) */
    public int getRating() {
        return rating;
    }

    /**
     * Sets a rating for this review, ensuring it stays within the 0–5 range.
     * @param rating the numeric rating to set
     */
    public void setRating(int rating) {
        if (rating < 0) {
            this.rating = 0;
        } else if (rating > 5) {
            this.rating = 5;
        } else {
            this.rating = rating;
        }
    }

    /** @return a formatted string representation of the review */
    @Override
    public String toString() {
        if (this.reviewText == null) {
            this.reviewText = "N/A";
        }
        return String.format(
                "Review { user='%s', book='%s', rating=%d, comment='%s' }",
                user != null ? user.getUsername() : "Unknown",
                book != null ? book.getTitle() : "Unknown",
                rating,
                reviewText
        );
    }
}
