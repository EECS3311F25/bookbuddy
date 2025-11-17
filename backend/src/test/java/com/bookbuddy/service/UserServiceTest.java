package com.bookbuddy.service;

import com.bookbuddy.model.User;
import com.bookbuddy.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for UserService using an in-memory Fake Repository.
 */
class UserServiceTest {

    private UserService userService;
    private FakeUserRepository fakeRepo;
    private FakePasswordEncoder fakeEncoder;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        fakeRepo = new FakeUserRepository();
        fakeEncoder = new FakePasswordEncoder();
        userService = new UserService(fakeRepo, fakeEncoder);

        user1 = new User("John", "Doe", "john123", "john@mail.com", "pass123");
        user2 = new User("Alice", "Smith", "alice88", "alice@mail.com", "alicepass");
    }

    // Utility method to set ID manually
    private void setId(User u, long id) {
        try {
            Field f = User.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(u, id);
        } catch (Exception ignored) {}
    }

    @Test
    void testSaveUserHashesPassword() {
        User saved = userService.saveUser(user1);

        assertTrue(saved.getPassword().startsWith("$2a$"));
        assertEquals(1, fakeRepo.storage.size());
    }

    @Test
    void testSaveUserDoesNotRehashHashedPassword() {
        user1.updatePassword("$2a$alreadyHashed");
        User saved = userService.saveUser(user1);

        assertEquals("$2a$alreadyHashed", saved.getPassword());
    }

    @Test
    void testGetAllUsers() {
        fakeRepo.save(user1);
        fakeRepo.save(user2);

        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void testGetUserById() {
        User saved = fakeRepo.save(user1);
        Optional<User> found = userService.getUserById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("john123", found.get().getUsername());
    }

    @Test
    void testGetUserByUsername() {
        fakeRepo.save(user1);

        Optional<User> found = userService.getUserByUsername("john123");
        assertTrue(found.isPresent());
        assertEquals("john@mail.com", found.get().getEmail());
    }

    @Test
    void testExistsByEmail() {
        fakeRepo.save(user1);

        assertTrue(userService.existsByEmail("john@mail.com"));
        assertFalse(userService.existsByEmail("missing@mail.com"));
    }

    @Test
    void testDeleteUser() {
        User saved = fakeRepo.save(user1);
        userService.deleteUser(saved.getId());

        assertEquals(0, fakeRepo.storage.size());
    }

    @Test
    void testUpdateUserUpdatesBasicFields() {
        User saved = fakeRepo.save(user1);

        User newData = new User("NewFirst", "NewLast", "newuser", "new@mail.com", "newpass");

        User updated = userService.updateUser(saved.getId(), newData);

        assertEquals("NewFirst", updated.getFirstName());
        assertEquals("NewLast", updated.getLastName());
        assertEquals("newuser", updated.getUsername());
        assertEquals("new@mail.com", updated.getEmail());
    }

    @Test
    void testUpdateUserHashesNewPassword() {
        User saved = fakeRepo.save(user1);

        User newData = new User();
        newData.updatePassword("newrawpass");

        User updated = userService.updateUser(saved.getId(), newData);

        assertTrue(updated.getPassword().startsWith("$2a$"));
    }

    @Test
    void testUpdateUserDoesNotRehashExistingHashedPassword() {
        User saved = fakeRepo.save(user1);

        User newData = new User();
        newData.updatePassword("$2a$already"); // Already hashed password

        User updated = userService.updateUser(saved.getId(), newData);

        // When password starts with $2a$, updateUser skips updating it entirely
        // So the password remains unchanged from the original
        assertEquals("pass123", updated.getPassword());
    }

    @Test
    void testUpdateUserThrowsIfNotFound() {
        User newData = new User();

        assertThrows(EntityNotFoundException.class,
                () -> userService.updateUser(999L, newData));
    }

    @Test
    void testUpdateUserIgnoresBlankPassword() {
        User saved = fakeRepo.save(user1);

        User newData = new User();
        newData.updatePassword("   "); // blank password should be ignored

        User updated = userService.updateUser(saved.getId(), newData);

        // Ensure blank password was ignored
        assertNotEquals("   ", updated.getPassword());

        // Ensure password remains unchanged
        assertEquals("pass123", updated.getPassword());
    }


    /**
     * Fake in-memory PasswordEncoder
     */
    static class FakePasswordEncoder implements PasswordEncoder {
        @Override
        public String encode(CharSequence rawPassword) {
            return "$2a$" + rawPassword;
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return encodedPassword.equals("$2a$" + rawPassword);
        }
    }

    /**
     * Simple in-memory Fake Repository for User.
     */
    static class FakeUserRepository implements UserRepository {

        long idCounter = 1;
        Map<Long, User> storage = new HashMap<>();

        @Override
        public User save(User user) {
            try {
                Field f = User.class.getDeclaredField("id");
                f.setAccessible(true);

                Long currentId = (Long) f.get(user);
                if (currentId == null || currentId == 0) {
                    f.set(user, idCounter++);
                }
            } catch (Exception ignored) {}

            storage.put(user.getId(), user);
            return user;
        }

        @Override
        public List<User> findAll() {
            return new ArrayList<>(storage.values());
        }

        @Override
        public Optional<User> findById(Long id) {
            return Optional.ofNullable(storage.get(id));
        }

        @Override
        public void deleteById(Long id) {
            storage.remove(id);
        }

        @Override
        public Optional<User> findByUsername(String username) {
            return storage.values().stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst();
        }

        @Override
        public boolean existsByEmail(String email) {
            return storage.values().stream()
                    .anyMatch(u -> u.getEmail().equals(email));
        }

        @Override
        public boolean existsByUsername(String username) {
            return storage.values().stream()
                    .anyMatch(u -> u.getUsername().equals(username));
        }

        // Unused JPA methods
        @Override public boolean existsById(Long id) { return false; }
        @Override public long count() { return 0; }
        @Override public void delete(User entity) {}
        @Override public void deleteAll() {}
        @Override public void deleteAll(Iterable<? extends User> entities) {}
        @Override public void deleteAllById(Iterable<? extends Long> longs) {}
        @Override public List<User> findAllById(Iterable<Long> longs) { return null; }
        @Override public <S extends User> List<S> saveAll(Iterable<S> entities) { return null; }
        @Override public <S extends User> S saveAndFlush(S entity) { return null; }
        @Override public void flush() {}

		@Override
		public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
			return null;
		}

		@Override
		public void deleteAllInBatch(Iterable<User> entities) {

		}

		@Override
		public void deleteAllByIdInBatch(Iterable<Long> ids) {

		}

		@Override
		public void deleteAllInBatch() {

		}

		@Override
		public User getOne(Long id) {
			return null;
		}

		@Override
		public User getById(Long id) {
			return null;
		}

		@Override
		public User getReferenceById(Long id) {
			return null;
		}

		@Override
		public <S extends User> List<S> findAll(Example<S> example) {
			return null;
		}

		@Override
		public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
			return null;
		}

		@Override
		public List<User> findAll(Sort sort) {
			return null;
		}

		@Override
		public Page<User> findAll(Pageable pageable) {
			return null;
		}

		@Override
		public <S extends User> Optional<S> findOne(Example<S> example) {
			return Optional.empty();
		}

		@Override
		public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
			return null;
		}

		@Override
		public <S extends User> long count(Example<S> example) {
			return 0;
		}

		@Override
		public <S extends User> boolean exists(Example<S> example) {
			return false;
		}

		@Override
		public <S extends User, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
			return null;
		}

		@Override
		public Optional<User> findByEmail(String email) {
			return Optional.empty();
		}
    }
}
