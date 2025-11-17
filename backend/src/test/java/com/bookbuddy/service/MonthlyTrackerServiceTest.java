package com.bookbuddy.service;

import com.bookbuddy.model.*;
import com.bookbuddy.repository.MonthlyTrackerRepository;
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
 * Comprehensive test suite for MonthlyTrackerService based on the real MonthlyTracker model.
 */
class MonthlyTrackerServiceTest {

    private MonthlyTrackerService service;
    private FakeMonthlyTrackerRepository fakeRepo;

    private User user1;
    private User user2;

    private MonthlyTracker tracker1;
    private MonthlyTracker tracker2;

    @BeforeEach
    void setUp() {
        fakeRepo = new FakeMonthlyTrackerRepository();
        service = new MonthlyTrackerService(fakeRepo);

        user1 = new User("Alice", "Blue", "alice01", "alice@mail.com", "pass123");
        user2 = new User("John", "Doe", "john02", "john@mail.com", "pass123");

        setUserId(user1, 1L);
        setUserId(user2, 2L);

        tracker1 = new MonthlyTracker(user1, Months.JANUARY);
        tracker1.setTargetBooksNum(5);

        tracker2 = new MonthlyTracker(user2, Months.FEBRUARY);
        tracker2.setTargetBooksNum(3);
    }

    // Utility methods to set IDs manually
    private void setUserId(User u, long id) {
        try {
            Field f = User.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(u, id);
        } catch (Exception ignored) {}
    }

    private void setTrackerId(MonthlyTracker t, long id) {
        try {
            Field f = MonthlyTracker.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(t, id);
        } catch (Exception ignored) {}
    }

    @Test
    void testSaveTracker() {
        MonthlyTracker saved = service.saveTracker(tracker1);

        assertEquals(1, fakeRepo.storage.size());
        assertEquals(5, saved.getTargetBooksNum());
        assertEquals(Months.JANUARY, saved.getMonth());
        assertEquals("alice01", saved.getUser().getUsername());
    }

    @Test
    void testGetAllTrackers() {
        fakeRepo.save(tracker1);
        fakeRepo.save(tracker2);

        List<MonthlyTracker> trackers = service.getAllTrackers();
        assertEquals(2, trackers.size());
    }

    @Test
    void testGetTrackerById() {
        MonthlyTracker saved = fakeRepo.save(tracker1);

        Optional<MonthlyTracker> found = service.getTrackerById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(5, found.get().getTargetBooksNum());
    }

    @Test
    void testGetTrackerByIdNotFound() {
        Optional<MonthlyTracker> found = service.getTrackerById(999L);
        assertFalse(found.isPresent());
    }

    @Test
    void testDeleteTracker() {
        MonthlyTracker saved = fakeRepo.save(tracker1);
        service.deleteTracker(saved.getId());

        assertEquals(0, fakeRepo.storage.size());
    }

    @Test
    void testDeleteTrackerDoesNotCrashOnMissing() {
        service.deleteTracker(999L);
        assertTrue(true);
    }

    @Test
    void testGetTrackersByUserId() {
        fakeRepo.save(tracker1);
        fakeRepo.save(tracker2);

        List<MonthlyTracker> result = service.getTrackersByUserId(1L);

        assertEquals(1, result.size());
        assertEquals(Months.JANUARY, result.get(0).getMonth());
    }

    @Test
    void testGetTrackersByUserIdNone() {
        List<MonthlyTracker> result = service.getTrackersByUserId(3L);
        assertEquals(0, result.size());
    }

    @Test
    void testUpdateGoalSuccess() {
        MonthlyTracker saved = fakeRepo.save(new MonthlyTracker(user1, Months.MARCH));
        saved.setTargetBooksNum(4);

        MonthlyTracker updated = service.updateGoal(saved.getId(), 10);

        assertEquals(10, updated.getTargetBooksNum());
    }

