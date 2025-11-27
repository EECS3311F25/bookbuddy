package com.bookbuddy.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * DTO for adding multiple books to a monthly tracker at once.
 */
public class BulkTrackerBookRequest {

    @NotNull(message = "Monthly Tracker ID is required")
    private Long monthlyTrackerId;

    @NotEmpty(message = "At least one book ID is required")
    private List<Long> userBookIds;

    // Constructors
    public BulkTrackerBookRequest() {
    }

    public BulkTrackerBookRequest(Long monthlyTrackerId, List<Long> userBookIds) {
        this.monthlyTrackerId = monthlyTrackerId;
        this.userBookIds = userBookIds;
    }

    // Getters and Setters
    public Long getMonthlyTrackerId() {
        return monthlyTrackerId;
    }

    public void setMonthlyTrackerId(Long monthlyTrackerId) {
        this.monthlyTrackerId = monthlyTrackerId;
    }

    public List<Long> getUserBookIds() {
        return userBookIds;
    }

    public void setUserBookIds(List<Long> userBookIds) {
        this.userBookIds = userBookIds;
    }
}
