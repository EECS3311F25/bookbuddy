package com.bookbuddy.controller;

import com.bookbuddy.dto.AddBookFromSearchRequest;
import com.bookbuddy.dto.UserBookRequest;
import com.bookbuddy.model.*;
import com.bookbuddy.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Handles all operations for a user's personal library.
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
     * Get all books saved by a user.
     *
     * @param userId ID of the user
     * @return list of UserBook records
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
     * Add a book to a user's library.
     *
     * @param request request data with userId, bookId, and shelf
     * @return created UserBook record
     */
    @PostMapping
    public ResponseEntity<?> addBookToLibrary(@Valid @RequestBody UserBookRequest request) {

        Optional<User> user = userService.getUserById(request.getUserId());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with id: " + request.getUserId());
        }

        Optional<BookCatalog> book = bookCatalogService.getBookById(request.getBookId());
        if (book.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Book not found in catalog with id: " + request.getBookId());
        }

        ShelfStatus shelf = request.getShelf() != null
                ? request.getShelf()
                : ShelfStatus.WANT_TO_READ;

        UserBook userBook = new UserBook(user.get(), book.get(), shelf);

        UserBook savedUserBook = userBookService.saveUserBook(userBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserBook);
    }

    /**
     * Update shelf status for a UserBook.
     *
     * @param id    ID of the UserBook
     * @param shelf new shelf status
     * @return updated UserBook
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

        if (shelf == ShelfStatus.WANT_TO_READ) {
            book.markAsWantToRead();
        } else if (shelf == ShelfStatus.CURRENTLY_READING) {
            book.markAsCurrentlyReading();
        } else if (shelf == ShelfStatus.READ) {
            book.markAsRead();
        }

        UserBook updatedBook = userBookService.saveUserBook(book);
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * Delete a UserBook record.
     *
     * @param id ID of the record
     * @return success or error message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeBookFromLibrary(@PathVariable Long id) {

        Optional<UserBook> userBook = userBookService.getUserBookById(id);

        if (userBook.isPresent()) {
            userBookService.deleteUserBook(id);
            return ResponseEntity.ok("Book removed from library");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("UserBook not found with id: " + id);
        }
    }

    /**
     * Get all UserBook entries.
     *
     * @return list of all UserBook records
     */
    @GetMapping
    public ResponseEntity<List<UserBook>> getAllUserBooks() {
        List<UserBook> userBooks = userBookService.getAllUserBooks();
        return ResponseEntity.ok(userBooks);
    }

    /**
     * Get one UserBook by ID.
     *
     * @param id UserBook ID
     * @return the record or a not-found message
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
     * Add a book directly from Open Library search.
     *
     * @param request request with search results and user details
     * @return created UserBook record
     */
    @PostMapping("/add-from-search")
    public ResponseEntity<?> addBookFromSearch(
            @Valid @RequestBody AddBookFromSearchRequest request) {

        Optional<User> user = userService.getUserById(request.getUserId());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with id: " + request.getUserId());
        }

        Optional<BookCatalog> existingBook = bookCatalogService.findByOpenLibraryId(request.getOpenLibraryId());

        BookCatalog catalogBook;

        if (existingBook.isPresent()) {
            catalogBook = existingBook.get();
        } else {
            catalogBook = new BookCatalog(request.getTitle(), request.getAuthor());
            catalogBook.setOpenLibraryId(request.getOpenLibraryId());
            catalogBook.setGenre(request.getGenre() != null
                    ? request.getGenre()
                    : Genre.OTHER);

            if (request.getCoverUrl() != null) {
                catalogBook.setCoverUrl(request.getCoverUrl());
            }

            catalogBook = bookCatalogService.saveBook(catalogBook);
        }

        ShelfStatus shelf = request.getShelf() != null
                ? request.getShelf()
                : ShelfStatus.WANT_TO_READ;

        UserBook userBook = new UserBook(user.get(), catalogBook, shelf);
        UserBook savedUserBook = userBookService.saveUserBook(userBook);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserBook);
    }
}
