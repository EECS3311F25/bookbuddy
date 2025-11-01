package com.bookbuddy.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a book entry in the global catalog of the application.
 * This entity stores all general information about books that exist in the system,
 * such as title, author, genre, description, and cover URL.
 * It acts as the main library that is shared among all users.
 * 
 * The global Bank list everyone can access and add from
 */
@Entity
@Table(name = "book_catalog") 
public class BookCatalog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String author;

	@Column(length = 2000)
	private String description;

	@Column(unique = true)
	private String coverUrl;

	@Column(unique = true)
	private String openLibraryId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Genre genre;

	@OneToMany(mappedBy = "book")
	private ArrayList<UserBook> userBook;

	public BookCatalog() {}


	public BookCatalog(String title, String author) {
		this.title = title;
		this.author = author;
		this.genre = Genre.OTHER;
		this.description = "";
		this.coverUrl = "";
		this.openLibraryId = "";
	}

	//getters and setters 

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public String getOpenLibraryId() {
		return openLibraryId;
	}

	public void setOpenLibraryId(String openLibraryId) {
		this.openLibraryId = openLibraryId;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	// change book info
	public void UpdateDetails(String title, String author, String description) {
		this.title = title; 
		this.author = author; 
		this.description = description;
	}
	// check if it comes from openlibraryAPI 
	public boolean hasOpenLibraryId() {
	  return openLibraryId != null && !openLibraryId.isEmpty();

	}
	
	@Override
	public String toString() { 
	
	 
	    String bookDescription;
	    String bookCover;
	    String bookOpenId;

	    if (description != null && !description.isEmpty()) {
	        bookDescription = description;
	    } else {
	        bookDescription = "N/A";
	    }

	    if (coverUrl != null && !coverUrl.isEmpty()) {
	        bookCover = coverUrl;
	    } else {
	        bookCover = "N/A";
	    }

	    if (openLibraryId != null && !openLibraryId.isEmpty()) {
	        bookOpenId = openLibraryId;
	    } else {
	        bookOpenId = "N/A";
	    }

	    return "BookCatalog{" +
	            "title='" + this.title + '\'' +
	            ", author='" + this.author + '\'' +
	            ", genre='" + this.genre+ '\'' +
	            ", description='" + bookDescription + '\'' +
	            ", coverUrl='" + bookCover + '\'' +
	            ", openLibraryId='" + bookOpenId + '\'' +
	            '}';
	}



}
