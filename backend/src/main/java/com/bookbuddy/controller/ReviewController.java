package com.bookbuddy.controller;

import com.bookbuddy.model.Review;
import com.bookbuddy.service.ReviewService;
import com.bookbuddy.service.BookCatalogService;
import com.bookbuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for handling book reviews and ratings.
 */
@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final BookCatalogService bookCatalogService;

    @Autowired
    public ReviewController(
            ReviewService reviewService,
            UserService userService,
            BookCatalogService bookCatalogService) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.bookCatalogService = bookCatalogService;
    }

    /**
     * Submit a review for a book.
     *
     * @param review review data
     * @return created review
     */
    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody Review review) {

        Optional<?> user = userService.getUserById(review.getUser().getId());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }

        Optional<?> book = bookCatalogService.getBookById(review.getBook().getId());
        if (book.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Book not found");
        }

        Review savedReview = reviewService.saveReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }

    /**
     * Get all reviews for a book.
     *
     * @param bookId ID of the book
     * @return list of reviews
     */
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Review>> getReviewsForBook(@PathVariable Long bookId) {
        List<Review> reviews = reviewService.getReviewsByBookId(bookId);
        return ResponseEntity.ok(reviews);
    }

    /**
     * Get a single review by its ID.
     *
     * @param id review ID
     * @return review or error message
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable Long id) {

        Optional<Review> review = reviewService.getReviewById(id);
        if (review.isPresent()) {
            return ResponseEntity.ok(review.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Review not found with id: " + id);
    }

    /**
     * Delete a review.
     *
     * @param id review ID
     * @return success or error message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        Optional<Review> review = reviewService.getReviewById(id);

        if (review.isPresent()) {
            reviewService.deleteReview(id);
            return ResponseEntity.ok("Review deleted");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Review not found with id: " + id);
    }

    /**
     * Get average rating for a book.
     *
     * @param bookId book ID
     * @return rating value
     */
    @GetMapping("/book/{bookId}/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long bookId) {
        double rating = reviewService.getAverageRating(bookId);
        return ResponseEntity.ok(rating);
    }
}
