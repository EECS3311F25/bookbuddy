package com.bookbuddy.model;


import jakarta.persistence.*;
import jakarta.persistence.Table;

/*
 * review is an object between the user and book catalog, and connect them 
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
    
    @Column (nullable = true)
    private String reviewText;  //  actual review 
    
    @Column (nullable = false)
    private int rating;         //  1-5 stars
    
    public Review(BookCatalog book, User user, int rating) { 
    	
    	this.book = book; 
    	this.user = user; 
    	if ( rating <= 5 && rating >= 0) { 
        	this.rating = rating; 
    	} else if (rating < 0 ) { 
    		this.rating = 0; 
    	} else if (rating > 5) { 
    		this.rating = 5; 
    	}
    }
    
    public String getComment() { return reviewText; }
    public void setComment(String comment) { this.reviewText = comment; }
    
    @Override
    public String toString() {
    	if ( this.reviewText == null) { this.reviewText = "N/A"; } 
        return String.format(
            "Review { user='%s', book='%s', rating=%d, comment='%s' }",
            user.getUsername(),
            book.getTitle(),
            rating

        );
    }

}

