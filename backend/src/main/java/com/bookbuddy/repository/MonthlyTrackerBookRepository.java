package com.bookbuddy.repository;

import com.bookbuddy.model.MonthlyTrackerBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository for MonthlyTrackerBook entity.
 * Tracks user's monthly reading goals and associated books.
 */
@Repository
public interface MonthlyTrackerBookRepository extends JpaRepository<MonthlyTrackerBook, Long> {

    // Find all monthly trackers by user ID
    List<MonthlyTrackerBook> findByUserId(Long userId);

    // Find all monthly trackers by month name
    List<MonthlyTrackerBook> findByMonthName(String monthName);

    // Find a specific monthly tracker for a user by month
    MonthlyTrackerBook findByUserIdAndMonthName(Long userId, String monthName);
}
