package com.bookbuddy.service;

import com.bookbuddy.model.User;
import com.bookbuddy.model.BookCatalog;
import com.bookbuddy.model.UserBook;
import com.bookbuddy.model.ShelfStatus;
import com.bookbuddy.repository.UserBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for UserBookService using an in-memory Fake Repository.
 */
class UserBookServiceTest {

    private UserBookService service;
    private FakeUserBookRepository fakeRepo;

    private User user1;
    private User user2;

    private BookCatalog book1;
    private BookCatalog book2;

    @BeforeEach
    void setUp() {
        fakeRepo = new FakeUserBookRepository();
        service = new UserBookService(fakeRepo);

        // Fake users
        user1 = new User("John", "Doe", "john123", "john@mail.com", "pass123");
        user2 = new User("Alice", "Smith", "alice99", "alice@mail.com", "mypassword");

        setUserId(user1, 1L);
        setUserId(user2, 2L);

        // Fake books
        book1 = new BookCatalog("Dune", "Frank Herbert");
        book2 = new BookCatalog("1984", "George Orwell");

        setBookId(book1, 10L);
        setBookId(book2, 20L);
    }

    // Utility methods to set IDs
    private void setUserId(User u, long id) {
        try {
            Field f = User.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(u, id);
        } catch (Exception ignored) {}
    }

    private void setBookId(BookCatalog b, long id) {
        try {
            Field f = BookCatalog.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(b, id);
        } catch (Exception ignored) {}
    }

    @Test
    void testSaveUserBook() {
        UserBook ub = new UserBook(user1, book1, ShelfStatus.WANT_TO_READ);
        UserBook saved = service.saveUserBook(ub);

        assertEquals(1, fakeRepo.storage.size());
        assertEquals(user1, saved.getUser());
        assertEquals(book1, saved.getBook());
    }

    @Test
    void testGetAllUserBooks() {
        fakeRepo.save(new UserBook(user1, book1, ShelfStatus.WANT_TO_READ));
        fakeRepo.save(new UserBook(user2, book2, ShelfStatus.READ));

        List<UserBook> list = service.getAllUserBooks();
        assertEquals(2, list.size());
    }

    @Test
    void testGetUserBookById() {
        UserBook ub = fakeRepo.save(new UserBook(user1, book1, ShelfStatus.READ));
        Optional<UserBook> found = service.getUserBookById(ub.getId());

        assertTrue(found.isPresent());
        assertEquals(book1, found.get().getBook());
    }

    @Test
    void testGetUserBookByIdNotFound() {
        Optional<UserBook> found = service.getUserBookById(999L);
        assertFalse(found.isPresent());
    }

    @Test
    void testDeleteUserBook() {
        UserBook ub = fakeRepo.save(new UserBook(user1, book1, ShelfStatus.READ));
        service.deleteUserBook(ub.getId());

        assertEquals(0, fakeRepo.storage.size());
    }

    @Test
    void testDeleteUserBookDoesNotCrashOnMissing() {
        service.deleteUserBook(999L); // should not crash
        assertTrue(true);
    }

    @Test
    void testGetBooksByUserId() {
        fakeRepo.save(new UserBook(user1, book1, ShelfStatus.WANT_TO_READ));
        fakeRepo.save(new UserBook(user1, book2, ShelfStatus.READ));
        fakeRepo.save(new UserBook(user2, book1, ShelfStatus.CURRENTLY_READING));

        List<UserBook> result = service.getBooksByUserId(1L);

        assertEquals(2, result.size());
    }

    @Test
    void testGetBooksByUserIdNone() {
        List<UserBook> result = service.getBooksByUserId(2L);
        assertEquals(0, result.size());
    }

    @Test
    void testGetUsersByBookId() {
        fakeRepo.save(new UserBook(user1, book1, ShelfStatus.CURRENTLY_READING));
        fakeRepo.save(new UserBook(user2, book1, ShelfStatus.WANT_TO_READ));
        fakeRepo.save(new UserBook(user2, book2, ShelfStatus.READ));

        List<UserBook> result = service.getUsersByBookId(10L);

        assertEquals(2, result.size());
    }

    @Test
    void testGetUsersByBookIdNone() {
        List<UserBook> result = service.getUsersByBookId(20L);
        assertEquals(0, result.size());
    }

