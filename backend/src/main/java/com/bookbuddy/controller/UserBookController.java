package com.bookbuddy.controller;

import com.bookbuddy.dto.AddBookFromSearchRequest;
import com.bookbuddy.dto.UserBookRequest;
import com.bookbuddy.model.BookCatalog;
import com.bookbuddy.model.Genre;
import com.bookbuddy.model.ShelfStatus;
import com.bookbuddy.model.User;
import com.bookbuddy.model.UserBook;
import com.bookbuddy.service.BookCatalogService;
import com.bookbuddy.service.UserBookService;
import com.bookbuddy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for UserBook operations
 * Handles user's personal library (adding/removing books, changing shelf
 * status)
 */
@RestController
@RequestMapping("/api/userbooks")
@CrossOrigin(origins = "http://localhost:5173")
public class UserBookController {

    private final UserBookService userBookService;
    private final UserService userService;
    private final BookCatalogService bookCatalogService;

    @Autowired
    public UserBookController(
            UserBookService userBookService,
            UserService userService,
            BookCatalogService bookCatalogService) {
        this.userBookService = userBookService;
        this.userService = userService;
        this.bookCatalogService = bookCatalogService;
    }

    /**
     * Get all books in a user's library
     * GET /api/userbooks/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserBooks(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with id: " + userId);
        }

        List<UserBook> userBooks = userBookService.getBooksByUserId(userId);
        return ResponseEntity.ok(userBooks);
    }

    /**
     * Add a book to user's library
     * POST /api/userbooks
     */
    @PostMapping
    public ResponseEntity<?> addBookToLibrary(@Valid @RequestBody UserBookRequest request) {
        // Validate user exists
        Optional<User> user = userService.getUserById(request.getUserId());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with id: " + request.getUserId());
        }

        // Validate book exists in catalog
        Optional<BookCatalog> book = bookCatalogService.getBookById(request.getBookId());
        if (book.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Book not found in catalog with id: " + request.getBookId());
        }

        // Use shelf status from request or default
        ShelfStatus shelf = request.getShelf() != null ? request.getShelf() : ShelfStatus.WANT_TO_READ;

        // Create UserBook
        UserBook userBook = new UserBook(user.get(), book.get(), shelf);
        UserBook savedUserBook = userBookService.saveUserBook(userBook);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserBook);
    }

    /**
     * Update shelf status for a book in user's library
     * PUT /api/userbooks/{id}/shelf
     */
    @PutMapping("/{id}/shelf")
    public ResponseEntity<?> updateShelfStatus(
            @PathVariable Long id,
            @RequestParam ShelfStatus shelf) {

        Optional<UserBook> userBook = userBookService.getUserBookById(id);
        if (userBook.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("UserBook not found with id: " + id);
        }

        UserBook book = userBook.get();

        // Update shelf status using model methods
        switch (shelf) {
            case WANT_TO_READ:
                book.markAsWantToRead();
                break;
            case CURRENTLY_READING:
                book.markAsCurrentlyReading();
                break;
            case READ:
                book.markAsRead();
                break;
        }

        UserBook updatedBook = userBookService.saveUserBook(book);
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * Remove a book from user's library
     * DELETE /api/userbooks/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeBookFromLibrary(@PathVariable Long id) {
        Optional<UserBook> userBook = userBookService.getUserBookById(id);
        if (userBook.isPresent()) {
            userBookService.deleteUserBook(id);
            return ResponseEntity.ok("Book removed from library successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("UserBook not found with id: " + id);
        }
    }

    /**
     * Get all UserBook entries
     * GET /api/userbooks
     */
    @GetMapping
    public ResponseEntity<List<UserBook>> getAllUserBooks() {
        List<UserBook> userBooks = userBookService.getAllUserBooks();
        return ResponseEntity.ok(userBooks);
    }

    /**
     * Get UserBook by ID
     * GET /api/userbooks/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserBookById(@PathVariable Long id) {
        Optional<UserBook> userBook = userBookService.getUserBookById(id);
        if (userBook.isPresent()) {
            return ResponseEntity.ok(userBook.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("UserBook not found with id: " + id);
        }
    }

    /**
     * Add a book from Open Library search directly to user's library
     * Creates catalog entry if it doesn't exist (checks by openLibraryId)
     * POST /api/userbooks/add-from-search
     */
    @PostMapping("/add-from-search")
    public ResponseEntity<?> addBookFromSearch(@Valid @RequestBody AddBookFromSearchRequest request) {
        // Validate user exists
        Optional<User> user = userService.getUserById(request.getUserId());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with id: " + request.getUserId());
        }

        // Check if book already exists in catalog by openLibraryId
        Optional<BookCatalog> existingBook = bookCatalogService.findByOpenLibraryId(request.getOpenLibraryId());

        BookCatalog catalogBook;
        if (existingBook.isPresent()) {
            catalogBook = existingBook.get();
        } else {
            // Create new catalog entry from search data
            catalogBook = new BookCatalog(request.getTitle(), request.getAuthor());
            catalogBook.setOpenLibraryId(request.getOpenLibraryId());

            // Use genre from request or default to OTHER
            catalogBook.setGenre(request.getGenre() != null ? request.getGenre() : Genre.OTHER);

            if (request.getCoverUrl() != null) {
                catalogBook.setCoverUrl(request.getCoverUrl());
            }

            catalogBook = bookCatalogService.saveBook(catalogBook);
        }

        // Use shelf status from request or default
        ShelfStatus shelf = request.getShelf() != null ? request.getShelf() : ShelfStatus.WANT_TO_READ;

        // Create UserBook
        UserBook userBook = new UserBook(user.get(), catalogBook, shelf);
        UserBook savedUserBook = userBookService.saveUserBook(userBook);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserBook);
    }
}
