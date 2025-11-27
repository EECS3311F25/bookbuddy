package com.bookbuddy.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a user's monthly reading goal.
 * Each user can have multiple MonthlyTrackers (one for each month)
 * that keep track of their progress and reading targets.
 */
@Entity
@Table(name = "monthly_trackers")
public class MonthlyTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Each tracker belongs to one user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // The month being tracked
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Months month;

    // The year (must be a 4-digit number)
    @Pattern(regexp = "^\\d{4}$", message = "Year must be a 4-digit number")
    @Column(nullable = false)
    private String year;

    // User's target number of books for this month
    @Column(nullable = false)
    private int targetBooksNum;

    // The list of books being tracked for this month
    @JsonManagedReference
    @OneToMany(mappedBy = "monthlyTracker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MonthlyTrackerBook> goalBooks = new ArrayList<>();

    /** Default constructor (required by JPA). */
    public MonthlyTracker() {}

    /**
     * Creates a tracker for a user for a specific month.
     * @param user the user who owns this tracker
     * @param month the month being tracked
     */
    public MonthlyTracker(User user, Months month) {
        this.user = user;
        this.month = month;
        this.year = String.valueOf(LocalDate.now().getYear());
        this.targetBooksNum = 0;
    }

    /** @return the ID of this tracker */
    public Long getId() {
        return id;
    }

    /** @return the user who owns this tracker */
    public User getUser() {
        return user;
    }

    /** @param user sets the user who owns this tracker */
    public void setUser(User user) {
        this.user = user;
    }

    /** @return the month associated with this tracker */
    public Months getMonth() {
        return month;
    }

    /** @param month sets the month for this tracker */
    public void setMonth(Months month) {
        this.month = month;
    }

    /** @return the year being tracked */
    public String getYear() {
        return year;
    }

    /** @param year sets the year being tracked */
    public void setYear(String year) {
        this.year = year;
    }

    /** @return the target number of books */
    public int getTargetBooksNum() {
        return targetBooksNum;
    }

    /** @param targetBooksNum sets the target number of books */
    public void setTargetBooksNum(int targetBooksNum) {
        this.targetBooksNum = targetBooksNum;
    }

    /** @return list of all goal books in this tracker */
    public List<MonthlyTrackerBook> getGoalBooks() {
        return goalBooks;
    }

    /** @param goalBooks sets the list of goal books */
    public void setGoalBooks(List<MonthlyTrackerBook> goalBooks) {
        this.goalBooks = goalBooks;
    }

    /**
     * Adds a user book to the tracker if:
     * 1. The book belongs to the same user.
     * 2. The book is not marked as READ.
     *
     * @param userBook the book to add to the tracker
     */
    public void addToTracker(UserBook userBook) {
        if (userBook != null
                && userBook.getUser() != null
                && userBook.getUser().equals(this.user)
                && userBook.getShelf() != ShelfStatus.READ) {

            MonthlyTrackerBook goalBook = new MonthlyTrackerBook(this, userBook);
            goalBooks.add(goalBook);
        }
    }

    /**
     * Removes a user book from the tracker.
     * @param userBook the book to remove
     */
    public void removeFromTracker(UserBook userBook) {
        if (userBook == null) {
            return;
        }

        Iterator<MonthlyTrackerBook> iterator = goalBooks.iterator();
        while (iterator.hasNext()) {
            MonthlyTrackerBook goalBook = iterator.next();
            if (goalBook.getUserBook().equals(userBook)) {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * Removes all completed books from the tracker.
     */
    public void cleanUpCompletedBooks() {
        Iterator<MonthlyTrackerBook> iterator = goalBooks.iterator();
        while (iterator.hasNext()) {
            MonthlyTrackerBook goalBook = iterator.next();
            if (goalBook.isCompleted()) {
                iterator.remove();
            }
        }
    }

    /**
     * Returns a formatted string of tracker details.
     * @return string with user, month, year, and progress info
     */
    @Override
    public String toString() {
        String userName = "Unknown";
        String monthName = "Unknown";

        if (user != null) {
            userName = user.getUsername();
        }

        if (month != null) {
            monthName = month.name();
        }

        return "MonthlyTracker { " +
                "id=" + id +
                ", user='" + userName + '\'' +
                ", month=" + monthName +
                ", year=" + year +
                ", targetBooks=" + targetBooksNum +
                ", currentBooks=" + goalBooks.size() +
                " }";
    }
}
