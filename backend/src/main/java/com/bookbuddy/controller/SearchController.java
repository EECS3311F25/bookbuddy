package com.bookbuddy.controller;

import com.bookbuddy.dto.OpenLibrarySearchResponse;
import com.bookbuddy.service.OpenLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Open Library book search
 */
@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "http://localhost:5173")
public class SearchController {

    private final OpenLibraryService openLibraryService;

    @Autowired
    public SearchController(OpenLibraryService openLibraryService) {
        this.openLibraryService = openLibraryService;
    }

    /**
     * Search books using Open Library API
     * GET /api/search?q=lord+of+the+rings&limit=10&page=1
     */
    @GetMapping
    public ResponseEntity<OpenLibrarySearchResponse> searchBooks(
            @RequestParam String q,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer page) {

        OpenLibrarySearchResponse response = openLibraryService.searchBooks(q, limit, page);
        return ResponseEntity.ok(response);
    }

    /**
     * Search books by title
     * GET /api/search/by-title?title=lord+of+the+rings&limit=10
     */
    @GetMapping("/by-title")
    public ResponseEntity<OpenLibrarySearchResponse> searchByTitle(
            @RequestParam String title,
            @RequestParam(required = false) Integer limit) {

        OpenLibrarySearchResponse response = openLibraryService.searchByTitle(title, limit);
        return ResponseEntity.ok(response);
    }

    /**
     * Search books by author
     * GET /api/search/by-author?author=tolkien&limit=10
     */
    @GetMapping("/by-author")
    public ResponseEntity<OpenLibrarySearchResponse> searchByAuthor(
            @RequestParam String author,
            @RequestParam(required = false) Integer limit) {

        OpenLibrarySearchResponse response = openLibraryService.searchByAuthor(author, limit);
        return ResponseEntity.ok(response);
    }
}
