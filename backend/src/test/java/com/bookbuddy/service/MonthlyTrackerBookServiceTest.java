package com.bookbuddy.service;

import com.bookbuddy.model.*;
import com.bookbuddy.repository.MonthlyTrackerBookRepository;
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
 * Full test suite for MonthlyTrackerBookService using an in-memory Fake Repository.
 */
class MonthlyTrackerBookServiceTest {

    private MonthlyTrackerBookService service;
    private FakeMonthlyTrackerBookRepo fakeRepo;

    private User user;
    private BookCatalog book;
    private UserBook userBook;

    private MonthlyTracker tracker;
    private MonthlyTrackerBook trackerBook1;
    private MonthlyTrackerBook trackerBook2;

    @BeforeEach
    void setup() {
        fakeRepo = new FakeMonthlyTrackerBookRepo();
        service = new MonthlyTrackerBookService(fakeRepo);

        user = new User("Alice", "Blue", "alice01", "alice@mail.com", "Pass1234");
        setUserId(user, 10L);

        book = new BookCatalog("Dune", "Frank Herbert");
        setBookId(book, 20L);

        userBook = new UserBook(user, book, ShelfStatus.WANT_TO_READ);
        setUserBookId(userBook, 30L);

        tracker = new MonthlyTracker(user, Months.JANUARY);
        setTrackerId(tracker, 40L);

        trackerBook1 = new MonthlyTrackerBook(tracker, userBook);
        trackerBook2 = new MonthlyTrackerBook(tracker, userBook);
    }

    // Utility: assign ID to User
    private void setUserId(User u, long id) {
        try {
            Field f = User.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(u, id);
        } catch (Exception ignored) {}
    }

    // Utility: assign ID to BookCatalog
    private void setBookId(BookCatalog b, long id) {
        try {
            Field f = BookCatalog.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(b, id);
        } catch (Exception ignored) {}
    }

    // Utility: assign ID to UserBook
    private void setUserBookId(UserBook ub, long id) {
        try {
            Field f = UserBook.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(ub, id);
        } catch (Exception ignored) {}
    }

    // Utility: assign ID to MonthlyTracker
    private void setTrackerId(MonthlyTracker t, long id) {
        try {
            Field f = MonthlyTracker.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(t, id);
        } catch (Exception ignored) {}
    }

    @Test
    void testSaveMonthlyTrackerBook() {
        MonthlyTrackerBook saved = service.saveMonthlyTrackerBook(trackerBook1);

        assertNotNull(saved.getId());
        assertEquals(1, fakeRepo.map.size());
        assertEquals(tracker, saved.getMonthlyTracker());
        assertEquals(userBook, saved.getUserBook());
    }

    @Test
    void testGetAllMonthlyTrackerBooks() {
        fakeRepo.save(trackerBook1);
        fakeRepo.save(trackerBook2);

        List<MonthlyTrackerBook> list = service.getAllMonthlyTrackerBooks();
        assertEquals(2, list.size());
    }

    @Test
    void testGetMonthlyTrackerBookById() {
        MonthlyTrackerBook saved = fakeRepo.save(trackerBook1);

        Optional<MonthlyTrackerBook> found = service.getMonthlyTrackerBookById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(userBook, found.get().getUserBook());
    }

    @Test
    void testGetMonthlyTrackerBookById_NotFound() {
        Optional<MonthlyTrackerBook> found = service.getMonthlyTrackerBookById(999L);
        assertFalse(found.isPresent());
    }

    @Test
    void testDeleteMonthlyTrackerBook() {
        MonthlyTrackerBook saved = fakeRepo.save(trackerBook1);

        service.deleteMonthlyTrackerBook(saved.getId());

        assertEquals(0, fakeRepo.map.size());
    }

    @Test
    void testDeleteMonthlyTrackerBook_DoesNotCrash() {
        service.deleteMonthlyTrackerBook(999L);
        assertTrue(true); // no exception expected
    }

    @Test
    void testGetByTrackerId() {
        MonthlyTrackerBook a = fakeRepo.save(trackerBook1);
        MonthlyTrackerBook b = fakeRepo.save(trackerBook2);

        List<MonthlyTrackerBook> list = service.getByTrackerId(40L);

        assertEquals(2, list.size());
    }

    @Test
    void testGetByTrackerId_None() {
        List<MonthlyTrackerBook> list = service.getByTrackerId(999L);
        assertEquals(0, list.size());
    }