    @Test
    void testUpdateGoalTrackerNotFound() {
        assertThrows(IllegalArgumentException.class,
                () -> service.updateGoal(999L, 7));
    }

    /**
     * Fake in-memory repository for MonthlyTracker.
     */
    static class FakeMonthlyTrackerRepository implements MonthlyTrackerRepository {

        long idCounter = 1;
        Map<Long, MonthlyTracker> storage = new HashMap<>();

        @Override
        public MonthlyTracker save(MonthlyTracker tracker) {
            try {
                Field f = MonthlyTracker.class.getDeclaredField("id");
                f.setAccessible(true);

                Long currentId = (Long) f.get(tracker);
                if (currentId == null || currentId == 0) {
                    f.set(tracker, idCounter++);
                }
            } catch (Exception ignored) {}

            storage.put(tracker.getId(), tracker);
            return tracker;
        }

        @Override
        public List<MonthlyTracker> findAll() {
            return new ArrayList<>(storage.values());
        }

        @Override
        public Optional<MonthlyTracker> findById(Long id) {
            return Optional.ofNullable(storage.get(id));
        }

        @Override
        public void deleteById(Long id) {
            storage.remove(id);
        }

        @Override
        public List<MonthlyTracker> findByUserId(Long userId) {
            List<MonthlyTracker> result = new ArrayList<>();
            for (MonthlyTracker t : storage.values()) {
                if (t.getUser() != null && t.getUser().getId() == userId) {
                    result.add(t);
                }
            }
            return result;
        }

        // Unused JpaRepository methods
        @Override public boolean existsById(Long id) { return false; }
        @Override public long count() { return 0; }
        @Override public void delete(MonthlyTracker entity) {}
        @Override public void deleteAll() {}
        @Override public void deleteAll(Iterable<? extends MonthlyTracker> entities) {}
        @Override public void deleteAllById(Iterable<? extends Long> longs) {}
        @Override public List<MonthlyTracker> findAllById(Iterable<Long> longs) { return null; }
        @Override public <S extends MonthlyTracker> List<S> saveAll(Iterable<S> entities) { return null; }
        @Override public <S extends MonthlyTracker> S saveAndFlush(S entity) { return null; }
        @Override public void flush() {}

		@Override
		public <S extends MonthlyTracker> List<S> saveAllAndFlush(Iterable<S> entities) {
			return null;
		}

		@Override
		public void deleteAllInBatch(Iterable<MonthlyTracker> entities) {

		}

		@Override
		public void deleteAllByIdInBatch(Iterable<Long> ids) {

		}

		@Override
		public void deleteAllInBatch() {

		}

		@Override
		public MonthlyTracker getOne(Long id) {
			return null;
		}

		@Override
		public MonthlyTracker getById(Long id) {
			return null;
		}

		@Override
		public MonthlyTracker getReferenceById(Long id) {
			return null;
		}

		@Override
		public <S extends MonthlyTracker> List<S> findAll(Example<S> example) {
			return null;
		}

		@Override
		public <S extends MonthlyTracker> List<S> findAll(Example<S> example, Sort sort) {
			return null;
		}

		@Override
		public List<MonthlyTracker> findAll(Sort sort) {
			return null;
		}

		@Override
		public Page<MonthlyTracker> findAll(Pageable pageable) {
			return null;
		}

		@Override
		public <S extends MonthlyTracker> Optional<S> findOne(Example<S> example) {
			return Optional.empty();
		}

		@Override
		public <S extends MonthlyTracker> Page<S> findAll(Example<S> example, Pageable pageable) {
			return null;
		}

		@Override
		public <S extends MonthlyTracker> long count(Example<S> example) {
			return 0;
		}

		@Override
		public <S extends MonthlyTracker> boolean exists(Example<S> example) {
			return false;
		}

		@Override
		public <S extends MonthlyTracker, R> R findBy(Example<S> example,
				Function<FetchableFluentQuery<S>, R> queryFunction) {
			return null;
		}
    }
}
