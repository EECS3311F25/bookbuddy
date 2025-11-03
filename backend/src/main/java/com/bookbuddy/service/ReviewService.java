package com.bookbuddy.service;

import com.bookbuddy.model.Review;
import com.bookbuddy.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
/**
 * Service layer for managing {@link Review} entities.
 * Handles all the operations related to the user reviews, including creating, updating , deletion, and retrieving reviews
 * for specific books.It also calculate the average ratings for books based on submitted user feedback. 
 * Main tasks : 
 * 1. save or update book reviews
 * 2. retrieve reviews by book or bookID
 * 3. Delete Reviews
 * 4. Calculate average book ratings
 * 
 */
public class ReviewService {
	
	
 
	private final ReviewRepository reviewRepository;

    /**
     * Constructor injection for the ReviewRepository dependency.
     * @param reviewRepository repository instance injected by Spring
     */
    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * Saves or updates a {@link Review} entry in the database.
     * @param review the {@link Review} entity to be saved or updated
     * @return the saved {@link Review} entity
     */
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    /**
     * Retrieves all {@link Review} entries from the database.
     * @return list of all {@link Review} entities
     */
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    /**
     * Retrieves a specific {@link Review} by its unique ID.
     * @param id the unique ID of the review
     * @return an {@link Optional} containing the review if found
     */
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    /**
     * Deletes a {@link Review} entry from the database by ID.
     * @param id the unique ID of the review to be deleted
     */
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    /**
     * Retrieves all {@link Review} entries associated with a specific catalog book.
     * @param bookId the ID of the {@link com.bookbuddy.model.BookCatalog} being reviewed
     * @return list of {@link Review} entities linked to the specified catalog book
     */
    public List<Review> getReviewsByBookId(Long bookId) {
        return reviewRepository.findByBookId(bookId);
    }

    /**
     * Calculates the average rating for a given catalog book.
     * @param bookId the ID of the {@link com.bookbuddy.model.BookCatalog} whose average rating is to be calculated
     * @return the average rating value as a double (0.0 if no reviews exist)
     */
    public double getAverageRating(Long bookId) {
        List<Review> reviews = reviewRepository.findByBookId(bookId);

        if (reviews.isEmpty()) {
            return 0.0;
        }

        double total = 0;
        for (Review review : reviews) {
            total += review.getRating();
        }

        return total / reviews.size();
    }
  
}
