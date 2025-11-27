package com.bookbuddy.controller;

import com.bookbuddy.dto.MonthlyTrackerBookRequest;
import com.bookbuddy.model.MonthlyTracker;
import com.bookbuddy.model.MonthlyTrackerBook;
import com.bookbuddy.model.UserBook;
import com.bookbuddy.service.MonthlyTrackerBookService;
import com.bookbuddy.service.MonthlyTrackerService;
import com.bookbuddy.service.UserBookService;
import jakarta.validation.Valid;
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
@CrossOrigin(origins = "http://localhost:5173")
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
     * @param request contains tracker ID and user book ID
     * @return created MonthlyTrackerBook record
     */
    @PostMapping
    public ResponseEntity<?> addBookToTracker(@Valid @RequestBody MonthlyTrackerBookRequest request) {

        Optional<MonthlyTracker> tracker = monthlyTrackerService.getTrackerById(request.getMonthlyTrackerId());
        if (tracker.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("MonthlyTracker not found with id: " + request.getMonthlyTrackerId());
        }

        Optional<UserBook> userBook = userBookService.getUserBookById(request.getUserBookId());
        if (userBook.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("UserBook not found with id: " + request.getUserBookId());
        }

        // Check if book is already in tracker
        if (trackerBookService.isBookInTracker(request.getMonthlyTrackerId(), request.getUserBookId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Book is already in this tracker");
        }

        // Validate book belongs to same user as tracker
        if (!java.util.Objects.equals(userBook.get().getUser().getId(), tracker.get().getUser().getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Book does not belong to the same user as the tracker");
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

        // Automatically update the UserBook shelf to READ if not already
        UserBook userBook = book.getUserBook();
        if (userBook != null && userBook.getShelf() != com.bookbuddy.model.ShelfStatus.READ) {
            userBook.setShelf(com.bookbuddy.model.ShelfStatus.READ);
            userBookService.saveUserBook(userBook);
        }

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

    /**
     * Check if a book is already in a tracker.
     *
     * @param trackerId  ID of the tracker
     * @param userBookId ID of the user book
     * @return true if book is in tracker, false otherwise
     */
    @GetMapping("/tracker/{trackerId}/contains/{userBookId}")
    public ResponseEntity<Boolean> containsBook(
            @PathVariable Long trackerId,
            @PathVariable Long userBookId) {
        boolean contains = trackerBookService.isBookInTracker(trackerId, userBookId);
        return ResponseEntity.ok(contains);
    }

    /**
     * Add multiple books to a tracker at once.
     *
     * @param request contains tracker ID and list of user book IDs
     * @return list of created MonthlyTrackerBook records
     */
    @PostMapping("/bulk")
    public ResponseEntity<?> addBooksToTracker(@Valid @RequestBody com.bookbuddy.dto.BulkTrackerBookRequest request) {

        Optional<MonthlyTracker> tracker = monthlyTrackerService.getTrackerById(request.getMonthlyTrackerId());
        if (tracker.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("MonthlyTracker not found with id: " + request.getMonthlyTrackerId());
        }

        java.util.List<MonthlyTrackerBook> addedBooks = new java.util.ArrayList<>();
        java.util.List<String> errors = new java.util.ArrayList<>();

        for (Long userBookId : request.getUserBookIds()) {
            // Check if book exists
            Optional<UserBook> userBook = userBookService.getUserBookById(userBookId);
            if (userBook.isEmpty()) {
                errors.add("UserBook not found with id: " + userBookId);
                continue;
            }

            // Check if already in tracker
            if (trackerBookService.isBookInTracker(request.getMonthlyTrackerId(), userBookId)) {
                errors.add("Book " + userBookId + " is already in tracker");
                continue;
            }

            // Validate same user
            if (!java.util.Objects.equals(userBook.get().getUser().getId(), tracker.get().getUser().getId())) {
                errors.add("Book " + userBookId + " does not belong to the same user");
                continue;
            }

            // Add book
            MonthlyTrackerBook trackerBook = new MonthlyTrackerBook(tracker.get(), userBook.get());
            MonthlyTrackerBook saved = trackerBookService.saveMonthlyTrackerBook(trackerBook);
            addedBooks.add(saved);
        }

        // Return results
        java.util.Map<String, Object> response = new java.util.HashMap<>();
        response.put("added", addedBooks);
        response.put("errors", errors);
        response.put("successCount", addedBooks.size());
        response.put("errorCount", errors.size());

        return ResponseEntity.ok(response);
    }
}
