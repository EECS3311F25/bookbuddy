package com.bookbuddy.controller;

import com.bookbuddy.model.MonthlyTracker;
import com.bookbuddy.service.MonthlyTrackerService;
import com.bookbuddy.service.UserService;
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
// @CrossOrigin
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
     * @param tracker new tracker data
     * @return created tracker
     */
    @PostMapping
    public ResponseEntity<?> createTracker(@RequestBody MonthlyTracker tracker) {

        Optional<?> user = userService.getUserById(tracker.getUser().getId());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }

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
}