    @Test
    void testCountCompletedBooks() {
        MonthlyTrackerBook a = fakeRepo.save(trackerBook1);
        MonthlyTrackerBook b = fakeRepo.save(trackerBook2);

        // Mark one completed
        b.setCompleted(true);

        long count = service.countCompletedBooks(40L);

        assertEquals(1, count);
    }

    @Test
    void testCountCompletedBooks_NoneCompleted() {
        fakeRepo.save(trackerBook1);
        fakeRepo.save(trackerBook2);

        long count = service.countCompletedBooks(40L);

        assertEquals(0, count);
    }

    /**
     * Fake in-memory repository for MonthlyTrackerBook.
     */
    static class FakeMonthlyTrackerBookRepo implements MonthlyTrackerBookRepository {

        long idCounter = 1;
        Map<Long, MonthlyTrackerBook> map = new HashMap<>();

        @Override
        public MonthlyTrackerBook save(MonthlyTrackerBook entity) {
            try {
                Field f = MonthlyTrackerBook.class.getDeclaredField("id");
                f.setAccessible(true);

                Long val = (Long) f.get(entity);
                if (val == null || val == 0) {
                    f.set(entity, idCounter++);
                }

            } catch (Exception ignored) {}

            map.put(entity.getId(), entity);
            return entity;
        }

        @Override
        public List<MonthlyTrackerBook> findAll() {
            return new ArrayList<>(map.values());
        }

        @Override
        public Optional<MonthlyTrackerBook> findById(Long id) {
            return Optional.ofNullable(map.get(id));
        }

        @Override
        public void deleteById(Long id) {
            map.remove(id);
        }

        @Override
        public List<MonthlyTrackerBook> findByMonthlyTrackerId(Long trackerId) {
            List<MonthlyTrackerBook> result = new ArrayList<>();
            for (MonthlyTrackerBook mtb : map.values()) {
                if (mtb.getMonthlyTracker() != null &&
                    mtb.getMonthlyTracker().getId().equals(trackerId)) {
                    result.add(mtb);
                }
            }
            return result;
        }

        // Unused JpaRepository methods
        @Override public boolean existsById(Long aLong) { return false; }
        @Override public long count() { return 0; }
        @Override public void delete(MonthlyTrackerBook entity) {}
        @Override public void deleteAll() {}
        @Override public void deleteAll(Iterable<? extends MonthlyTrackerBook> iterable) {}
        @Override public void deleteAllById(Iterable<? extends Long> iterable) {}
        @Override public List<MonthlyTrackerBook> findAllById(Iterable<Long> iterable) { return null; }
        @Override public <S extends MonthlyTrackerBook> List<S> saveAll(Iterable<S> entities) { return null; }
        @Override public <S extends MonthlyTrackerBook> S saveAndFlush(S entity) { return null; }
        @Override public void flush() {}

		@Override
		public <S extends MonthlyTrackerBook> List<S> saveAllAndFlush(Iterable<S> entities) {
			return null;
		}

		@Override
		public void deleteAllInBatch(Iterable<MonthlyTrackerBook> entities) {

		}

		@Override
		public void deleteAllByIdInBatch(Iterable<Long> ids) {

		}

		@Override
		public void deleteAllInBatch() {

		}

		@Override
		public MonthlyTrackerBook getOne(Long id) {
			return null;
		}

		@Override
		public MonthlyTrackerBook getById(Long id) {
			return null;
		}

		@Override
		public MonthlyTrackerBook getReferenceById(Long id) {
			return null;
		}

		@Override
		public <S extends MonthlyTrackerBook> List<S> findAll(Example<S> example) {
			return null;
		}

		@Override
		public <S extends MonthlyTrackerBook> List<S> findAll(Example<S> example, Sort sort) {
			return null;
		}

		@Override
		public List<MonthlyTrackerBook> findAll(Sort sort) {
			return null;
		}

		@Override
		public Page<MonthlyTrackerBook> findAll(Pageable pageable) {
			return null;
		}

		@Override
		public <S extends MonthlyTrackerBook> Optional<S> findOne(Example<S> example) {
			return Optional.empty();
		}

		@Override
		public <S extends MonthlyTrackerBook> Page<S> findAll(Example<S> example, Pageable pageable) {
			return null;
		}

		@Override
		public <S extends MonthlyTrackerBook> long count(Example<S> example) {
			return 0;
		}

		@Override
		public <S extends MonthlyTrackerBook> boolean exists(Example<S> example) {
			return false;
		}

		@Override
		public <S extends MonthlyTrackerBook, R> R findBy(Example<S> example,
				Function<FetchableFluentQuery<S>, R> queryFunction) {
			return null;
		}
    }
}
