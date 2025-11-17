package com.bookbuddy.controller;

import com.bookbuddy.model.MonthlyTracker;
import com.bookbuddy.model.MonthlyTrackerBook;
import com.bookbuddy.model.UserBook;
import com.bookbuddy.service.MonthlyTrackerBookService;
import com.bookbuddy.service.MonthlyTrackerService;
import com.bookbuddy.service.UserBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing books inside a monthly reading tracker.
 */
@RestController
@RequestMapping("/api/monthly-tracker-books")
//@CrossOrigin
public class MonthlyTrackerBookController {

    private final MonthlyTrackerBookService trackerBookService;
    private final MonthlyTrackerService monthlyTrackerService;
    private final UserBookService userBookService;

    @Autowired
    public MonthlyTrackerBookController(
            MonthlyTrackerBookService trackerBookService,
            MonthlyTrackerService monthlyTrackerService,
            UserBookService userBookService) {
        this.trackerBookService = trackerBookService;
        this.monthlyTrackerService = monthlyTrackerService;
        this.userBookService = userBookService;
    }

    /**
     * Add a UserBook to a MonthlyTracker.
     *
     * @param trackerId ID of the monthly tracker
     * @param userBookId ID of the user's book
     * @return created MonthlyTrackerBook record
     */
    @PostMapping
    public ResponseEntity<?> addBookToTracker(
            @RequestParam Long trackerId,
            @RequestParam Long userBookId) {

        Optional<MonthlyTracker> tracker = monthlyTrackerService.getTrackerById(trackerId);
        if (tracker.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("MonthlyTracker not found with id: " + trackerId);
        }

        Optional<UserBook> userBook = userBookService.getUserBookById(userBookId);
        if (userBook.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("UserBook not found with id: " + userBookId);
        }

        MonthlyTrackerBook trackerBook = new MonthlyTrackerBook(tracker.get(), userBook.get());
        MonthlyTrackerBook saved = trackerBookService.saveMonthlyTrackerBook(trackerBook);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Get all books inside a monthly tracker.
     *
     * @param trackerId ID of the tracker
     * @return list of MonthlyTrackerBook records
     */
    @GetMapping("/tracker/{trackerId}")
    public ResponseEntity<List<MonthlyTrackerBook>> getBooksInTracker(@PathVariable Long trackerId) {
        List<MonthlyTrackerBook> books = trackerBookService.getByTrackerId(trackerId);
        return ResponseEntity.ok(books);
    }

    /**
     * Mark a tracker book as completed.
     *
     * @param id ID of the MonthlyTrackerBook
     * @return updated record
     */
    @PutMapping("/{id}/complete")
    public ResponseEntity<?> markAsCompleted(@PathVariable Long id) {
        Optional<MonthlyTrackerBook> trackerBook = trackerBookService.getMonthlyTrackerBookById(id);

        if (trackerBook.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("TrackerBook not found with id: " + id);
        }

        MonthlyTrackerBook book = trackerBook.get();
        book.setCompleted(true);

        MonthlyTrackerBook updated = trackerBookService.saveMonthlyTrackerBook(book);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a MonthlyTrackerBook.
     *
     * @param id ID of the record
     * @return success or error message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrackerBook(@PathVariable Long id) {
        Optional<MonthlyTrackerBook> trackerBook = trackerBookService.getMonthlyTrackerBookById(id);

        if (trackerBook.isPresent()) {
            trackerBookService.deleteMonthlyTrackerBook(id);
            return ResponseEntity.ok("Tracker book deleted");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Tracker book not found with id: " + id);
    }
}
