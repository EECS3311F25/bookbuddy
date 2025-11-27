package com.bookbuddy.service;

import com.bookbuddy.model.MonthlyTrackerBook;
import com.bookbuddy.repository.MonthlyTrackerBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing {@link MonthlyTrackerBook} entities.
 *
 * This class connects monthly reading trackers to individual
 * {@link com.bookbuddy.model.UserBook} entries, enabling goal tracking
 * and completion updates. It provides methods to save, retrieve,
 * and count tracked books for each user’s reading goal.
 *
 * Main Tasks in MonthlyTrackerBookService:
 * 
 * 1. Save and update tracker book records
 * 2. Retrieve all tracker books or by tracker ID
 * 3. Delete books from monthly tracker
 * 4. Count completed books in a tracker
 * 
 */
@Service
public class MonthlyTrackerBookService {

    private final MonthlyTrackerBookRepository monthlyTrackerBookRepository;

    /**
     * Constructor injection for the MonthlyTrackerBookRepository dependency.
     * 
     * @param monthlyTrackerBookRepository repository instance injected by Spring
     */
    @Autowired
    public MonthlyTrackerBookService(MonthlyTrackerBookRepository monthlyTrackerBookRepository) {
        this.monthlyTrackerBookRepository = monthlyTrackerBookRepository;
    }

    /**
     * Saves or updates a {@link MonthlyTrackerBook} record.
     * 
     * @param trackerBook the {@link MonthlyTrackerBook} to be saved or updated
     * @return the saved {@link MonthlyTrackerBook} entity
     */
    public MonthlyTrackerBook saveMonthlyTrackerBook(MonthlyTrackerBook trackerBook) {
        return monthlyTrackerBookRepository.save(trackerBook);
    }

    /**
     * Retrieves all {@link MonthlyTrackerBook} records from the database.
     * 
     * @return list of all {@link MonthlyTrackerBook} entities
     */
    public List<MonthlyTrackerBook> getAllMonthlyTrackerBooks() {
        return monthlyTrackerBookRepository.findAll();
    }

    /**
     * Retrieves a specific {@link MonthlyTrackerBook} by its ID.
     * 
     * @param id the unique ID of the tracker book
     * @return an {@link Optional} containing the tracker book if found
     */
    public Optional<MonthlyTrackerBook> getMonthlyTrackerBookById(Long id) {
        return monthlyTrackerBookRepository.findById(id);
    }

    /**
     * Deletes a {@link MonthlyTrackerBook} record from the database by ID.
     * 
     * @param id the unique ID of the record to be deleted
     */
    public void deleteMonthlyTrackerBook(Long id) {
        monthlyTrackerBookRepository.deleteById(id);
    }

    /**
     * Retrieves all {@link MonthlyTrackerBook} entries linked to a specific monthly
     * tracker.
     * 
     * @param trackerId the ID of the {@link com.bookbuddy.model.MonthlyTracker}
     * @return list of {@link MonthlyTrackerBook} entries belonging to the tracker
     */
    public List<MonthlyTrackerBook> getByTrackerId(Long trackerId) {
        return monthlyTrackerBookRepository.findByMonthlyTrackerId(trackerId);
    }

    /**
     * Counts the number of completed books in a specific monthly tracker.
     * 
     * @param trackerId the ID of the tracker
     * @return the number of completed books
     */
    public long countCompletedBooks(Long trackerId) {
        List<MonthlyTrackerBook> trackerBooks = monthlyTrackerBookRepository.findByMonthlyTrackerId(trackerId);

        int count = 0;
        for (MonthlyTrackerBook book : trackerBooks) {
            if (book.isCompleted()) {
                count++;
            }
        }

        return count;
    }

    /**
     * Checks if a specific user book is already in a tracker.
     * 
     * @param trackerId  the ID of the tracker
     * @param userBookId the ID of the user book
     * @return true if the book is already in the tracker
     */
    public boolean isBookInTracker(Long trackerId, Long userBookId) {
        List<MonthlyTrackerBook> trackerBooks = monthlyTrackerBookRepository.findByMonthlyTrackerId(trackerId);

        for (MonthlyTrackerBook trackerBook : trackerBooks) {
            if (trackerBook.getUserBook() != null
                    && trackerBook.getUserBook().getId().equals(userBookId)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Retrieves books from a tracker filtered by completion status.
     * 
     * @param trackerId the ID of the tracker
     * @param completed true to get completed books, false for incomplete
     * @return list of {@link MonthlyTrackerBook} entries matching the status
     */
    public List<MonthlyTrackerBook> getBooksWithStatus(Long trackerId, boolean completed) {
        List<MonthlyTrackerBook> allBooks = monthlyTrackerBookRepository.findByMonthlyTrackerId(trackerId);

        List<MonthlyTrackerBook> filtered = new java.util.ArrayList<>();
        for (MonthlyTrackerBook book : allBooks) {
            if (book.isCompleted() == completed) {
                filtered.add(book);
            }
        }

        return filtered;
    }
}
