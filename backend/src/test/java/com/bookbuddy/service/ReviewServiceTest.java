package com.bookbuddy.service;

import com.bookbuddy.model.Review;
import com.bookbuddy.model.BookCatalog;
import com.bookbuddy.model.User;
import com.bookbuddy.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for ReviewService using a simple in-memory Fake repository.
 */
class ReviewServiceTest {

    private ReviewService reviewService;
    private FakeReviewRepository fakeRepo;

    private User user1;
    private User user2;

    private BookCatalog book1;
    private BookCatalog book2;

    @BeforeEach
    void setUp() {
        fakeRepo = new FakeReviewRepository();
        reviewService = new ReviewService(fakeRepo);

        user1 = new User("John", "Doe", "john123", "john@test.com", "pass123");
        user2 = new User("Alice", "Smith", "alice89", "alice@test.com", "pass888");

        book1 = new BookCatalog("Dune", "Frank Herbert");
        book2 = new BookCatalog("1984", "George Orwell");

        // give books IDs (service tests assume real IDs)
        setId(book1, 1L);
        setId(book2, 2L);
    }

    // Utility to assign IDs (simulates database behavior)
    private void setId(BookCatalog book, long id) {
        try {
            var f = BookCatalog.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(book, id);
        } catch (Exception ignored) {}
    }

    @Test
    void testSaveReview() {
        Review r = new Review(book1, user1, 4);
        r.setComment("Great read");
        Review saved = reviewService.saveReview(r);

        assertEquals("Great read", saved.getComment());
        assertEquals(1, fakeRepo.storage.size());
    }

    @Test
    void testGetAllReviews() {
        Review r1 = new Review(book1, user1, 5);
        r1.setComment("Excellent");
        Review r2 = new Review(book1, user1, 3);
        r2.setComment("Okay");

        fakeRepo.save(r1);
        fakeRepo.save(r2);

        List<Review> result = reviewService.getAllReviews();
        assertEquals(2, result.size());
    }

    @Test
    void testGetReviewById() {
        Review r = new Review(book1, user1, 5);
        r.setComment("Loved it");
        r = fakeRepo.save(r);

        Optional<Review> found = reviewService.getReviewById(r.getId());

        assertTrue(found.isPresent());
        assertEquals("Loved it", found.get().getComment());
    }

    @Test
    void testDeleteReview() {
        Review r = new Review(book1, user1, 2);
        r.setComment("Mid");
        r = fakeRepo.save(r);

        reviewService.deleteReview(r.getId());

        assertEquals(0, fakeRepo.storage.size());
    }

    @Test
    void testDeleteReviewDoesNotCrashIfMissing() {
        reviewService.deleteReview(999L); // non-existent
        assertEquals(0, fakeRepo.storage.size());
    }

    @Test
    void testGetReviewsByBookId() {
        Review r1 = new Review(book1, user1, 5);
        r1.setComment("Excellent");
        Review r2 = new Review(book1, user1, 4);
        r2.setComment("Pretty good");
        Review r3 = new Review(book2, user1, 1);
        r3.setComment("Did not like");

        fakeRepo.save(r1);
        fakeRepo.save(r2);
        fakeRepo.save(r3);

        List<Review> result = reviewService.getReviewsByBookId(1L);

        assertEquals(2, result.size());
    }

