package com.bookbuddy.controller;

import com.bookbuddy.dto.BookSearchResultDTO;
import com.bookbuddy.dto.OpenLibrarySearchResponse;
import com.bookbuddy.dto.SearchResponseDTO;
import com.bookbuddy.service.OpenLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<SearchResponseDTO> searchBooks(
            @RequestParam String q,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer page) {

        OpenLibrarySearchResponse rawResponse = openLibraryService.searchBooks(q, limit, page);
        SearchResponseDTO cleanResponse = mapToCleanResponse(rawResponse, page);
        return ResponseEntity.ok(cleanResponse);
    }

    /**
     * Search books using title only.
     *
     * @param title title of the book
     * @param limit maximum number of results
     * @return search results returned by Open Library
     */
    @GetMapping("/by-title")
    public ResponseEntity<SearchResponseDTO> searchByTitle(
            @RequestParam String title,
            @RequestParam(required = false) Integer limit) {

        OpenLibrarySearchResponse rawResponse = openLibraryService.searchByTitle(title, limit);
        SearchResponseDTO cleanResponse = mapToCleanResponse(rawResponse, 1);
        return ResponseEntity.ok(cleanResponse);
    }

    /**
     * Search books using author name only.
     *
     * @param author author name
     * @param limit  maximum number of results
     * @return search results returned by Open Library
     */
    @GetMapping("/by-author")
    public ResponseEntity<SearchResponseDTO> searchByAuthor(
            @RequestParam String author,
            @RequestParam(required = false) Integer limit) {

        OpenLibrarySearchResponse rawResponse = openLibraryService.searchByAuthor(author, limit);
        SearchResponseDTO cleanResponse = mapToCleanResponse(rawResponse, 1);
        return ResponseEntity.ok(cleanResponse);
    }

    /**
     * Maps raw Open Library response to clean DTO
     */
    private SearchResponseDTO mapToCleanResponse(OpenLibrarySearchResponse rawResponse, Integer page) {
        List<BookSearchResultDTO> cleanBooks = rawResponse.getDocs().stream()
                .map(this::mapToBookSearchResult)
                .collect(Collectors.toList());

        return new SearchResponseDTO(
                rawResponse.getNumFound(),
                page != null ? page : 1,
                cleanBooks);
    }

    /**
     * Maps single OpenLibraryBook to clean BookSearchResultDTO
     */
    private BookSearchResultDTO mapToBookSearchResult(OpenLibrarySearchResponse.OpenLibraryBook book) {
        return new BookSearchResultDTO(
                extractOpenLibraryId(book.getKey()),
                book.getTitle(),
                book.getFirstAuthor(),
                book.getCoverUrl(),
                book.getFirstPublishYear());
    }

    /**
     * Extracts clean Open Library ID from key
     * Example: "/works/OL27448W" -> "OL27448W"
     */
    private String extractOpenLibraryId(String key) {
        if (key == null || key.isEmpty()) {
            return "";
        }

        // Extract the ID after the last "/"
        int lastSlash = key.lastIndexOf('/');
        if (lastSlash != -1 && lastSlash < key.length() - 1) {
            String id = key.substring(lastSlash + 1);
            // Verify it starts with "O"
            if (id.startsWith("O")) {
                return id;
            }
        }

        // Fallback: return original key if parsing fails
        return key;
    }
}
