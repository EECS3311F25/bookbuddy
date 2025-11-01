package com.bookbuddy.model;

import jakarta.persistence.*;

/**
 * Represents a single book that is part of a user's monthly reading goal.
 * Each MonthlyTrackerBook links a UserBook entry to a specific MonthlyTracker.
 */
@Entity
@Table(name = "monthly_tracker_books")
public class MonthlyTrackerBook { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The monthly tracker this goal belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "monthly_tracker_id", nullable = false)
    private MonthlyTracker monthlyTracker;

    /**
     * The user's book entry being tracked for this month.
     */
    @ManyToOne
    @JoinColumn(name = "user_book_id", nullable = false)
    private UserBook userBook;

    /**
     * Whether the user completed this book during the month.
     */
    @Column(nullable = false)
    private boolean isCompleted = false;

    public MonthlyTrackerBook() {
        // Required by JPA
    }

    public MonthlyTrackerBook(MonthlyTracker monthlyTracker, UserBook userBook) {
        this.monthlyTracker = monthlyTracker;
        this.userBook = userBook;
        this.isCompleted = false;
    }

    public Long getId() {
        return id;
    }

    public MonthlyTracker getMonthlyTracker() {
        return monthlyTracker;
    }

    public void setMonthlyTracker(MonthlyTracker monthlyTracker) {
        this.monthlyTracker = monthlyTracker;
    }

    public UserBook getUserBook() {
        return userBook;
    }

    public void setUserBook(UserBook userBook) {
        this.userBook = userBook;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }


    /**
     * Marks this goal book as completed.
     */
    public void markAsCompleted() {
        this.isCompleted = true;
    }

    /**
     * Resets the book to incomplete (if user unmarks it).
     */
    public void resetCompletion() {
        this.isCompleted = false;
    }

    /**
     * Changes the shelf of the underlying UserBook (for consistency).
     */
    public void changeShelf(ShelfStatus newStatus) {
        if (userBook != null && newStatus != null) {
            userBook.setShelf(newStatus);
        }
    }

    @Override
    public String toString() {
        String bookTitle = (userBook != null && userBook.getBook() != null)
                ? userBook.getBook().getTitle()
                : "Unknown Book";

        String authorName = (userBook != null && userBook.getBook() != null)
                ? userBook.getBook().getAuthor()
                : "Unknown Author";

        return String.format(
            "MonthlyTrackerBook { id=%d, book='%s' by '%s', completed=%b }",
            id, bookTitle, authorName, isCompleted
        );
    }
}
