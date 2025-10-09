package com.bookbuddy.service;

import com.bookbuddy.dto.BookRequest;
import com.bookbuddy.model.Book;
import com.bookbuddy.model.User;
import com.bookbuddy.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for Book operations
 */
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public Book createBook(BookRequest request, User user) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setDescription(request.getDescription());
        book.setCoverUrl(request.getCoverUrl());
        book.setOpenLibraryId(request.getOpenLibraryId());
        book.setShelf(request.getShelf());
        book.setRating(request.getRating());
        book.setReview(request.getReview());
        book.setUser(user);

        return bookRepository.save(book);
    }

    public List<Book> getUserBooks(User user) {
        return bookRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public List<Book> getBooksByShelf(User user, String shelf) {
        return bookRepository.findByUserAndShelf(user, shelf);
    }

    public long countBooksByShelf(User user, String shelf) {
        return bookRepository.countByUserAndShelf(user, shelf);
    }
}
