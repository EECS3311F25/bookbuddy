package com.bookbuddy.controller;

import com.bookbuddy.dto.BookRequest;
import com.bookbuddy.model.Book;
import com.bookbuddy.model.User;
import com.bookbuddy.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Book operations
 * Demonstrates MVC connectivity: Controller -> Service -> Repository -> Database
 */
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class BookController {

    private final BookService bookService;

    /**
     * Create a new book
     * Example: POST /api/books with JSON body
     * Demonstrates: Button/Form -> Controller -> Service -> Repository -> SQLite Database
     */
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookRequest request) {
        // TODO: Replace with actual authenticated user from Spring Security
        // For now, using a mock user for demonstration
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("demo");
        mockUser.setEmail("demo@bookbuddy.com");

        Book createdBook = bookService.createBook(request, mockUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    /**
     * Get all books for the current user
     */
    @GetMapping
    public ResponseEntity<List<Book>> getUserBooks() {
        // TODO: Replace with actual authenticated user
        User mockUser = new User();
        mockUser.setId(1L);

        List<Book> books = bookService.getUserBooks(mockUser);
        return ResponseEntity.ok(books);
    }

    /**
     * Get books by shelf (want_to_read, currently_reading, read)
     */
    @GetMapping("/shelf/{shelf}")
    public ResponseEntity<List<Book>> getBooksByShelf(@PathVariable String shelf) {
        // TODO: Replace with actual authenticated user
        User mockUser = new User();
        mockUser.setId(1L);

        List<Book> books = bookService.getBooksByShelf(mockUser, shelf);
        return ResponseEntity.ok(books);
    }
}
