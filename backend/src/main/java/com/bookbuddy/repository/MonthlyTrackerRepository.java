package com.bookbuddy.repository;

import com.bookbuddy.model.MonthlyTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link MonthlyTracker} entities.
 *
 * This repository provides built-in CRUD operations and
 * custom queries for retrieving trackers associated with users.ß
 */
@Repository
public interface MonthlyTrackerRepository extends JpaRepository<MonthlyTracker, Long> {

    /**
     * Finds all monthly trackers belonging to a specific user.
     *
     * @param userId the ID of the {@link com.bookbuddy.model.User}
     * @return list of {@link MonthlyTracker} entries linked to that user
     */
    List<MonthlyTracker> findByUserId(Long userId);
}
