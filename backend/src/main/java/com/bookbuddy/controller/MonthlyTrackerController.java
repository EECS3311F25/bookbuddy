package com.bookbuddy.controller;

import com.bookbuddy.dto.MonthlyTrackerRequest;
import com.bookbuddy.model.MonthlyTracker;
import com.bookbuddy.model.Months;
import com.bookbuddy.model.User;
import com.bookbuddy.service.MonthlyTrackerService;
import com.bookbuddy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing monthly reading trackers for users.
 */
@RestController
@RequestMapping("/api/monthly-tracker")
@CrossOrigin(origins = "http://localhost:5173")
public class MonthlyTrackerController {

    private final MonthlyTrackerService monthlyTrackerService;
    private final UserService userService;

    @Autowired
    public MonthlyTrackerController(
            MonthlyTrackerService monthlyTrackerService,
            UserService userService) {
        this.monthlyTrackerService = monthlyTrackerService;
        this.userService = userService;
    }

    /**
     * Create a new monthly tracker.
     *
     * @param request new tracker data
     * @return created tracker
     */
    @PostMapping
    public ResponseEntity<?> createTracker(@Valid @RequestBody MonthlyTrackerRequest request) {

        Optional<User> userOpt = userService.getUserById(request.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with id: " + request.getUserId());
        }

        User user = userOpt.get();

        // Convert month number to Months enum
        Months monthEnum;
        try {
            monthEnum = Months.fromValue(request.getMonth());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid month number: " + request.getMonth());
        }

        // Check for duplicate tracker
        Optional<MonthlyTracker> existing = monthlyTrackerService.getTrackerByUserAndMonth(
                request.getUserId(), monthEnum, String.valueOf(request.getYear()));
        if (existing.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Tracker already exists for " + monthEnum.name() + " " + request.getYear());
        }

        // Validate minimum goal
        if (request.getMonthlyGoal() < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Monthly goal must be at least 1");
        }

        MonthlyTracker tracker = new MonthlyTracker(user, monthEnum);
        tracker.setYear(String.valueOf(request.getYear()));
        tracker.setTargetBooksNum(request.getMonthlyGoal());

        MonthlyTracker savedTracker = monthlyTrackerService.saveTracker(tracker);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTracker);
    }

    /**
     * Get all monthly trackers.
     *
     * @return list of all trackers
     */
    @GetMapping
    public ResponseEntity<List<MonthlyTracker>> getAllTrackers() {
        List<MonthlyTracker> trackers = monthlyTrackerService.getAllTrackers();
        return ResponseEntity.ok(trackers);
    }

    /**
     * Get a tracker by ID.
     *
     * @param id tracker ID
     * @return tracker or error message
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTrackerById(@PathVariable Long id) {
        Optional<MonthlyTracker> tracker = monthlyTrackerService.getTrackerById(id);

        if (tracker.isPresent()) {
            return ResponseEntity.ok(tracker.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Tracker not found with id: " + id);
    }

    /**
     * Get all trackers for a specific user.
     *
     * @param userId ID of the user
     * @return list of trackers
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getTrackersByUser(@PathVariable Long userId) {

        Optional<?> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with id: " + userId);
        }

        List<MonthlyTracker> trackers = monthlyTrackerService.getTrackersByUserId(userId);
        return ResponseEntity.ok(trackers);
    }

    /**
     * Update the goal (number of target books) for a tracker.
     *
     * @param id        tracker ID
     * @param newTarget new goal value
     * @return updated tracker
     */
    @PutMapping("/{id}/goal")
    public ResponseEntity<?> updateGoal(
            @PathVariable Long id,
            @RequestParam int newTarget) {

        try {
            MonthlyTracker updated = monthlyTrackerService.updateGoal(id, newTarget);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tracker not found with id: " + id);
        }
    }

    /**
     * Delete a monthly tracker.
     *
     * @param id tracker ID
     * @return success or error message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTracker(@PathVariable Long id) {
        Optional<MonthlyTracker> tracker = monthlyTrackerService.getTrackerById(id);

        if (tracker.isPresent()) {
            monthlyTrackerService.deleteTracker(id);
            return ResponseEntity.ok("Tracker deleted");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Tracker not found with id: " + id);
    }

    /**
     * Get tracker progress statistics.
     *
     * @param id tracker ID
     * @return progress information
     */
    @GetMapping("/{id}/progress")
    public ResponseEntity<?> getProgress(@PathVariable Long id) {
        try {
            com.bookbuddy.dto.TrackerProgressDTO progress = monthlyTrackerService.calculateProgress(id);
            return ResponseEntity.ok(progress);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tracker not found with id: " + id);
        }
    }

    /**
     * Get tracker for current month for a user.
     *
     * @param userId user ID
     * @return current month tracker or 404 if not found
     */
    @GetMapping("/user/{userId}/current")
    public ResponseEntity<?> getCurrentMonthTracker(@PathVariable Long userId) {
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with id: " + userId);
        }

        java.time.LocalDate now = java.time.LocalDate.now();
        int currentMonth = now.getMonthValue();
        String currentYear = String.valueOf(now.getYear());

        Months monthEnum = Months.fromValue(currentMonth);
        Optional<MonthlyTracker> tracker = monthlyTrackerService.getTrackerByUserAndMonth(
                userId, monthEnum, currentYear);

        if (tracker.isPresent()) {
            return ResponseEntity.ok(tracker.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No tracker found for current month");
    }

    /**
     * Get tracker for specific month and year.
     *
     * @param userId user ID
     * @param month  month number (1-12)
     * @param year   year
     * @return tracker or 404 if not found
     */
    @GetMapping("/user/{userId}/month")
    public ResponseEntity<?> getTrackerByMonth(
            @PathVariable Long userId,
            @RequestParam int month,
            @RequestParam int year) {

        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with id: " + userId);
        }

        Months monthEnum;
        try {
            monthEnum = Months.fromValue(month);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid month number: " + month);
        }

        Optional<MonthlyTracker> tracker = monthlyTrackerService.getTrackerByUserAndMonth(
                userId, monthEnum, String.valueOf(year));

        if (tracker.isPresent()) {
            return ResponseEntity.ok(tracker.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No tracker found for " + monthEnum.name() + " " + year);
    }
}
