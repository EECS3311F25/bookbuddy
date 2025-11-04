package com.bookbuddy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Response wrapper for Open Library search API results
 */
public class OpenLibrarySearchResponse {

    @JsonProperty("num_found")
    private int numFound;

    @JsonProperty("start")
    private int start;

    @JsonProperty("docs")
    private List<OpenLibraryBook> docs;

    public OpenLibrarySearchResponse() {}

    public int getNumFound() {
        return numFound;
    }

    public void setNumFound(int numFound) {
        this.numFound = numFound;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<OpenLibraryBook> getDocs() {
        return docs;
    }

    public void setDocs(List<OpenLibraryBook> docs) {
        this.docs = docs;
    }

    /**
     * Represents a single book result from Open Library API
     */
    public static class OpenLibraryBook {

        @JsonProperty("key")
        private String key;

        @JsonProperty("title")
        private String title;

        @JsonProperty("author_name")
        private List<String> authorName;

        @JsonProperty("first_publish_year")
        private Integer firstPublishYear;

        @JsonProperty("cover_i")
        private Long coverId;

        @JsonProperty("isbn")
        private List<String> isbn;

        @JsonProperty("edition_count")
        private Integer editionCount;

        public OpenLibraryBook() {}

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getAuthorName() {
            return authorName;
        }

        public void setAuthorName(List<String> authorName) {
            this.authorName = authorName;
        }

        public Integer getFirstPublishYear() {
            return firstPublishYear;
        }

        public void setFirstPublishYear(Integer firstPublishYear) {
            this.firstPublishYear = firstPublishYear;
        }

        public Long getCoverId() {
            return coverId;
        }

        public void setCoverId(Long coverId) {
            this.coverId = coverId;
        }

        public List<String> getIsbn() {
            return isbn;
        }

        public void setIsbn(List<String> isbn) {
            this.isbn = isbn;
        }

        public Integer getEditionCount() {
            return editionCount;
        }

        public void setEditionCount(Integer editionCount) {
            this.editionCount = editionCount;
        }

        public String getCoverUrl() {
            if (coverId != null) {
                return "https://covers.openlibrary.org/b/id/" + coverId + "-L.jpg";
            }
            return null;
        }

        public String getFirstAuthor() {
            if (authorName != null && !authorName.isEmpty()) {
                return authorName.get(0);
            }
            return "Unknown Author";
        }
    }
}
