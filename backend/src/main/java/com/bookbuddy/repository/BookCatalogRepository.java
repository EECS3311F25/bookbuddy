/**
 * Handles book catagory queries
 */
package com.bookbuddy.repository;

import com.bookbuddy.model.BookCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository for BookCatalog entity.
 * Manages all available books and their metadata.
 */
@Repository
public interface BookCatalogRepository extends JpaRepository<BookCatalog, Long> {

    // Find book by title
    Optional<BookCatalog> findByTitle(String title);

    // Find books by author
    List<BookCatalog> findByAuthor(String author);

    // Find books by genre
    List<BookCatalog> findByGenre(String genre);

    // Find book by Open Library ID (prevents duplicates)
    Optional<BookCatalog> findByOpenLibraryId(String openLibraryId);
}
