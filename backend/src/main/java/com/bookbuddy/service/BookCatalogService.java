package com.bookbuddy.service;

import com.bookbuddy.dto.OpenLibrarySearchResponse;
import com.bookbuddy.model.BookCatalog;
import com.bookbuddy.model.Genre;
import com.bookbuddy.repository.BookCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing {@link BookCatalog} entities.
 *
 * This class provides the business logic for all operations
 * related to the global book catalog, including adding new books,
 * retrieving available books, and removing entries from the system.
 * It acts as a bridge between the controller and repository layers.
 *
 * Main Tasks in BookCatalogService:
 * 
 *   1. Save or update catalog book entries
 *   2. Retrieve all books in the catalog
 *   3. Find a book by its ID
 *   4. Delete a book from the catalog
 * 
 */
@Service
public class BookCatalogService {

    private final BookCatalogRepository bookCatalogRepository;

    /**
     * Constructor injection for the BookCatalogRepository dependency.
     * @param bookCatalogRepository repository instance injected by Spring
     */
    @Autowired
    public BookCatalogService(BookCatalogRepository bookCatalogRepository) {
        this.bookCatalogRepository = bookCatalogRepository;
    }

    /**
     * Saves or updates a {@link BookCatalog} entry in the database.
     * @param book the {@link BookCatalog} entity to be saved or updated
     * @return the saved {@link BookCatalog} entity
     */
    public BookCatalog saveBook(BookCatalog book) {
        return bookCatalogRepository.save(book);
    }

    /**
     * Retrieves all {@link BookCatalog} entries from the database.
     * @return list of all {@link BookCatalog} entities
     */
    public List<BookCatalog> getAllBooks() {
        return bookCatalogRepository.findAll();
    }

    /**
     * Retrieves a specific {@link BookCatalog} entry by its ID.
     * @param id the unique ID of the catalog book
     * @return an {@link Optional} containing the book if found
     */
    public Optional<BookCatalog> getBookById(Long id) {
        return bookCatalogRepository.findById(id);
    }

    /**
     * Deletes a {@link BookCatalog} entry from the catalog by its ID.
     * @param id the unique ID of the catalog book to be deleted
     */
    public void deleteBook(Long id) {
        bookCatalogRepository.deleteById(id);
    }

    /**
     * Find or create a BookCatalog entry from Open Library search result.
     * Checks for existing book by openLibraryId to prevent duplicates.
     *
     * @param openLibraryBook the book data from Open Library API
     * @return existing or newly created BookCatalog entity
     */
    public BookCatalog findOrCreateFromOpenLibrary(OpenLibrarySearchResponse.OpenLibraryBook openLibraryBook) {
        String openLibraryId = openLibraryBook.getKey();

        // Check if book already exists in catalog
        Optional<BookCatalog> existing = bookCatalogRepository.findByOpenLibraryId(openLibraryId);
        if (existing.isPresent()) {
            return existing.get();
        }

        // Create new catalog entry
        BookCatalog newBook = new BookCatalog(
                openLibraryBook.getTitle(),
                openLibraryBook.getFirstAuthor()
        );

        newBook.setOpenLibraryId(openLibraryId);
        newBook.setGenre(Genre.OTHER); // Default genre

        if (openLibraryBook.getCoverUrl() != null) {
            newBook.setCoverUrl(openLibraryBook.getCoverUrl());
        }

        return bookCatalogRepository.save(newBook);
    }

    /**
     * Find book by Open Library ID
     */
    public Optional<BookCatalog> findByOpenLibraryId(String openLibraryId) {
        return bookCatalogRepository.findByOpenLibraryId(openLibraryId);
    }
}
