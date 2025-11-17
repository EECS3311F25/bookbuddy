package com.bookbuddy.dto;

import java.util.List;

/**
 * Clean search response wrapper for frontend
 */
public class SearchResponseDTO {

    private int totalResults;
    private int currentPage;
    private List<BookSearchResultDTO> books;

    public SearchResponseDTO() {
    }

    public SearchResponseDTO(int totalResults, int currentPage, List<BookSearchResultDTO> books) {
        this.totalResults = totalResults;
        this.currentPage = currentPage;
        this.books = books;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<BookSearchResultDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookSearchResultDTO> books) {
        this.books = books;
    }
}
