package com.bookbuddy.service;

import com.bookbuddy.model.UserBook;
import com.bookbuddy.repository.UserBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing {@link UserBook} entities.
 *
 * This class handles all operations related to the user's
 * personal book collection, including adding books to their
 * library, retrieving records, and deleting entries. It acts
 * as a bridge between the controller and repository layers.
 *
 * Main Tasks in UserBookService:
 * 
 *  1. Save or update user-specific book records
 *  2. Retrieve all user book records or specific entries
 *  3. Delete books from a user’s library
 *  4. Retrieve all books owned by a specific user
 *  5. Retrieve all users who own a specific book
 * 
 */
@Service
public class UserBookService {

    private final UserBookRepository userBookRepository;

    /**
     * Constructor injection for the UserBookRepository dependency.
     * @param userBookRepository repository instance injected by Spring
     */
    @Autowired
    public UserBookService(UserBookRepository userBookRepository) {
        this.userBookRepository = userBookRepository;
    }

    /**
     * Saves or updates a {@link UserBook} record.
     * @param userBook the userBook entity to be saved or updated
     * @return the saved {@link UserBook} entity
     */
    public UserBook saveUserBook(UserBook userBook) {
        return userBookRepository.save(userBook);
    }

    /**
     * Retrieves all {@link UserBook} records from the database.
     * @return list of all {@link UserBook} entities
     */
    public List<UserBook> getAllUserBooks() {
        return userBookRepository.findAll();
    }

    /**
     * Retrieves a specific {@link UserBook} by its ID.
     * @param id the unique ID of the UserBook record
     * @return an {@link Optional} containing the record if found
     */
    public Optional<UserBook> getUserBookById(Long id) {
        return userBookRepository.findById(id);
    }

    /**
     * Deletes a {@link UserBook} record by its ID.
     * @param id the unique ID of the record to be deleted
     */
    public void deleteUserBook(Long id) {
        userBookRepository.deleteById(id);
    }

    /**
     * Retrieves all {@link UserBook} entries belonging to a specific user.
     * @param userId the ID of the user
     * @return list of {@link UserBook} entries owned by that user
     */
    public List<UserBook> getBooksByUserId(Long userId) {
        return userBookRepository.findByUserId(userId);
    }

    /**
     * Retrieves all {@link UserBook} entries associated with a specific book.
     * @param bookId the ID of the book
     * @return list of {@link UserBook} entries that reference this book
     */
    public List<UserBook> getUsersByBookId(Long bookId) {
        return userBookRepository.findByBookId(bookId);
    }
}
