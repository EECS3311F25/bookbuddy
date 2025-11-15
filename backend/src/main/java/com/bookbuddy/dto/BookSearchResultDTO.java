package com.bookbuddy.dto;

/**
 * Clean DTO for search results returned to frontend
 * Matches BookCatalog structure for easy integration
 */
public class BookSearchResultDTO {

    private String openLibraryId;
    private String title;
    private String author;
    private String coverUrl;
    private Integer publishYear;

    public BookSearchResultDTO() {
    }

    public BookSearchResultDTO(String openLibraryId, String title, String author, String coverUrl,
            Integer publishYear) {
        this.openLibraryId = openLibraryId;
        this.title = title;
        this.author = author;
        this.coverUrl = coverUrl;
        this.publishYear = publishYear;
    }

    public String getOpenLibraryId() {
        return openLibraryId;
    }

    public void setOpenLibraryId(String openLibraryId) {
        this.openLibraryId = openLibraryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Integer getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }
}