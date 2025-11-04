package com.bookbuddy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Represents the relationship between a User and a BookCatalog entry.
 * Each record in this table corresponds to a book that a specific user has
 * added to their personal library (e.g., marked as "READ" or
 * "CURRENTLY_READING").
 */

@Entity
@Table(name = "user_book")
public class UserBook {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * The user who owns this book entry in their personal library.
	 */
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	/**
	 * The global book catalog entry this user has added to their library.
	 */
	@ManyToOne
	@JoinColumn(name = "book_id", nullable = false)
	private BookCatalog book;

	/**
	 * The current shelf status for this book in the user's library.
	 * Possible values: WANT_TO_READ, CURRENTLY_READING, READ.
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ShelfStatus shelf = ShelfStatus.WANT_TO_READ;

	/**
	 * The date when the user marked this book as completed.
	 * Can be null if the book has not been finished yet.
	 */
	private LocalDate completedAt;

	/**
	 * The date when this entry was created (added to the user library).
	 */
	private LocalDate createdAt = LocalDate.now();

	// Default constructor (required by JPA)
	public UserBook() {
	}

	public UserBook(User user, BookCatalog book, ShelfStatus shelf) {

		this.user = user;
		this.book = book;
		this.shelf = shelf;
		this.createdAt = LocalDate.now();

	}

	// getters and setters

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BookCatalog getBook() {
		return book;
	}

	public void setBook(BookCatalog book) {
		this.book = book;
	}

	public ShelfStatus getShelf() {
		return shelf;
	}

	public void setShelf(ShelfStatus shelf) {
		this.shelf = shelf;
	}

	public LocalDate getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(LocalDate completedAt) {
		this.completedAt = completedAt;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public void markAsRead() {

		this.shelf = ShelfStatus.READ;
		this.completedAt = LocalDate.now();

	}

	public void markAsCurrentlyReading() {

		this.shelf = ShelfStatus.CURRENTLY_READING;
		this.completedAt = null;

	}

	public void markAsWantToRead() {

		this.shelf = ShelfStatus.WANT_TO_READ;
		this.completedAt = null;

	}

	public boolean isCompleted() {
		if (this.shelf == ShelfStatus.READ && this.completedAt != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {

		String username;
		String bookTitle;
		String completedDate;

		if (user != null) {
			username = user.getUsername();
		} else {
			username = "Unknown User";
		}

		if (book != null) {
			bookTitle = book.getTitle();
		} else {
			bookTitle = "Unknown Book";
		}

		if (completedAt != null) {
			completedDate = completedAt.toString();
		} else {
			completedDate = "Not completed";
		}

		return "UserBook{" +
				"user=" + username +
				", book=" + bookTitle +
				", shelf=" + shelf.name() +
				", completedAt=" + completedDate +
				", createdAt=" + createdAt +
				'}';
	}
}
