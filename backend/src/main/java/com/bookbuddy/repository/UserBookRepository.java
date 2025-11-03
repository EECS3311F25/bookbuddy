package com.bookbuddy.repository;


import com.bookbuddy.model.User;
import com.bookbuddy.model.UserBook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Book entity
 */
@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Long> {

    List<UserBook> findByUser(User user);

    List<UserBook> findByUserAndShelf(User user, String shelf);

    List<UserBook> findByUserOrderByCreatedAtDesc(User user);
    
    List<UserBook> findByUserId(Long userId);
    
    List<UserBook> findByBookId(Long bookId); 

    long countByUserAndShelf(User user, String shelf);
}