    /**
     * In-memory Fake Repository for UserBook.
     */
    static class FakeUserBookRepository implements UserBookRepository {

        long idCounter = 1;
        Map<Long, UserBook> storage = new HashMap<>();

        @Override
        public UserBook save(UserBook userBook) {
            try {
                Field f = UserBook.class.getDeclaredField("id");
                f.setAccessible(true);

                Long currentId = (Long) f.get(userBook);

                // FIX: JPA uses null for uninitialized Long IDs, not 0
                if (currentId == null || currentId == 0) {
                    f.set(userBook, idCounter++);
                }
            } catch (Exception ignored) {}

            storage.put(userBook.getId(), userBook);
            return userBook;
        }


        @Override
        public List<UserBook> findAll() {
            return new ArrayList<>(storage.values());
        }

        @Override
        public Optional<UserBook> findById(Long id) {
            return Optional.ofNullable(storage.get(id));
        }

        @Override
        public void deleteById(Long id) {
            storage.remove(id);
        }

        @Override
        public List<UserBook> findByUserId(Long userId) {
            List<UserBook> list = new ArrayList<>();
            for (UserBook ub : storage.values()) {
                if (ub.getUser() != null && ub.getUser().getId() == userId) {
                    list.add(ub);
                }
            }
            return list;
        }

        @Override
        public List<UserBook> findByBookId(Long bookId) {
            List<UserBook> list = new ArrayList<>();
            for (UserBook ub : storage.values()) {
                if (ub.getBook() != null && ub.getBook().getId() == bookId) {
                    list.add(ub);
                }
            }
            return list;
        }

        // Unused JPA methods
        @Override public boolean existsById(Long id) { return false; }
        @Override public long count() { return 0; }
        @Override public void delete(UserBook entity) {}
        @Override public void deleteAll() {}
        @Override public void deleteAll(Iterable<? extends UserBook> entities) {}
        @Override public void deleteAllById(Iterable<? extends Long> ids) {}
        @Override public List<UserBook> findAllById(Iterable<Long> ids) { return null; }
        @Override public <S extends UserBook> List<S> saveAll(Iterable<S> entities) { return null; }
        @Override public <S extends UserBook> S saveAndFlush(S entity) { return null; }
        @Override public void flush() {}

		@Override
		public <S extends UserBook> List<S> saveAllAndFlush(Iterable<S> entities) {
			return null;
		}

		@Override
		public void deleteAllInBatch(Iterable<UserBook> entities) {

		}

		@Override
		public void deleteAllByIdInBatch(Iterable<Long> ids) {

		}

		@Override
		public void deleteAllInBatch() {

		}

		@Override
		public UserBook getOne(Long id) {
			return null;
		}

		@Override
		public UserBook getById(Long id) {
			return null;
		}

		@Override
		public UserBook getReferenceById(Long id) {
			return null;
		}

		@Override
		public <S extends UserBook> List<S> findAll(Example<S> example) {
			return null;
		}

		@Override
		public <S extends UserBook> List<S> findAll(Example<S> example, Sort sort) {
			return null;
		}

		@Override
		public List<UserBook> findAll(Sort sort) {
			return null;
		}

		@Override
		public Page<UserBook> findAll(Pageable pageable) {
			return null;
		}

		@Override
		public <S extends UserBook> Optional<S> findOne(Example<S> example) {
			return Optional.empty();
		}

		@Override
		public <S extends UserBook> Page<S> findAll(Example<S> example, Pageable pageable) {
			return null;
		}

		@Override
		public <S extends UserBook> long count(Example<S> example) {
			return 0;
		}

		@Override
		public <S extends UserBook> boolean exists(Example<S> example) {
			return false;
		}

		@Override
		public <S extends UserBook, R> R findBy(Example<S> example,
				Function<FetchableFluentQuery<S>, R> queryFunction) {
			return null;
		}

		@Override
		public List<UserBook> findByUser(User user) {
			return null;
		}

		@Override
		public List<UserBook> findByUserAndShelf(User user, String shelf) {
			return null;
		}

		@Override
		public List<UserBook> findByUserOrderByCreatedAtDesc(User user) {
			return null;
		}

		@Override
		public long countByUserAndShelf(User user, String shelf) {
			return 0;
		}
    }
}
