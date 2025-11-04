package com.bookbuddy.service;

import com.bookbuddy.dto.OpenLibrarySearchResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Service for integrating with Open Library Search API
 * https://openlibrary.org/dev/docs/api/search
 */
@Service
public class OpenLibraryService {

    private static final String BASE_URL = "https://openlibrary.org/search.json";
    private final RestTemplate restTemplate;

    public OpenLibraryService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Search for books on Open Library
     * @param query Search query string
     * @param limit Maximum number of results (default 10)
     * @param page Page number for pagination (starts at 1)
     * @return OpenLibrarySearchResponse with search results
     */
    public OpenLibrarySearchResponse searchBooks(String query, Integer limit, Integer page) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("q", query)
                .queryParam("limit", limit != null ? limit : 10)
                .queryParam("page", page != null ? page : 1)
                .queryParam("fields", "key,title,author_name,first_publish_year,cover_i,isbn,edition_count")
                .build()
                .toUriString();

        return restTemplate.getForObject(url, OpenLibrarySearchResponse.class);
    }

    /**
     * Search books by title
     */
    public OpenLibrarySearchResponse searchByTitle(String title, Integer limit) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("title", title)
                .queryParam("limit", limit != null ? limit : 10)
                .queryParam("fields", "key,title,author_name,first_publish_year,cover_i,isbn,edition_count")
                .build()
                .toUriString();

        return restTemplate.getForObject(url, OpenLibrarySearchResponse.class);
    }

    /**
     * Search books by author
     */
    public OpenLibrarySearchResponse searchByAuthor(String author, Integer limit) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("author", author)
                .queryParam("limit", limit != null ? limit : 10)
                .queryParam("fields", "key,title,author_name,first_publish_year,cover_i,isbn,edition_count")
                .build()
                .toUriString();

        return restTemplate.getForObject(url, OpenLibrarySearchResponse.class);
    }
}
