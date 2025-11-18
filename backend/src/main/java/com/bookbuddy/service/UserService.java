package com.bookbuddy.service;

import com.bookbuddy.model.User;
import com.bookbuddy.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing {@link User} entities.
 *
 * This class handles the business logic for user-related
 * operations such as registration, login validation, duplicate
 * checks, updates, and deletions. It acts as an intermediary
 * between the controller layer and the repository layer.
 *
 * Main Tasks in UserService:
 * 
 *   Register new users
 *   Validate login credentials
 *   Check if user_name or email already exists
 *   Update or delete user information
 * 
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor injection for dependencies.
     * @param userRepository repository instance injected by Spring
     * @param passwordEncoder password encoder from SecurityConfig
     */
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates or updates a user in the database.
     * Hashes the password before saving.
     * @param user the user entity to be saved or updated
     * @return the saved {@link User} entity
     */
    public User saveUser(User user) {
        System.out.println("DEBUG: Saving user - " + user.getUsername());
        // Hash password if it's not already hashed (doesn't start with $2a$ which is BCrypt prefix)
        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
            user.updatePassword(passwordEncoder.encode(user.getPassword()));
        }
        User savedUser = userRepository.save(user);
        System.out.println("DEBUG: User saved successfully with ID: " + savedUser.getId());
        return savedUser;
    }

    /**
     * Retrieves all users from the database.
     * @return list of all {@link User} entities
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their unique ID.
     * @param id the user's ID
     * @return an {@link Optional} containing the user if found
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Retrieves a user by their username.
     * @param username the user's username
     * @return an {@link Optional} containing the user if found
     */
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Checks if a user exists with the specified email.
     * @param email the user's email address
     * @return true if the email exists, false otherwise
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Deletes a user from the database by ID.
     * @param id the user's ID
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Updates an existing user's information.
     * Uses the model's built-in update methods for field validation.
     *
     * @param id the ID of the user to update
     * @param newData the user object containing updated fields
     * @return the updated {@link User} object
     * @throws EntityNotFoundException if no user is found with the given ID
     */
    public User updateUser(Long id, User newData) {
        Optional<User> existingUserOpt = userRepository.findById(id);

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            existingUser.updateFirstName(newData.getFirstName());
            existingUser.updateLastName(newData.getLastName());
            existingUser.updateUsername(newData.getUsername());
            existingUser.updateEmail(newData.getEmail());

            // Hash password if updating
            if (newData.getPassword() != null && !newData.getPassword().isBlank()
                && !newData.getPassword().startsWith("$2a$")) {
                existingUser.updatePassword(passwordEncoder.encode(newData.getPassword()));
            }

            return userRepository.save(existingUser);
        } else {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
    }

    /**
     * Authenticates a user by username/email and password.
     *
     * @param usernameOrEmail username or email to search for
     * @param password raw password to verify
     * @return the authenticated {@link User} if credentials are valid
     * @throws EntityNotFoundException if user not found
     * @throws IllegalArgumentException if password is incorrect
     */
    public User login(String usernameOrEmail, String password) {
        // Try to find user by username first, then by email
        Optional<User> userOpt = userRepository.findByUsername(usernameOrEmail);

        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByEmail(usernameOrEmail);
        }

        if (userOpt.isEmpty()) {
            throw new EntityNotFoundException("User not found with username or email: " + usernameOrEmail);
        }

        User user = userOpt.get();

        // Verify password using BCrypt
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return user;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