    @Test
    void testGetReviewsByBookIdWhenNoneExist() {
        List<Review> result = reviewService.getReviewsByBookId(1L);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAverageRating() {
        Review r1 = new Review(book1, user1, 5);
        r1.setComment("Great");
        Review r2 = new Review(book1, user1, 3);
        r2.setComment("Good");

        fakeRepo.save(r1);
        fakeRepo.save(r2);

        double avg = reviewService.getAverageRating(1L);
        assertEquals(4.0, avg);
    }

    @Test
    void testGetAverageRatingSingleReview() {
        Review r = new Review(book1, user1, 2);
        r.setComment("Meh");
        fakeRepo.save(r);

        double avg = reviewService.getAverageRating(1L);

        assertEquals(2.0, avg);
    }

    @Test
    void testGetAverageRatingNoReviews() {
        assertEquals(0.0, reviewService.getAverageRating(1L));
    }

    @Test
    void testSaveMultipleReviewsFromDifferentUsers() {
        Review r1 = new Review(book1, user1, 5);
        r1.setComment("Amazing");
        Review r2 = new Review(book1, user2, 4);
        r2.setComment("Good");

        fakeRepo.save(r1);
        fakeRepo.save(r2);

        assertEquals(2, fakeRepo.storage.size());
    }

    @Test
    void testSaveReviewWithNullComment() {
        Review r = new Review(book1, user1, 3);
        Review saved = reviewService.saveReview(r);

        assertNull(saved.getComment());
    }

    @Test
    void testUpdateReviewRating() {
        Review r = new Review(book1, user1, 2);
        r.setComment("Okay");
        r = fakeRepo.save(r);

        r.setRating(5);
        reviewService.saveReview(r);

        assertEquals(5, fakeRepo.storage.get(r.getId()).getRating());
    }

    @Test
    void testUpdateReviewComment() {
        Review r = new Review(book1, user1, 3);
        r.setComment("Old comment");
        r = fakeRepo.save(r);

        r.setComment("Updated comment");
        reviewService.saveReview(r);

        assertEquals("Updated comment",
                fakeRepo.storage.get(r.getId()).getComment());
    }

    @Test
    void testReviewsRemainIsolatedBetweenBooks() {
        Review r1 = new Review(book1, user1, 5);
        r1.setComment("Loved");
        Review r2 = new Review(book2, user1, 1);
        r2.setComment("Hated");

        fakeRepo.save(r1);
        fakeRepo.save(r2);

        List<Review> book1Reviews = reviewService.getReviewsByBookId(1L);
        List<Review> book2Reviews = reviewService.getReviewsByBookId(2L);

        assertEquals(1, book1Reviews.size());
        assertEquals(1, book2Reviews.size());
    }

    @Test
    void testRatingsAreClampedBetween0And5() {
        Review r1 = new Review(book1, user1, -10);
        r1.setComment("Too low");
        Review r2 = new Review(book1, user1, 100);
        r2.setComment("Too high");

        fakeRepo.save(r1);
        fakeRepo.save(r2);

        assertEquals(0, r1.getRating());
        assertEquals(5, r2.getRating());
    }

    /**
     * Simple in-memory fake repository.
     */
    static class FakeReviewRepository implements ReviewRepository {

        long idCounter = 1;
        Map<Long, Review> storage = new HashMap<>();

        @Override
        public Review save(Review review) {
            try {
                var f = Review.class.getDeclaredField("id");
                f.setAccessible(true);
                if (f.getLong(review) == 0) {
                    f.set(review, idCounter++);
                }
            } catch (Exception ignored) {}

            storage.put(review.getId(), review);
            return review;
        }

        @Override
        public List<Review> findAll() {
            return new ArrayList<>(storage.values());
        }

        @Override
        public Optional<Review> findById(Long id) {
            return Optional.ofNullable(storage.get(id));
        }

        @Override
        public void deleteById(Long id) {
            storage.remove(id);
        }

        @Override
        public List<Review> findByBookId(Long bookId) {
            List<Review> result = new ArrayList<>();
            for (Review r : storage.values()) {
                if (r.getBook() != null && r.getBook().getId() == bookId) {
                    result.add(r);
                }
            }
            return result;
        }

		@Override
		public void flush() {

		}

		@Override
		public <S extends Review> S saveAndFlush(S entity) {
			return null;
		}

		@Override
		public <S extends Review> List<S> saveAllAndFlush(Iterable<S> entities) {
			return null;
		}

		@Override
		public void deleteAllInBatch(Iterable<Review> entities) {

		}

		@Override
		public void deleteAllByIdInBatch(Iterable<Long> ids) {

		}

		@Override
		public void deleteAllInBatch() {

		}

		@Override
		public Review getOne(Long id) {
			return null;
		}

		@Override
		public Review getById(Long id) {
			return null;
		}

		@Override
		public Review getReferenceById(Long id) {
			return null;
		}

		@Override
		public <S extends Review> List<S> findAll(Example<S> example) {
			return null;
		}

		@Override
		public <S extends Review> List<S> findAll(Example<S> example, Sort sort) {
			return null;
		}

		@Override
		public <S extends Review> List<S> saveAll(Iterable<S> entities) {
			return null;
		}

		@Override
		public List<Review> findAllById(Iterable<Long> ids) {
			return null;
		}

		@Override
		public boolean existsById(Long id) {
			return false;
		}

		@Override
		public long count() {
			return 0;
		}

		@Override
		public void delete(Review entity) {

		}

		@Override
		public void deleteAllById(Iterable<? extends Long> ids) {

		}

		@Override
		public void deleteAll(Iterable<? extends Review> entities) {

		}

		@Override
		public void deleteAll() {

		}

		@Override
		public List<Review> findAll(Sort sort) {
			return null;
		}

		@Override
		public Page<Review> findAll(Pageable pageable) {
			return null;
		}

		@Override
		public <S extends Review> Optional<S> findOne(Example<S> example) {
			return Optional.empty();
		}

		@Override
		public <S extends Review> Page<S> findAll(Example<S> example, Pageable pageable) {
			return null;
		}

		@Override
		public <S extends Review> long count(Example<S> example) {
			return 0;
		}

		@Override
		public <S extends Review> boolean exists(Example<S> example) {
			return false;
		}

		@Override
		public <S extends Review, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
			return null;
		}

		@Override
		public List<Review> findByUserId(Long userId) {
			return null;
		}

		@Override
		public List<Review> findByRating(int rating) {
			return null;
		}
    }
}
