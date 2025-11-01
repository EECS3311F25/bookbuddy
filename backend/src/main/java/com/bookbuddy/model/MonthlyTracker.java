package com.bookbuddy.model;

import java.util.ArrayList;
import java.util.Iterator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

/**
 * Tracks a user's monthly reading goals.
 * Each user can have multiple MonthlyTrackers (e.g., one for each month).
 * Each tracker can contain multiple MonthlyTrackerBooks.
 * 
 * When a book is marked as READ, it is removed from the monthly tracker automatically.
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

	@Pattern(regexp = "^\\d{4}$", message = "Year must be a 4-digit number")
	@Column(nullable = false)
	private String year;

	// User's target number of books for this month
	@Column(nullable = false)
	private int targetBooksNum;

	// The list of books in this monthly tracker
	@OneToMany(mappedBy = "monthlyTracker", cascade = CascadeType.ALL, orphanRemoval = true)
	private ArrayList<MonthlyTrackerBook> goalBooks = new ArrayList<>();

	public MonthlyTracker() {}

	public MonthlyTracker(User user, Months month) {
		this.user = user;
		this.month = month;
		this.year = String.valueOf(LocalDate.now().getYear());
		this.targetBooksNum = 0;
	}

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Months getMonth() {
		return month;
	}

	public void setMonth(Months month) {
		this.month = month;
	}


	public int getTargetBooksNum() {
		return targetBooksNum;
	}

	public void setTargetBooksNum(int targetBooksNum) {
		this.targetBooksNum = targetBooksNum;
	}

	public ArrayList<MonthlyTrackerBook> getGoalBooks() {
		return goalBooks;
	}

	public void setGoalBooks(ArrayList<MonthlyTrackerBook> goalBooks) {
		this.goalBooks = goalBooks;
	}


	/**
	 * Adds a UserBook to this monthly tracker.
	 * Automatically ignores books already marked as READ.
	 * the user of the book should be the same and not be null  
	 */
	public void addToTracker(UserBook userBook) {
		if (userBook != null 
				&& userBook.getUser().equals(this.user) 
				&& userBook.getShelf() != ShelfStatus.READ) {

			MonthlyTrackerBook goalBook = new MonthlyTrackerBook(this, userBook);
			goalBooks.add(goalBook);
		}
	}

	/**
	 * Removes a book from this tracker.
	 * 
	 */
	public void removeFromTracker(UserBook userBook) {
		if (userBook == null) return;

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
	 * Removes all books marked as completed (READ).
	 */
	public void cleanUpCompletedBooks() {
		goalBooks.removeIf(goalBook -> goalBook.isCompleted());
	}

	@Override
	public String toString() {
		return String.format(
				"MonthlyTracker { id=%d, user='%s', month=%s, year=%d, targetBooks=%d, currentBooks=%d }",
				id, user.getUsername(), month.name(), year,	targetBooksNum, goalBooks.size());
	}
}
