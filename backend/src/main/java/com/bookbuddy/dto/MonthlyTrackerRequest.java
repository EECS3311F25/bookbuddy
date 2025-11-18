package com.bookbuddy.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for creating or updating a Monthly Tracker.
 */
public class MonthlyTrackerRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Year is required")
    @Min(value = 2000, message = "Year must be at least 2000")
    @Max(value = 2100, message = "Year must be at most 2100")
    private Integer year;

    @NotNull(message = "Month is required")
    @Min(value = 1, message = "Month must be between 1 and 12")
    @Max(value = 12, message = "Month must be between 1 and 12")
    private Integer month;

    @NotNull(message = "Monthly goal is required")
    @Min(value = 1, message = "Monthly goal must be at least 1")
    private Integer monthlyGoal;

    // Constructors
    public MonthlyTrackerRequest() {
    }

    public MonthlyTrackerRequest(Long userId, Integer year, Integer month, Integer monthlyGoal) {
        this.userId = userId;
        this.year = year;
        this.month = month;
        this.monthlyGoal = monthlyGoal;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getMonthlyGoal() {
        return monthlyGoal;
    }

    public void setMonthlyGoal(Integer monthlyGoal) {
        this.monthlyGoal = monthlyGoal;
    }
}
