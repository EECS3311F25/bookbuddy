package com.bookbuddy.controller;

import com.bookbuddy.dto.BookCatalogRequest;
import com.bookbuddy.model.BookCatalog;
import com.bookbuddy.service.BookCatalogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for BookCatalog operations
 * Handles global book catalog (books available to all users)
 */
@RestController
@RequestMapping("/api/catalog")
@CrossOrigin(origins = "http://localhost:5173")
public class BookCatalogController {

    private final BookCatalogService bookCatalogService;

    @Autowired
    public BookCatalogController(BookCatalogService bookCatalogService) {
        this.bookCatalogService = bookCatalogService;
    }

    /**
     * Get all books in the catalog
     * GET /api/catalog
     */
    @GetMapping
    public ResponseEntity<List<BookCatalog>> getAllBooks() {
        List<BookCatalog> books = bookCatalogService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    /**
     * Get book by ID
     * GET /api/catalog/{bookId}
     */
    @GetMapping("/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable Long bookId) {
        Optional<BookCatalog> book = bookCatalogService.getBookById(bookId);
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Book not found with id: " + bookId);
        }
    }

    /**
     * Add a new book to the catalog
     * POST /api/catalog
     */
    @PostMapping
    public ResponseEntity<?> addBook(@Valid @RequestBody BookCatalogRequest request) {
        BookCatalog book = new BookCatalog(request.getTitle(), request.getAuthor());
        book.setDescription(request.getDescription());
        book.setCoverUrl(request.getCoverUrl());
        book.setOpenLibraryId(request.getOpenLibraryId());
        book.setGenre(request.getGenre());

        BookCatalog savedBook = bookCatalogService.saveBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    /**
     * Update an existing book in the catalog
     * PUT /api/catalog/{bookId}
     */
    @PutMapping("/{bookId}")
    public ResponseEntity<?> updateBook(@PathVariable Long bookId, @Valid @RequestBody BookCatalogRequest request) {
        Optional<BookCatalog> existingBook = bookCatalogService.getBookById(bookId);

        if (existingBook.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Book not found with id: " + bookId);
        }

        BookCatalog book = existingBook.get();
        book.UpdateDetails(request.getTitle(), request.getAuthor(), request.getDescription());
        book.setCoverUrl(request.getCoverUrl());
        book.setOpenLibraryId(request.getOpenLibraryId());
        book.setGenre(request.getGenre());

        BookCatalog updatedBook = bookCatalogService.saveBook(book);
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * Delete a book from the catalog
     * DELETE /api/catalog/{bookId}
     */
    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        Optional<BookCatalog> book = bookCatalogService.getBookById(bookId);
        if (book.isPresent()) {
            bookCatalogService.deleteBook(bookId);
            return ResponseEntity.ok("Book deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Book not found with id: " + bookId);
        }
    }
}
