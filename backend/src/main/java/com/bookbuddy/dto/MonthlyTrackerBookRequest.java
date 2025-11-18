package com.bookbuddy.dto;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for adding a book to a Monthly Tracker.
 */
public class MonthlyTrackerBookRequest {

    @NotNull(message = "Monthly Tracker ID is required")
    private Long monthlyTrackerId;

    @NotNull(message = "User Book ID is required")
    private Long userBookId;

    // Constructors
    public MonthlyTrackerBookRequest() {
    }

    public MonthlyTrackerBookRequest(Long monthlyTrackerId, Long userBookId) {
        this.monthlyTrackerId = monthlyTrackerId;
        this.userBookId = userBookId;
    }

    // Getters and Setters
    public Long getMonthlyTrackerId() {
        return monthlyTrackerId;
    }

    public void setMonthlyTrackerId(Long monthlyTrackerId) {
        this.monthlyTrackerId = monthlyTrackerId;
    }

    public Long getUserBookId() {
        return userBookId;
    }

    public void setUserBookId(Long userBookId) {
        this.userBookId = userBookId;
    }
}
