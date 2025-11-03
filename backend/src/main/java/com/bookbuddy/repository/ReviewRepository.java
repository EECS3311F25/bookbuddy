package com.bookbuddy.repository;

import com.bookbuddy.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository for Review entity.
 * Stores ratings and written reviews for books.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

 

    // Find all reviews written by a specific user
    List<Review> findByUserId(Long userId);

    // Find reviews by rating value
    List<Review> findByRating(int rating);
    List<Review> findByBookId(Long bookId);
}
