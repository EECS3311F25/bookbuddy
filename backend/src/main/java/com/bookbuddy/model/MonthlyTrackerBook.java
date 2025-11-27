package com.bookbuddy.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

/**
 * Represents a single book that is part of a user's monthly reading goal.
 * Each MonthlyTrackerBook links a {@link UserBook} entry to a specific {@link MonthlyTracker}.
 */
@Entity
@Table(name = "monthly_tracker_books")
public class MonthlyTrackerBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The monthly tracker this goal belongs to. */
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "monthly_tracker_id", nullable = false)
    private MonthlyTracker monthlyTracker;

    /** The user's book entry being tracked for this month. */
    @ManyToOne
    @JoinColumn(name = "user_book_id", nullable = false)
    private UserBook userBook;

    /** Whether the user completed this book during the month. */
    @JsonProperty("isCompleted")
    @Column(nullable = false)
    private boolean isCompleted = false;

    /** Default constructor (required by JPA). */
    public MonthlyTrackerBook() {}

    /**
     * Creates a new tracker book linking a monthly tracker and a user book.
     * @param monthlyTracker the {@link MonthlyTracker} this book belongs to
     * @param userBook the {@link UserBook} being tracked
     */
    public MonthlyTrackerBook(MonthlyTracker monthlyTracker, UserBook userBook) {
        this.monthlyTracker = monthlyTracker;
        this.userBook = userBook;
        this.isCompleted = false;
    }

    /** @return the unique ID of this tracker book */
    public Long getId() {
        return id;
    }

    /** @return the {@link MonthlyTracker} associated with this book */
    public MonthlyTracker getMonthlyTracker() {
        return monthlyTracker;
    }

    /** @param monthlyTracker sets the {@link MonthlyTracker} for this book */
    public void setMonthlyTracker(MonthlyTracker monthlyTracker) {
        this.monthlyTracker = monthlyTracker;
    }

    /** @return the {@link UserBook} being tracked */
    public UserBook getUserBook() {
        return userBook;
    }

    /** @param userBook sets the {@link UserBook} being tracked */
    public void setUserBook(UserBook userBook) {
        this.userBook = userBook;
    }

    /** @return true if this book has been completed */
    public boolean isCompleted() {
        return isCompleted;
    }

    /** @param completed sets the completion status of this book */
    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }

    /** Marks this tracker book as completed. */
    public void markAsCompleted() {
        this.isCompleted = true;
    }

    /** Resets this tracker book to incomplete. */
    public void resetCompletion() {
        this.isCompleted = false;
    }

    /**
     * Updates the shelf status of the underlying user book (for consistency).
     * @param newStatus the new {@link ShelfStatus} to assign
     */
    public void changeShelf(ShelfStatus newStatus) {
        if (userBook != null && newStatus != null) {
            userBook.setShelf(newStatus);
        }
    }

    /**
     * @return a readable summary of this tracker book
     */
    @Override
    public String toString() {
        String bookTitle = "Unknown Book";
        String authorName = "Unknown Author";

        if (userBook != null && userBook.getBook() != null) {
            if (userBook.getBook().getTitle() != null) {
                bookTitle = userBook.getBook().getTitle();
            }
            if (userBook.getBook().getAuthor() != null) {
                authorName = userBook.getBook().getAuthor();
            }
        }

        return "MonthlyTrackerBook { id=" + id +
                ", book='" + bookTitle + "'" +
                ", author='" + authorName + "'" +
                ", completed=" + isCompleted +
                " }";
    }
}
