package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.dto.ChangePasswordRequest;
import com.example.turisticka_agencija.dto.UpdateProfileRequest;
import com.example.turisticka_agencija.dto.UserResponse;
import com.example.turisticka_agencija.model.User;
import com.example.turisticka_agencija.service.AuthService;
import com.example.turisticka_agencija.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService,AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        List<UserResponse> users = userService.getAllUsers()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getContact(),
                        user.getRole()
                ))
                .toList();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {

        User user = userService.getUserById(id);

        return ResponseEntity.ok(
                new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getContact(),
                        user.getRole()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok("Korisnik je uspešno obrisan");
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateProfile(
            Authentication authentication,
            @RequestBody UpdateProfileRequest request
    ) {

        User user = userService.updateProfile(authentication.getName(), request);

        return ResponseEntity.ok(
                new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getContact(),
                        user.getRole()
                )
        );
    }

    @PutMapping("/me/password")
    public ResponseEntity<String> changePassword(
            Authentication authentication,
            @RequestBody ChangePasswordRequest request
    ) {

        authService.changePassword(authentication.getName(), request);

        return ResponseEntity.ok("Lozinka je uspešno promenjena");
    }
}