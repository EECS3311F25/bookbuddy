package com.bookbuddy.service;

import com.bookbuddy.model.MonthlyTracker;
import com.bookbuddy.repository.MonthlyTrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing {@link MonthlyTracker} entities.
 *
 * This class handles logic related to users' monthly reading goals,
 * including creating, updating, retrieving, and deleting trackers.
 * Each tracker is associated with a specific user and month, allowing
 * progress tracking and goal adjustments.
 *
 * Main Tasks in MonthlyTrackerService:
 * 
 * 1. Create and update monthly reading goals
 * 2. Retrieve trackers by user ID or tracker ID
 * 3. Update target book count per month
 * 4. Delete trackers when no longer needed
 * 
 */
@Service
public class MonthlyTrackerService {

    private final MonthlyTrackerRepository monthlyTrackerRepository;

    /**
     * Constructor injection for the MonthlyTrackerRepository dependency.
     * 
     * @param monthlyTrackerRepository repository instance injected by Spring
     */
    @Autowired
    public MonthlyTrackerService(MonthlyTrackerRepository monthlyTrackerRepository) {
        this.monthlyTrackerRepository = monthlyTrackerRepository;
    }

    /**
     * Saves or updates a {@link MonthlyTracker} entry in the database.
     * 
     * @param tracker the {@link MonthlyTracker} entity to be saved or updated
     * @return the saved {@link MonthlyTracker} entity
     */
    public MonthlyTracker saveTracker(MonthlyTracker tracker) {
        return monthlyTrackerRepository.save(tracker);
    }

    /**
     * Retrieves all {@link MonthlyTracker} entries from the database.
     * 
     * @return list of all {@link MonthlyTracker} entities
     */
    public List<MonthlyTracker> getAllTrackers() {
        return monthlyTrackerRepository.findAll();
    }

    /**
     * Retrieves a specific {@link MonthlyTracker} by its unique ID.
     * 
     * @param id the unique ID of the tracker
     * @return an {@link Optional} containing the tracker if found
     */
    public Optional<MonthlyTracker> getTrackerById(Long id) {
        return monthlyTrackerRepository.findById(id);
    }

    /**
     * Deletes a {@link MonthlyTracker} entry by its ID.
     * 
     * @param id the unique ID of the tracker to be deleted
     */
    public void deleteTracker(Long id) {
        monthlyTrackerRepository.deleteById(id);
    }

    /**
     * Retrieves all {@link MonthlyTracker} entries belonging to a specific user.
     * 
     * @param userId the ID of the user
     * @return list of {@link MonthlyTracker} entries linked to that user
     */
    public List<MonthlyTracker> getTrackersByUserId(Long userId) {
        return monthlyTrackerRepository.findByUserId(userId);
    }

    /**
     * Updates the goal (number of books to read) for a given tracker.
     * 
     * @param trackerId the ID of the tracker to be updated
     * @param newTarget the new target number of books
     * @return the updated {@link MonthlyTracker} entity
     * @throws IllegalArgumentException if no tracker is found with the given ID
     */
    public MonthlyTracker updateGoal(Long trackerId, int newTarget) {
        Optional<MonthlyTracker> trackerOpt = monthlyTrackerRepository.findById(trackerId);

        if (trackerOpt.isPresent()) {
            MonthlyTracker tracker = trackerOpt.get();
            tracker.setTargetBooksNum(newTarget);
            return monthlyTrackerRepository.save(tracker);
        } else {
            throw new IllegalArgumentException("Tracker not found with ID: " + trackerId);
        }
    }

    /**
     * Retrieves a tracker for a specific user, month, and year.
     * 
     * @param userId the ID of the user
     * @param month  the month enum
     * @param year   the year as a string
     * @return an {@link Optional} containing the tracker if found
     */
    public Optional<MonthlyTracker> getTrackerByUserAndMonth(Long userId, com.bookbuddy.model.Months month,
            String year) {
        List<MonthlyTracker> trackers = monthlyTrackerRepository.findByUserId(userId);

        for (MonthlyTracker tracker : trackers) {
            if (tracker.getMonth().equals(month) && tracker.getYear().equals(year)) {
                return Optional.of(tracker);
            }
        }

        return Optional.empty();
    }

    /**
     * Gets or creates a tracker for the current month.
     * 
     * @param userId      the ID of the user
     * @param targetBooks the target number of books if creating new tracker
     * @return the existing or newly created {@link MonthlyTracker}
     */
    public MonthlyTracker getOrCreateCurrentMonthTracker(Long userId, int targetBooks) {
        java.time.LocalDate now = java.time.LocalDate.now();
        int currentMonth = now.getMonthValue();
        String currentYear = String.valueOf(now.getYear());

        com.bookbuddy.model.Months monthEnum = com.bookbuddy.model.Months.fromValue(currentMonth);

        Optional<MonthlyTracker> existing = getTrackerByUserAndMonth(userId, monthEnum, currentYear);

        if (existing.isPresent()) {
            return existing.get();
        }

        // Create new tracker - note: user must be fetched by caller
        throw new IllegalStateException("User must be provided to create new tracker");
    }

    /**
     * Calculates progress statistics for a tracker.
     * 
     * @param trackerId the ID of the tracker
     * @return a {@link com.bookbuddy.dto.TrackerProgressDTO} with progress
     *         information
     * @throws IllegalArgumentException if tracker not found
     */
    public com.bookbuddy.dto.TrackerProgressDTO calculateProgress(Long trackerId) {
        Optional<MonthlyTracker> trackerOpt = monthlyTrackerRepository.findById(trackerId);

        if (trackerOpt.isEmpty()) {
            throw new IllegalArgumentException("Tracker not found with ID: " + trackerId);
        }

        MonthlyTracker tracker = trackerOpt.get();
        int totalBooks = tracker.getGoalBooks().size();

        int completedBooks = 0;
        for (com.bookbuddy.model.MonthlyTrackerBook book : tracker.getGoalBooks()) {
            if (book.isCompleted()) {
                completedBooks++;
            }
        }

        double percentage = 0.0;
        if (tracker.getTargetBooksNum() > 0) {
            percentage = (completedBooks * 100.0) / tracker.getTargetBooksNum();
        }

        com.bookbuddy.dto.TrackerProgressDTO progress = new com.bookbuddy.dto.TrackerProgressDTO();
        progress.setTrackerId(trackerId);
        progress.setTargetBooks(tracker.getTargetBooksNum());
        progress.setTotalBooks(totalBooks);
        progress.setCompletedBooks(completedBooks);
        progress.setCompletionPercentage(Math.round(percentage * 100.0) / 100.0);
        progress.setMonth(tracker.getMonth().name());
        progress.setYear(tracker.getYear());

        return progress;
    }
}
