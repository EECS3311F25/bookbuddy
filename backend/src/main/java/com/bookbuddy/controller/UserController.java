package com.bookbuddy.controller;

import com.bookbuddy.dto.UserRequest;
import com.bookbuddy.model.User;
import com.bookbuddy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for User operations
 * handles all user-related API requests 
 */

@RestController
@RequestMapping("/api/users")

public class UserController { 
	private final UserService userService; 
	
	@Autowired
	public UserController(UserService userService) { 
		this.userService = userService; 
	}
	
	/**
     * Register a new user.
     *
     * @param request user data sent from client
     * @return created user or error message
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest request) {

        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email already exists");
        }

        if (userService.existsByUsername(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username already exists");
        }

        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );

        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    /**
     * Get all users.
     *
     * @return list of users
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Get a user by ID.
     *
     * @param id user ID
     * @return user or an error message
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
    }

    /**
     * Get a user by username.
     *
     * @param username username to search
     * @return user or an error message
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
    }

    /**
     * Check if username already exists.
     *
     * @param username value to check
     * @return true or false
     */
    @GetMapping("/exists/username/{username}")
    public ResponseEntity<Boolean> existsByUsername(@PathVariable String username) {
        boolean exists = userService.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

    /**
     * Check if email already exists.
     *
     * @param email value to check
     * @return true or false
     */
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    /**
     * Update a user.
     *
     * @param id user ID
     * @param request new user data
     * @return updated user or error
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @Valid @RequestBody UserRequest request) {
        try {
            User updatedData = new User(
            		request.getFirstName(), request.getLastName(),
                    request.getUsername(), request.getEmail(),
                    request.getPassword());

            User updatedUser = userService.updateUser(id, updatedData);
            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
    }

    /**
     * Delete a user.
     *
     * @param id user ID
     * @return success message or error
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);

        if (user.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
