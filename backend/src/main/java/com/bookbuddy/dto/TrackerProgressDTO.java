package com.bookbuddy.dto;

/**
 * DTO for monthly tracker progress information.
 * Provides a summary of reading progress for a specific tracker.
 */
public class TrackerProgressDTO {

    private Long trackerId;
    private int targetBooks;
    private int totalBooks;
    private int completedBooks;
    private double completionPercentage;
    private String month;
    private String year;

    // Default constructor
    public TrackerProgressDTO() {
    }

    // Full constructor
    public TrackerProgressDTO(Long trackerId, int targetBooks, int totalBooks,
            int completedBooks, double completionPercentage,
            String month, String year) {
        this.trackerId = trackerId;
        this.targetBooks = targetBooks;
        this.totalBooks = totalBooks;
        this.completedBooks = completedBooks;
        this.completionPercentage = completionPercentage;
        this.month = month;
        this.year = year;
    }

    // Getters and Setters
    public Long getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(Long trackerId) {
        this.trackerId = trackerId;
    }

    public int getTargetBooks() {
        return targetBooks;
    }

    public void setTargetBooks(int targetBooks) {
        this.targetBooks = targetBooks;
    }

    public int getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(int totalBooks) {
        this.totalBooks = totalBooks;
    }

    public int getCompletedBooks() {
        return completedBooks;
    }

    public void setCompletedBooks(int completedBooks) {
        this.completedBooks = completedBooks;
    }

    public double getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(double completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
