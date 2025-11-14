package com.bookbuddy.controller;

import com.bookbuddy.dto.OpenLibrarySearchResponse;
import com.bookbuddy.service.OpenLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling book searches through the Open Library API.
 */
@RestController
@RequestMapping("/api/openlibrary")
// @CrossOrigin
public class OpenLibraryController {

    private final OpenLibraryService openLibraryService;

    @Autowired
    public OpenLibraryController(OpenLibraryService openLibraryService) {
        this.openLibraryService = openLibraryService;
    }

    /**
     * Search books using a general text query.
     *
     * @param q     search text
     * @param limit maximum number of results
     * @param page  page number for pagination
     * @return search results returned by Open Library
     */
    @GetMapping
    public ResponseEntity<OpenLibrarySearchResponse> searchBooks(
            @RequestParam String q,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer page) {

        OpenLibrarySearchResponse response =
                openLibraryService.searchBooks(q, limit, page);

        return ResponseEntity.ok(response);
    }

    /**
     * Search books using title only.
     *
     * @param title title of the book
     * @param limit maximum number of results
     * @return search results returned by Open Library
     */
    @GetMapping("/by-title")
    public ResponseEntity<OpenLibrarySearchResponse> searchByTitle(
            @RequestParam String title,
            @RequestParam(required = false) Integer limit) {

        OpenLibrarySearchResponse response =
                openLibraryService.searchByTitle(title, limit);

        return ResponseEntity.ok(response);
    }

    /**
     * Search books using author name only.
     *
     * @param author author name
     * @param limit  maximum number of results
     * @return search results returned by Open Library
     */
    @GetMapping("/by-author")
    public ResponseEntity<OpenLibrarySearchResponse> searchByAuthor(
            @RequestParam String author,
            @RequestParam(required = false) Integer limit) {

        OpenLibrarySearchResponse response =
                openLibraryService.searchByAuthor(author, limit);

        return ResponseEntity.ok(response);
    }
}
