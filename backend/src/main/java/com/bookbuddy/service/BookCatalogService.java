package com.bookbuddy.service;

import com.bookbuddy.model.BookCatalog;
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
}
