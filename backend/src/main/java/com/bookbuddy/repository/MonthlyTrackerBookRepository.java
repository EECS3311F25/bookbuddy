package com.bookbuddy.repository;

import com.bookbuddy.model.MonthlyTrackerBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for managing {@link MonthlyTrackerBook} entities.
 *
 * This repository handles database operations related to books
 * that are linked to a user's {@link com.bookbuddy.model.MonthlyTracker}.
 * It provides built-in CRUD operations through {@link JpaRepository}
 * and also defines a method to find tracker books by their tracker ID.
 */
@Repository
public interface MonthlyTrackerBookRepository extends JpaRepository<MonthlyTrackerBook, Long> {

    /**
     *
     * @param trackerId the ID of the {@link com.bookbuddy.model.MonthlyTracker}
     * @return list of {@link MonthlyTrackerBook} entities belonging to that tracker
     */
    List<MonthlyTrackerBook> findByMonthlyTrackerId(Long trackerId);
}
