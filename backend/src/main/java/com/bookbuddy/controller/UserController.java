package com.bookbuddy.controller;

import com.bookbuddy.dto.LoginRequest;
import com.bookbuddy.dto.UserDTO;
import com.bookbuddy.dto.UserRequest;
import com.bookbuddy.model.User;
import com.bookbuddy.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for User operations
 * Handles user registration, login, profile management
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Login user
     * POST /api/users/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest request) {
        try {
            User user = userService.login(request.getUsernameOrEmail(), request.getPassword());
            UserDTO userDTO = UserDTO.fromUser(user);
            return ResponseEntity.ok(userDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }
    }

    /**
     * Register a new user
     * POST /api/users/register
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest request) {
        System.out.println("DEBUG: Register request received for username: " + request.getUsername());

        // Check if email already exists
        if (userService.existsByEmail(request.getEmail())) {
            System.out.println("DEBUG: Email already exists: " + request.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email already exists");
        }

        // Check if username already exists
        if (userService.getUserByUsername(request.getUsername()).isPresent()) {
            System.out.println("DEBUG: Username already exists: " + request.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username already exists");
        }

        System.out.println("DEBUG: Creating new user object");
        // Create new user
        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getUsername(),
                request.getEmail(),
                request.getPassword());

        System.out.println("DEBUG: About to save user");
        User savedUser = userService.saveUser(user);
        UserDTO userDTO = UserDTO.fromUser(savedUser);
        System.out.println("DEBUG: User saved, returning response");
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    /**
     * Get all users
     * GET /api/users
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(UserDTO::fromUser)
                .toList();
        return ResponseEntity.ok(userDTOs);
    }

    /**
     * Get user by ID
     * GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            UserDTO userDTO = UserDTO.fromUser(user.get());
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with id: " + id);
        }
    }

    /**
     * Get user by username
     * GET /api/users/username/{username}
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isPresent()) {
            UserDTO userDTO = UserDTO.fromUser(user.get());
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with username: " + username);
        }
    }

    /**
     * Update user profile
     * PUT /api/users/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        try {
            // Get the existing user to check if username is changing
            Optional<User> existingUserOpt = userService.getUserById(id);
            if (existingUserOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found with id: " + id);
            }

            User existingUser = existingUserOpt.get();

            // Check if username is being changed and if new username already exists
            if (!existingUser.getUsername().equals(request.getUsername())) {
                if (userService.getUserByUsername(request.getUsername()).isPresent()) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body("Username already exists");
                }
            }

            User updatedData = new User(
                    request.getFirstName(),
                    request.getLastName(),
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword());

            User updatedUser = userService.updateUser(id, updatedData);
            UserDTO userDTO = UserDTO.fromUser(updatedUser);
            return ResponseEntity.ok(userDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating user");
        }
    }

    /**
     * Delete user
     * DELETE /api/users/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with id: " + id);
        }
    }
}
