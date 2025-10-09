package com.bookbuddy.repository;

import com.bookbuddy.model.Book;
import com.bookbuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Book entity
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByUser(User user);

    List<Book> findByUserAndShelf(User user, String shelf);

    List<Book> findByUserOrderByCreatedAtDesc(User user);

    long countByUserAndShelf(User user, String shelf);
}
