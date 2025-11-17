package com.bookbuddy.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.bookbuddy.dto.OpenLibrarySearchResponse;
import com.bookbuddy.model.BookCatalog;
import com.bookbuddy.model.Genre;
import com.bookbuddy.repository.BookCatalogRepository;
import com.bookbuddy.service.BookCatalogService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookCatalogServiceTest {

    @Mock
    private BookCatalogRepository bookCatalogRepository;

    @Mock
    private OpenLibrarySearchResponse.OpenLibraryBook openLibraryBook;

    @InjectMocks
    private BookCatalogService bookCatalogService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBook() {
        BookCatalog book = new BookCatalog("Dune", "Frank Herbert");
        when(bookCatalogRepository.save(book)).thenReturn(book);

        BookCatalog result = bookCatalogService.saveBook(book);

        assertEquals("Dune", result.getTitle());
        verify(bookCatalogRepository).save(book);
    }

    @Test
    void testGetAllBooks() {
        BookCatalog b1 = new BookCatalog("Dune", "Frank Herbert");
        BookCatalog b2 = new BookCatalog("1984", "George Orwell");

        when(bookCatalogRepository.findAll()).thenReturn(List.of(b1, b2));

        List<BookCatalog> result = bookCatalogService.getAllBooks();

        assertEquals(2, result.size());
        verify(bookCatalogRepository).findAll();
    }

    @Test
    void testGetBookById() {
        BookCatalog book = new BookCatalog("Dune", "Frank Herbert");
        when(bookCatalogRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<BookCatalog> result = bookCatalogService.getBookById(1L);

        assertTrue(result.isPresent());
        assertEquals("Dune", result.get().getTitle());
        verify(bookCatalogRepository).findById(1L);
    }

    @Test
    void testDeleteBook() {
        doNothing().when(bookCatalogRepository).deleteById(5L);

        bookCatalogService.deleteBook(5L);

        verify(bookCatalogRepository).deleteById(5L);
    }

    @Test
    void testFindOrCreateReturnsExisting() {
        String id = "/works/OL1234W";

        BookCatalog existing = new BookCatalog("Dune", "Frank Herbert");
        existing.setOpenLibraryId(id);

        when(openLibraryBook.getKey()).thenReturn(id);
        when(bookCatalogRepository.findByOpenLibraryId(id))
                .thenReturn(Optional.of(existing));

        BookCatalog result = bookCatalogService.findOrCreateFromOpenLibrary(openLibraryBook);

        assertEquals(existing, result);
        verify(bookCatalogRepository, never()).save(any());
    }

    @Test
    void testFindOrCreateCreatesNewEntry() {
        String id = "/works/OL5678W";

        when(openLibraryBook.getKey()).thenReturn(id);
        when(openLibraryBook.getTitle()).thenReturn("New Title");
        when(openLibraryBook.getFirstAuthor()).thenReturn("Author Name");
        when(openLibraryBook.getCoverUrl()).thenReturn("http://example.com/c.jpg");

        when(bookCatalogRepository.findByOpenLibraryId(id)).thenReturn(Optional.empty());

        BookCatalog saved = new BookCatalog("New Title", "Author Name");
        saved.setOpenLibraryId(id);
        saved.setCoverUrl("http://example.com/c.jpg");

        when(bookCatalogRepository.save(any(BookCatalog.class))).thenReturn(saved);

        BookCatalog result = bookCatalogService.findOrCreateFromOpenLibrary(openLibraryBook);

        assertEquals("New Title", result.getTitle());
        assertEquals("Author Name", result.getAuthor());
        assertEquals(id, result.getOpenLibraryId());
        assertEquals(Genre.OTHER, result.getGenre());
        assertEquals("http://example.com/c.jpg", result.getCoverUrl());
    }

    @Test
    void testFindByOpenLibraryId() {
        BookCatalog book = new BookCatalog("Dune", "Frank Herbert");
        book.setOpenLibraryId("OLTEST1");

        when(bookCatalogRepository.findByOpenLibraryId("OLTEST1"))
                .thenReturn(Optional.of(book));

        Optional<BookCatalog> result = bookCatalogService.findByOpenLibraryId("OLTEST1");

        assertTrue(result.isPresent());
        assertEquals("Dune", result.get().getTitle());
        verify(bookCatalogRepository).findByOpenLibraryId("OLTEST1");
    }
}
