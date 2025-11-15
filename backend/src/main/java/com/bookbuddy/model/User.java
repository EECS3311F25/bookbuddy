package com.bookbuddy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * User entity representing APP users.
 * Each user has their own personal library and reading goals.
 * The class handles:
 * 1. Personal library list (userBooks)
 * 2. Profile updates
 * 3. Simple user data management
 */

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank(message = "First name cannot be empty")
	@Column(nullable = false)
	private String firstName;

	@NotBlank(message = "Last name cannot be empty")
	@Column(nullable = false)
	private String lastName;

	@NotBlank(message = "Username cannot be empty")
	@Column(nullable = false, unique = true)
	private String username;

	@Email(message = "Email should be valid")
	@NotBlank(message = "Email cannot be empty")
	@Column(nullable = false, unique = true)
	private String email;

	@Pattern(regexp = "^$|^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Password must include at least 8 characters, uppercase, lowercase, and numbers")
	@Column(nullable = false)
	private String password;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserBook> userBooks;

	public User() {
		this.userBooks = new ArrayList<>();
	}

	public User(String firstName, String lastName, String username, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.userBooks = new ArrayList<>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonIgnore
	public List<UserBook> getUserBooks() {
		return userBooks;
	}

	@JsonIgnore
	public void setUserBooks(List<UserBook> userBooks) {
		this.userBooks = userBooks;
	}

	public void addUserBook(UserBook userBook) {
		if (userBook != null && !this.userBooks.contains(userBook)) {
			userBooks.add(userBook);
			userBook.setUser(this);
		}
	}

	public void removeBook(UserBook userBook) {
		if (userBook != null && this.userBooks.remove(userBook)) {
			userBook.setUser(null);
		}
	}

	public void updateFirstName(String newFirstName) {
		if (newFirstName != null && !newFirstName.isBlank()) {
			this.firstName = newFirstName;
		}
	}

	public void updateLastName(String newLastName) {
		if (newLastName != null && !newLastName.isBlank()) {
			this.lastName = newLastName;
		}
	}

	public void updateUsername(String newUsername) {
		if (newUsername != null && !newUsername.isBlank()) {
			this.username = newUsername;
		}
	}

	public void updateEmail(String newEmail) {
		if (newEmail != null && !newEmail.isBlank()) {
			this.email = newEmail;
		}
	}

	public void updatePassword(String newPassword) {
		if (newPassword != null && !newPassword.isBlank()) {
			this.password = newPassword; // should be hashed in service layer
		}
	}

	public int getTotalBooks() {
		return this.userBooks.size();
	}

	public String getBooks() {
		if (this.userBooks.isEmpty()) {
			return "N/A";
		}

		StringBuilder books = new StringBuilder();
		for (int i = 0; i < this.userBooks.size(); i++) {
			UserBook ub = userBooks.get(i);
			books.append(String.format("%s by %s", ub.getBook().getTitle(), ub.getBook().getAuthor()));
			if (i < this.userBooks.size() - 1) {
				books.append(", ");
			} else {
				books.append(".");
			}
		}
		return books.toString();
	}

	@Override
	public String toString() {
		return String.format(
				"User { id=%d, firstName='%s', lastName='%s', username='%s', email='%s', totalBooks=%d, books=[%s], password='******' }",
				id, firstName, lastName, username, email, getTotalBooks(), getBooks());
	}
}
