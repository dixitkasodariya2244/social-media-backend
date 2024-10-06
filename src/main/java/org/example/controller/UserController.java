package org.example.controller;

import org.example.DTO.AuthenticationResponse;
import org.example.DTO.LoginRequestDTO;
import org.example.DTO.StatusUpdateDTO;
import org.example.DTO.UserDTO;
import org.example.entity.StatusUpdate;
import org.example.entity.User;
import org.example.services.StatusUpdateService;
import org.example.services.UserServices;
import org.example.utils.JwtUtil;
import org.example.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    private UserServices userService;

    @Autowired
    private StatusUpdateService statusUpdateService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(UserMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/registration")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        User savedUser = userService.save(user);
        return ResponseEntity.ok(UserMapper.toDTO(savedUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok(UserMapper.toDTO(updatedUser));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO loginRequest) {
        Optional<User> loggedInUser = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());

        if (loggedInUser.isPresent()) {
            User user = loggedInUser.get();
            String token = jwtUtil.generateToken(user.getEmail());

            return ResponseEntity.ok(new AuthenticationResponse(token, user.getId())); // Create an AuthenticationResponse class with a `token` field
        } else {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
    }
    // Endpoint to get all relevant status updates
//    @GetMapping("/{id}/home")
//    public ResponseEntity<List<StatusUpdateDTO>> getUserHomePage(@PathVariable Long id) {
//        List<StatusUpdate> statusUpdates = statusUpdateService.getAllRelevantStatusUpdates(id);
//        List<StatusUpdateDTO> statusUpdateDTOs = statusUpdates.stream()
//                .map(UserMapper::toStatusUpdateDTO)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(statusUpdateDTOs);
//    }

    @GetMapping("/{id}/home")
    public ResponseEntity<UserDTO> getUserHomePage(@PathVariable Long id) {
        // Get user by ID, including friends and status updates
        Optional<User> userOptional = userService.getUserByIdWithStatusUpdates(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Convert User entity to UserDTO
            UserDTO userDTO = UserMapper.toDTO(user);

            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //    @GetMapping("/top-profiles")
//    public ResponseEntity<List<UserDTO>> getTopProfiles() {
//        // Assuming you have a method in userService to get top profiles
//        List<User> users = userService.getTopProfiles();
//        List<UserDTO> userDTOs = users.stream()
//                .map(UserMapper::toDTO)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(userDTOs);
//    }
// Endpoint to search users by name
    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUsersByName(@RequestParam String name) {
        List<User> users = userService.findUsersByName(name);
        List<UserDTO> userDTOs = users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }
}
